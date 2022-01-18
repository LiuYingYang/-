package com.medusa.gruul.platform.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.account.api.conf.MiniInfoProperty;
import com.medusa.gruul.common.core.constant.CommonConstants;
import com.medusa.gruul.common.core.constant.RegexConstants;
import com.medusa.gruul.common.core.constant.TimeConstants;
import com.medusa.gruul.common.core.constant.enums.AuthCodeEnum;
import com.medusa.gruul.common.core.constant.enums.LoginTerminalEnum;
import com.medusa.gruul.common.core.exception.ServiceException;
import com.medusa.gruul.common.core.util.*;
import com.medusa.gruul.common.dto.CurPcUserInfoDto;
import com.medusa.gruul.common.dto.CurUserDto;
import com.medusa.gruul.platform.api.entity.*;
import com.medusa.gruul.platform.conf.MeConstant;
import com.medusa.gruul.platform.conf.PlatformRedis;
import com.medusa.gruul.platform.conf.WechatOpenProperties;
import com.medusa.gruul.platform.constant.RedisConstant;
import com.medusa.gruul.platform.constant.ScanCodeScenesEnum;
import com.medusa.gruul.platform.mapper.AccountInfoMapper;
import com.medusa.gruul.platform.model.dto.*;
import com.medusa.gruul.platform.model.vo.*;
import com.medusa.gruul.platform.service.*;
import lombok.extern.log4j.Log4j2;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 平台用户表 服务实现类
 * </p>
 *
 * @author whh
 * @since 2019-09-07
 */
@Service
@Log4j2
@Component
@EnableConfigurationProperties(MiniInfoProperty.class)
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements IAccountInfoService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private ISendCodeService sendCodeService;

    @Autowired
    private WechatOpenProperties wechatOpenProperties;
    @Autowired
    private IPlatformShopInfoService platformShopInfoService;


    @Override
    public void checkoutAccount(String phone, Integer type) {
        AccountInfo accountInfo = this.baseMapper.selectOne(new QueryWrapper<AccountInfo>().eq("phone", phone));
        //账号存在校验
        if (type.equals(CommonConstants.NUMBER_ONE)) {
            if (accountInfo == null) {
                throw new ServiceException("账号不存在", SystemCode.DATA_NOT_EXIST.getCode());
            }
            return;
        }
        //账号不存在校验
        if (type.equals(CommonConstants.NUMBER_TWO)) {
            if (accountInfo != null) {
                throw new ServiceException("账号已存在");
            }
            return;
        }
        throw new ServiceException("非法操作");

    }




    /**
     * 缓存用户数据到缓存中
     * 每天12点失效
     *
     * @param info 用户基本信息
     * @param vo   前端封装用户信息
     * @return java.lang.String   redisKey token
     */
    private String cachePlatformCurUserDto(AccountInfo info, AccountInfoVo vo) {
        CurUserDto curUserDto = new CurUserDto();
        curUserDto.setUserId(info.getId().toString());
        curUserDto.setUserType(1);
        curUserDto.setAvatarUrl(info.getAvatarUrl());
        curUserDto.setGender(info.getGender());
        curUserDto.setOpenId(info.getOpenId());
        curUserDto.setNikeName(info.getNikeName());
        LoginShopInfoVo shopInfoVo = vo.getShopInfoVo();
        if (shopInfoVo != null && StrUtil.isNotEmpty(shopInfoVo.getShopId())) {
            curUserDto.setShopId(shopInfoVo.getShopId());
        }
        PlatformRedis platformRedis = new PlatformRedis();
        long between = getTodayEndTime();
        String jwtToken = new JwtUtils(MeConstant.JWT_PRIVATE_KEY).createJwtToken(MeConstant.PLATFORM);
        String redisKey = RedisConstant.TOKEN_KEY.concat(jwtToken);
        platformRedis.setNxPx(redisKey, JSON.toJSONString(curUserDto), between);

        //新版
        CurPcUserInfoDto curPcUserInfoDto = new CurPcUserInfoDto();
        curPcUserInfoDto.setUserId(info.getId().toString());
        curPcUserInfoDto.setTerminalType(LoginTerminalEnum.PC);
        curPcUserInfoDto.setAvatarUrl(info.getAvatarUrl());
        curPcUserInfoDto.setGender(info.getGender());
        curPcUserInfoDto.setOpenId(info.getOpenId());
        curPcUserInfoDto.setNikeName(info.getNikeName());
        PlatformRedis allRedis = new PlatformRedis(CommonConstants.PC_INFO_REDIS_KEY);
        allRedis.setNxPx(jwtToken, JSON.toJSONString(curPcUserInfoDto), between);

        return platformRedis.getBaseKey().concat(":").concat(redisKey);

    }

    /**
     * 获取当前时间距离当天结束时间还有多久毫秒值
     *
     * @return 1234ms
     */
    private long getTodayEndTime() {
        Date date = new Date();
        DateTime endOfDay = DateUtil.endOfDay(date);
        return DateUtil.between(date, endOfDay, DateUnit.MS);
    }

    /**
     * 更新用户最后登录时间
     *
     * @param accountInfoId com.medusa.gruul.platform.api.entity.AccountInfo
     */
    private void updateAccountLastLoignTime(Long accountInfoId) {
        CompletableFuture.runAsync(() -> {
            AccountInfo info = new AccountInfo();
            info.setLastLoginTime(LocalDateTime.now());
            info.setId(accountInfoId);
            this.updateById(info);
        });
    }


    @Override
    public String preAccountScanCode(PreAccountVerifyDto preAccountVerifyDto) {
        if (!ScanCodeScenesEnum.findScenes(preAccountVerifyDto.getScenes())) {
            throw new ServiceException("场景类型无效");
        }
        CurUserDto httpCurUser = CurUserUtil.getHttpCurUser();
        if (httpCurUser != null) {
            preAccountVerifyDto.setUserId(Long.valueOf(httpCurUser.getUserId()));
        }
        if (preAccountVerifyDto.getScenes().equals(ScanCodeScenesEnum.ACCOUNT_SHOP_INFO_CHECK.getScenes())) {
            Long shopInfoId = preAccountVerifyDto.getShopInfoId();
            if (shopInfoId == null) {
                throw new ServiceException("shopInfoId不能为空");
            }
            PlatformShopInfo platformShopInfo = platformShopInfoService.getById(shopInfoId);
            if (platformShopInfo == null) {
                throw new ServiceException("店铺不存在");
            }
        }

        String redirectUrl = wechatOpenProperties.getDomain().concat("/account-info/account/verify/notify");
        //网页应用目前仅填写snsapi_login即
        String scope = "snsapi_login";
        String state = SecureUtil.md5(System.currentTimeMillis() + "");
        new PlatformRedis().setNxPx(state, JSONObject.toJSONString(preAccountVerifyDto), TimeConstants.TEN_MINUTES);

        return wxMpService.switchoverTo(preAccountVerifyDto.getAppId()).buildQrConnectUrl(redirectUrl, scope, state);
    }

    @Override
    public void accountScanCodeNotify(String code, String state, HttpServletResponse response) {
        if (StrUtil.isEmpty(state)) {
            throw new ServiceException("非法请求");
        }
        PlatformRedis platformRedis = new PlatformRedis();
        String jsonData = platformRedis.get(state);
        if (StrUtil.isEmpty(jsonData)) {
            throw new ServiceException("无效数据或数据已过期");
        }
        PreAccountVerifyDto preAccountVerifyDto = JSONObject.parseObject(jsonData, PreAccountVerifyDto.class);
        Result result = Result.failed();
        //微信换绑
        if (preAccountVerifyDto.getScenes().equals(ScanCodeScenesEnum.ACCOUNT_SWITCHING.getScenes())) {
            result = this.changeTie(preAccountVerifyDto.getAppId(), code, preAccountVerifyDto.getUserId());
            //账号注册
        } else if (preAccountVerifyDto.getScenes().equals(ScanCodeScenesEnum.ACCOUNT_REGISTER.getScenes())) {
            result = this.createTempAccount(preAccountVerifyDto.getAppId(), code);
        } else if (preAccountVerifyDto.getScenes().equals(ScanCodeScenesEnum.ACCOUNT_LOGGIN.getScenes())) {
            result = this.scanCodeLogin(preAccountVerifyDto.getAppId(), code);
        } else if (preAccountVerifyDto.getScenes().equals(ScanCodeScenesEnum.ACCOUNT_SHOP_INFO_CHECK.getScenes())) {
            result = this.verifyShopAccount(preAccountVerifyDto.getAppId(), code, preAccountVerifyDto.getShopInfoId());
        } else {
            throw new ServiceException("错误数据");
        }

        //用户同意授权,跳转成功后页面
        StringBuilder redirectUrl = new StringBuilder(preAccountVerifyDto.getRedirectUrl());
        //判断是否已存在路径参数
        if (preAccountVerifyDto.getRedirectUrl().contains(MeConstant.WENHAO)) {
            redirectUrl.append("&");
        } else {
            redirectUrl.append("?");
        }
        code = SecureUtil.md5(System.currentTimeMillis() + "");
        redirectUrl.append("code=").append(code);
        //获取二维码时如存在shopInfoId则返回，供校验扫码前和扫码后是否一致
        if (preAccountVerifyDto.getShopInfoId() != null) {
            redirectUrl.append("&shopInfoId=").append(preAccountVerifyDto.getShopInfoId());
        }
        //存储查询之后不删除但只有5分钟有效的数据
        platformRedis.setNxPx(code.concat(":inside"), JSONObject.toJSONString(result), TimeConstants.TEN_MINUTES);
        //存储返回结果,提供一次查询,查询即失效
        platformRedis.setNxPx(code, JSONObject.toJSONString(result), TimeConstants.TEN_MINUTES);

        try {
            response.sendRedirect(redirectUrl.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫码校验用户是否是店铺拥有者
     *
     * @param appId      网站应用appId
     * @param code       回调code
     * @param shopInfoId 店铺id
     * @return com.medusa.gruul.common.core.util.Result
     */
    private Result verifyShopAccount(String appId, String code, Long shopInfoId) {
        try {
            wxMpService.switchoverTo(appId);
            AccountInfo accountInfo = null;
            WxMpOAuth2AccessToken wxMpOauth2AccessToken = wxMpService.oauth2getAccessToken(code);
            if (StrUtil.isNotEmpty(wxMpOauth2AccessToken.getUnionId())) {
                accountInfo = this.getByAccountUnionId(wxMpOauth2AccessToken.getUnionId());
            }
            if (accountInfo == null) {
                accountInfo = this.getByAccountOpenId(wxMpOauth2AccessToken.getOpenId());
                if (accountInfo == null) {
                    return Result.failed("请使用正确账户");
                }
            }
            PlatformShopInfo platformShopInfo = platformShopInfoService.getById(shopInfoId);
            if (platformShopInfo == null) {
                return Result.failed("店铺数据不存在,非法校验");
            }
            if (!platformShopInfo.getAccountId().equals(accountInfo.getId())) {
                return Result.failed("请使用店铺所有者的微信账号扫码");
            }
            return Result.ok(accountInfo);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return Result.failed(e.getError().getErrorMsg());
        }
    }

    /**
     * 扫码登录回调
     *
     * @param appId 网站应用appId
     * @param code  回调code
     * @return com.medusa.gruul.common.core.util.Result
     */
    private Result scanCodeLogin(String appId, String code) {

        try {
            wxMpService.switchoverTo(appId);
            AccountInfo accountInfo = null;
            WxMpOAuth2AccessToken wxMpOauth2AccessToken = wxMpService.oauth2getAccessToken(code);
            if (StrUtil.isNotEmpty(wxMpOauth2AccessToken.getUnionId())) {
                accountInfo = this.getByAccountUnionId(wxMpOauth2AccessToken.getUnionId());
                if (accountInfo != null) {
                    return Result.ok(accountInfo);
                }
            }
            accountInfo = this.getByAccountOpenId(wxMpOauth2AccessToken.getOpenId());
            //openId也查不出来则说明是未注册账号,存储临时数据,可提供跳转注册
            if (accountInfo == null) {
                accountInfo = new AccountInfo();
                getMpInfo(accountInfo, wxMpOauth2AccessToken, appId);
            }
            return Result.ok(accountInfo);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return Result.failed(e.getError().getErrorMsg());
        }
    }

    /**
     * 创建临时账号数据,不存储至数据库,待账号注册成功时正式存入
     *
     * @param appId 网站应用的appid
     * @param code  授权返回的code
     * @return com.medusa.gruul.common.core.util.Result
     */
    private Result<AccountInfo> createTempAccount(String appId, String code) {
        this.wxMpService.switchoverTo(appId);
        try {
            AccountInfo accountInfo = null;
            WxMpOAuth2AccessToken wxMpOauth2AccessToken = wxMpService.oauth2getAccessToken(code);
            if (StrUtil.isNotEmpty(wxMpOauth2AccessToken.getUnionId())) {
                accountInfo = this.getByAccountUnionId(wxMpOauth2AccessToken.getUnionId());
                if (accountInfo != null) {
                    return Result.failed("该微信账号已存在绑定账号");
                }
            }
            accountInfo = this.getByAccountOpenId(wxMpOauth2AccessToken.getOpenId());
            if (accountInfo != null) {
                return Result.failed("该微信账号已存在绑定账号");
            }
            accountInfo = new AccountInfo();
            getMpInfo(accountInfo, wxMpOauth2AccessToken, appId);
            return Result.ok(accountInfo);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return Result.failed(e.getError().getErrorMsg());
        }

    }

    /**
     * 根据openId 获取用户信息
     *
     * @param openId 微信openId
     * @return com.medusa.gruul.platform.api.entity.AccountInfo
     */
    private AccountInfo getByAccountOpenId(String openId) {
        return this.getBaseMapper().selectOne(new QueryWrapper<AccountInfo>().eq("open_id", openId));
    }

    /**
     * 根据unionId 获取用户信息
     *
     * @param unionId 平台互通unionId
     * @return com.medusa.gruul.platform.api.entity.AccountInfo
     */
    private AccountInfo getByAccountUnionId(String unionId) {
        return this.getBaseMapper().selectOne(new QueryWrapper<AccountInfo>().eq("union_id", unionId));
    }

    @Override
    public LoginAccountInfoDetailVo info() {
        CurPcUserInfoDto curUser = CurUserUtil.getPcRqeustAccountInfo();
        if (curUser == null) {
            throw new ServiceException("非法查询", SystemCode.DATA_NOT_EXIST.getCode());
        }
        AccountInfo accountInfo = this.getById(curUser.getUserId());
        AccountInfoVo loginInfoVo = getLoginInfoVo(accountInfo);
        LoginAccountInfoDetailVo vo = new LoginAccountInfoDetailVo();
        BeanUtils.copyProperties(loginInfoVo, vo);
        vo.setBalance(accountInfo.getBalance());
        vo.setAccountType(accountInfo.getAccountType());
        vo.setPhone(accountInfo.getPhone());
        return vo;
    }


    @Override
    public Result<AccountInfo> changeTie(String appId, String code, Long userId) {
        AccountInfo accountInfo = null;
        try {
            WxMpOAuth2AccessToken wxMpOauth2AccessToken = wxMpService.switchoverTo(appId).oauth2getAccessToken(code);
            //判断是否已存在绑定账号
            AccountInfo old = null;
            if (StrUtil.isNotEmpty(wxMpOauth2AccessToken.getUnionId())) {
                old = this.baseMapper.selectOne(new QueryWrapper<AccountInfo>().eq("union_id", wxMpOauth2AccessToken.getUnionId()).notIn("id", userId));
            }
            if (old == null) {
                old = this.baseMapper.selectOne(new QueryWrapper<AccountInfo>().eq("open_id", wxMpOauth2AccessToken.getUnionId()).notIn("id", userId));
            }
            if (old != null) {
                throw new ServiceException("该微信号已绑定账号");
            }
            accountInfo = this.baseMapper.selectById(userId);
            AccountInfo info = getMpInfo(accountInfo, wxMpOauth2AccessToken, appId);

            this.updateById(info);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return Result.failed(e.getMessage());
        } catch (ServiceException e) {
            return Result.failed(e.getMessage());
        }
        return Result.ok(accountInfo);
    }


    @Override
    public void phoneChangeTie(PhoneChangeTieDto phoneChangeTieDto) {
        sendCodeService.certificateCheck(phoneChangeTieDto.getOneCertificate(), phoneChangeTieDto.getOldPhone(), AuthCodeEnum.ACCOUNT_PHONE_IN_TIE.getType());
        AccountInfo phoneAccount = this.getByPhone(phoneChangeTieDto.getNewPhone());
        if (phoneAccount != null) {
            throw new ServiceException("换绑手机账号已被使用");
        }
        sendCodeService.certificateCheck(phoneChangeTieDto.getTwoCertificate(), phoneChangeTieDto.getNewPhone(), AuthCodeEnum.ACCOUNT_PHONE_IN_TIE.getType());
        AccountInfo accountInfo = this.getById(CurUserUtil.getPcRqeustAccountInfo().getUserId());
        if (accountInfo == null) {
            throw new ServiceException("无效token");
        }
        accountInfo.setId(accountInfo.getId());
        accountInfo.setPhone(phoneChangeTieDto.getNewPhone());
        this.baseMapper.updateById(accountInfo);
        removeAccountLogin(accountInfo);

    }

    @Override
    public void passChangeTie(PassChangeTieDto passChangeTieDto) {
        AccountInfo accountInfo = this.getById(CurUserUtil.getPcRqeustAccountInfo().getUserId());
        if (accountInfo == null) {
            throw new ServiceException("无效账号");
        }
        if (!accountInfo.getPhone().equals(passChangeTieDto.getPhone())) {
            throw new ServiceException("手机号不正确");
        }
        sendCodeService.certificateCheck(passChangeTieDto.getCertificate(), accountInfo.getPhone(), AuthCodeEnum.ACCOUNT_PASSWORD_IN_TIE.getType());
        removeAccountLogin(accountInfo);
        accountInfo.setPassword(passChangeTieDto.getPasswd());
        String salt = RandomUtil.randomString(6);
        accountInfo.setSalt(salt);
        accountInfo.setPasswd(SecureUtil.md5(accountInfo.getPassword().concat(salt)));
        this.baseMapper.updateById(accountInfo);


    }


    @Override
    public AccountInfoVo login(LoginDto loginDto) {
        AccountInfoVo vo = null;
        switch (loginDto.getLoginType()) {
            case 1:
                vo = passwdLogin(loginDto.getPhone(), loginDto.getPassword());
                break;
            case 2:
                vo = phoneCodeLogin(loginDto.getPhone(), loginDto.getCertificate());
                break;
            case 3:
                vo = wxScanCodeLogin(loginDto.getCode());
                break;
            default:
                throw new ServiceException("非法登录请求");
        }
        updateAccountLastLoignTime(vo.getId());
        return vo;
    }

    /**
     * @param code code
     * @return
     */
    @Override
    public Result verifyStateResult(String code) {
        PlatformRedis platformRedis = new PlatformRedis();
        String jsonData = platformRedis.get(code);
        if (StrUtil.isEmpty(jsonData)) {
            return Result.failed("code已失效");
        }
        platformRedis.del(code);
        return JSONObject.parseObject(jsonData, Result.class);
    }

    @Override
    public void passwordRetrieve(PasswordRetrieveDto passwordRetrieveDto) {
        AccountInfo accountInfo = getByPhone(passwordRetrieveDto.getPhone());
        if (accountInfo == null) {
            throw new ServiceException("不存在该账号");
        }
        removeAccountLogin(accountInfo);
        //校验验证与手机号是否正确
        sendCodeService.certificateCheck(passwordRetrieveDto.getCertificate(), passwordRetrieveDto.getPhone(), AuthCodeEnum.ACCOUNT_FORGET_PASSWD.getType());

        accountInfo.setPassword(passwordRetrieveDto.getPasswd());
        String salt = RandomUtil.randomString(6);
        accountInfo.setSalt(salt);
        accountInfo.setPasswd(SecureUtil.md5(accountInfo.getPassword().concat(salt)));
        this.baseMapper.updateById(accountInfo);


    }

    /**
     * 清除指定用户缓存token
     *
     * @param accountInfo 用户数据
     */
    private void removeAccountLogin(AccountInfo accountInfo) {
        PlatformRedis platformRedis = new PlatformRedis();
        String key = SecureUtil.md5(accountInfo.getPhone()).concat(accountInfo.getSalt()).concat(accountInfo.getPasswd());
        String redisKey = RedisConstant.TOKEN_KEY.concat(key);
        platformRedis.del(redisKey);
    }

    /**
     * @param state
     * @return
     */
    private AccountInfoVo wxScanCodeLogin(String state) {
        String jsonData = new PlatformRedis().get(state);
        if (StrUtil.isEmpty(jsonData)) {
            throw new ServiceException("不存在该账号");
        }
        Result result = JSONObject.parseObject(jsonData, Result.class);
        if (result.getCode() != CommonConstants.SUCCESS) {
            throw new ServiceException(result.getMsg());
        }
        AccountInfo accountInfo = ((JSONObject) result.getData()).toJavaObject(AccountInfo.class);
        if (accountInfo.getId() == null) {
            throw new ServiceException("该微信账号未注册");
        }
        return getLoginInfoVo(accountInfo);
    }

    private AccountInfoVo phoneCodeLogin(String phone, String certificate) {
        AccountInfo accountInfo = this.getByPhone(phone);
        if (accountInfo == null) {
            throw new ServiceException("账号不存在");
        }
        sendCodeService.certificateCheck(certificate, phone, AuthCodeEnum.MINI_LOGIN.getType());
        return getLoginInfoVo(accountInfo);
    }

    /**
     * 手机号登录
     *
     * @param phone    手机号
     * @param password 密码
     * @return
     */
    private AccountInfoVo passwdLogin(String phone, String password) {
        AccountInfo accountInfo = this.getByPhone(phone);
        if (accountInfo == null) {
            throw new ServiceException("账号或密码错误");
        }
        String md5Pw = SecureUtil.md5(password.concat(accountInfo.getSalt()));
        if (!md5Pw.equals(accountInfo.getPasswd())) {
            throw new ServiceException("账号或密码错误");
        }
        return getLoginInfoVo(accountInfo);
    }

    /**
     * 封装返回用户登录信息
     *
     * @param accountInfo 账号信息
     * @return com.medusa.gruul.platform.model.vo.AccountInfoVo
     */
    @Override
    public AccountInfoVo getLoginInfoVo(AccountInfo accountInfo) {
        if (accountInfo.getForbidStatus().equals(CommonConstants.NUMBER_ONE)) {
            throw new ServiceException("账号当前无法登陆，请联系客服");
        }
        AccountInfoVo vo = new AccountInfoVo();
        BeanUtils.copyProperties(accountInfo, vo);
        //获取店铺信息
        PlatformShopInfo shopInfo = platformShopInfoService.getOne(null);
        if (shopInfo != null) {
            LoginShopInfoVo infoVo = platformShopInfoService.getLoginShopInfoVo(shopInfo);
            vo.setShopInfoVo(infoVo);
        }
        //设置平台用户Token redisKey
        String userToken = cachePlatformCurUserDto(accountInfo, vo);
        vo.setToken(userToken);
        return vo;
    }





    @Override
    public void emailChange(EmailChangeDto emailChangeDto) {
        AccountInfo accountInfo = this.getById(CurUserUtil.getPcRqeustAccountInfo().getUserId());
        if (ObjectUtil.isNull(accountInfo)) {
            throw new ServiceException("非法数据");
        }
        AccountInfo up = new AccountInfo();
        up.setId(accountInfo.getId());
        up.setEmail(emailChangeDto.getEmail());
        this.updateById(up);
    }




    @Override
    public Boolean verifyData(VerifyDataDto verifyDataDto) {
        CurUserDto curUser = CurUserUtil.getHttpCurUser();
        if (curUser == null) {
            throw new ServiceException("非法查询", SystemCode.DATA_NOT_EXIST.getCode());
        }
        AccountInfo accountInfo = this.getById(curUser.getUserId());
        Boolean flag = Boolean.FALSE;
        if (verifyDataDto.getOption().equals(CommonConstants.NUMBER_ONE)) {
            flag = accountInfo.getPhone().equals(verifyDataDto.getPhone());
        }
        return flag;
    }

    private AccountInfo getMpInfo(AccountInfo accountInfo, WxMpOAuth2AccessToken wxMpOauth2AccessToken, String appId) throws WxErrorException {
        WxMpUser wxMpUser = wxMpService.switchoverTo(appId).oauth2getUserInfo(wxMpOauth2AccessToken, "zh_CN");
        accountInfo.setRefreshToken(wxMpOauth2AccessToken.getRefreshToken());
        accountInfo.setAccessToken(wxMpOauth2AccessToken.getAccessToken());
        accountInfo.setAccessExpiresTime(DateUtils.timestampCoverLocalDateTime(wxMpOauth2AccessToken.getExpiresIn()));
        accountInfo.setOpenId(wxMpOauth2AccessToken.getOpenId());
        accountInfo.setCity(wxMpUser.getCity());
        accountInfo.setLanguage(wxMpUser.getLanguage());
        accountInfo.setNikeName(wxMpUser.getNickname());
        accountInfo.setAvatarUrl(wxMpUser.getHeadImgUrl());
        accountInfo.setGender(wxMpUser.getSex());
        accountInfo.setUnionId(StrUtil.isNotEmpty(wxMpUser.getUnionId()) ? wxMpUser.getUnionId() : null);
        accountInfo.setProvince(wxMpUser.getProvince());
        accountInfo.setCountry(wxMpUser.getCountry());
        accountInfo.setPrivilege(JSON.toJSONString(wxMpUser.getPrivileges()));
        return accountInfo;
    }

    private AccountInfo getByPhone(String username) {
        if (!ReUtil.isMatch(RegexConstants.REGEX_MOBILE_EXACT, username)) {
            throw new ServiceException("手机号错误", SystemCode.DATA_NOT_EXIST.getCode());
        }
        return this.baseMapper.selectOne(new QueryWrapper<AccountInfo>().eq("phone", username));
    }
}
