package com.medusa.gruul.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.platform.api.entity.AccountInfo;
import com.medusa.gruul.platform.model.dto.*;
import com.medusa.gruul.platform.model.vo.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 平台用户表 服务类
 * </p>
 *
 * @author whh
 * @since 2019-09-07
 */
public interface IAccountInfoService extends IService<AccountInfo> {


    /**
     * 校验是否存在该平台账号
     *
     * @param phone 手机号
     * @param type  type=1 校验账号存在  type=2校验账号不存在 默认使用1
     */
    void checkoutAccount(String phone, Integer type);

    /**
     * 用户校验预授权
     *
     * @param preAccountVerifyDto com.medusa.gruul.platform.model.dto.PreAccountVerifyDto
     * @return java.lang.String
     */
    String preAccountScanCode(PreAccountVerifyDto preAccountVerifyDto);

    /**
     * 用户校验统一回调入口
     *
     * @param code     code
     * @param state    state
     * @param response response
     */
    void accountScanCodeNotify(String code, String state, HttpServletResponse response);

    /**
     * 根据请求token获取当前用户信息
     *
     * @return com.medusa.gruul.platform.model.vo.AccountInfoVo
     */
    LoginAccountInfoDetailVo info();


    /**
     * 账号换绑
     *
     * @param appId  网站应用appId
     * @param code   扫码的code值
     * @param userId 用户id
     * @return 返回结果
     */
    Result changeTie(String appId, String code, Long userId);

    /**
     * 账号手机换绑
     *
     * @param phoneChangeTieDto com.medusa.gruul.platform.model.dto.PhoneChangeTieDto
     */
    void phoneChangeTie(PhoneChangeTieDto phoneChangeTieDto);

    /**
     * 账号修改密码
     *
     * @param passChangeTieDto com.medusa.gruul.platform.model.dto.PassChangeTieDto
     */
    void passChangeTie(PassChangeTieDto passChangeTieDto);




    /**
     * 商家登录入口
     *
     * @param tenementLoginDto com.medusa.gruul.platform.model.dto.LoginDto
     * @return com.medusa.gruul.platform.model.vo.AccountInfoVo
     */
    AccountInfoVo login(LoginDto tenementLoginDto);


    /**
     * 获取state值
     *
     * @param code code
     * @return com.medusa.gruul.common.core.util.Result
     */
    Result verifyStateResult(String code);

    /**
     * 忘记密码
     *
     * @param passwordRetrieveDto com.medusa.gruul.platform.model.dto.PasswordRetrieveDto
     */
    void passwordRetrieve(PasswordRetrieveDto passwordRetrieveDto);

    /**
     * 获取用户登录信息
     *
     * @param accountInfo com.medusa.gruul.platform.api.entity.AccountInfo
     * @return com.medusa.gruul.platform.model.vo.AccountInfoVo
     */
    AccountInfoVo getLoginInfoVo(AccountInfo accountInfo);




    /**
     * 修改用户电子发票邮箱接口
     *
     * @param emailChangeDto com.medusa.gruul.platform.model.dto.EmailChangeDto
     */
    void emailChange(EmailChangeDto emailChangeDto);




    /**
     * 校验当前用户相关信息是否正确
     *
     * @param verifyDataDto com.medusa.gruul.platform.model.dto.VerifyDataDto
     * @return 正确返回true  不正确返回false
     */
    Boolean verifyData(VerifyDataDto verifyDataDto);
}
