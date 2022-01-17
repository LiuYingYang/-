package com.medusa.gruul.platform.web.controller;

import com.medusa.gruul.common.core.annotation.EscapeLogin;
import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.platform.model.dto.*;
import com.medusa.gruul.platform.model.vo.AccountInfoVo;
import com.medusa.gruul.platform.model.vo.LoginAccountInfoDetailVo;
import com.medusa.gruul.platform.service.IAccountInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 平台用户表 前端控制器
 * </p>
 *
 * @author whh
 * @since 2019-09-07
 */
@RestController
@Api(hidden = true)
@RequestMapping("/account-info")
public class AccountInfoController {


    @Autowired
    private IAccountInfoService accountInfoService;



    @GetMapping("/checkout/account")
    @EscapeLogin
    @ApiOperation(value = "账号校验是否存在", tags = "接口")
    public Result checkoutAccount(@ApiParam(value = "手机") @RequestParam String phone,
                                  @ApiParam(value = "type=1 校验账号存在  type=2校验账号不存在 默认使用1 ") @RequestParam(defaultValue = "1") Integer type) {
        accountInfoService.checkoutAccount(phone, type);
        return Result.ok();
    }


    @PostMapping("/pre/account/verify")
    @EscapeLogin
    @ApiOperation(value = "预扫码接口,返回二维码url,url中会带上state参数供回调接口入口使用", tags = "接口")
    public Result preAccountScanCode(@RequestBody @Validated PreAccountVerifyDto preAccountVerifyDto) {
        String url = accountInfoService.preAccountScanCode(preAccountVerifyDto);
        return Result.ok(url);
    }

    @GetMapping("/account/verify/notify")
    @EscapeLogin
    @ApiOperation(value = "用户扫码回调统一入口(网站应用扫码成功跳转地址),回调成功会生成code返回", tags = "接口")
    public void accountScanCodeNotify(@ApiParam(value = "微信扫码返回的codo") @RequestParam String code, @ApiParam(value = "预扫码返回的state") @RequestParam String state, HttpServletResponse response) {
        accountInfoService.accountScanCodeNotify(code, state, response);
    }

    @GetMapping("/verify/state/result")
    @EscapeLogin
    @ApiOperation(value = "扫码回调成功,根据code获取指定场景值回调结束后的结果", tags = "接口")
    public Result verifyStateResult(@ApiParam(value = "预扫码返回的code") @RequestParam String code) {
        return accountInfoService.verifyStateResult(code);
    }

    @GetMapping("/info")
    @ApiOperation(value = "根据请求token获取当前用户最新的信息", tags = "接口")
    public Result<LoginAccountInfoDetailVo> info() {
        LoginAccountInfoDetailVo accountInfoVo = accountInfoService.info();
        return Result.ok(accountInfoVo);
    }

    @PutMapping("/verify/data")
    @ApiOperation(value = "校验当前用户相关信息是否正确，正确返回true  不正确返回false", tags = "接口")
    public Result<Boolean> verifyData(@RequestBody @Validated VerifyDataDto verifyDataDto) {
        Boolean flag = accountInfoService.verifyData(verifyDataDto);
        return Result.ok(flag);
    }

    @PutMapping("/email/change")
    @ApiOperation(value = "修改电子发票邮箱", tags = "接口")
    public Result emailChange(@RequestBody @Validated EmailChangeDto emailChangeDto) {
        accountInfoService.emailChange(emailChangeDto);
        return Result.ok();
    }


    @PutMapping("/phone/change/tie")
    @ApiOperation(value = "手机号换绑", tags = "接口")
    public Result phoneChangeTie(@RequestBody PhoneChangeTieDto phoneChangeTieDto) {
        accountInfoService.phoneChangeTie(phoneChangeTieDto);
        return Result.ok();
    }

    @PutMapping("/pass/change/tie")

    @ApiOperation(value = "修改密码", tags = "接口")
    public Result passChangeTie(@RequestBody PassChangeTieDto passChangeTieDto) {
        accountInfoService.passChangeTie(passChangeTieDto);
        return Result.ok();
    }



    @PostMapping("/login-v1")
    @EscapeLogin
    @ApiOperation(value = "登录接口", tags = {"账号相关", "商户相关接口"})
    public Result<AccountInfoVo> login1(@RequestBody @Validated LoginDto tenementLoginDto) {
        AccountInfoVo accountInfoVo = accountInfoService.login(tenementLoginDto);
        return Result.ok(accountInfoVo);
    }


    @PostMapping("/password-retrieve")
    @EscapeLogin
    @ApiOperation(value = "重置密码接口(目前仅限商户账号修改,商户子账号无法修改)", tags = {"账号相关", "商户相关接口"})
    public Result passwordRetrieve(@RequestBody @Validated PasswordRetrieveDto passwordRetrieveDto) {
        accountInfoService.passwordRetrieve(passwordRetrieveDto);
        return Result.ok();
    }

}
