package com.medusa.gruul.account.web.remote;

import com.medusa.gruul.account.api.model.*;
import com.medusa.gruul.account.service.*;
import com.medusa.gruul.common.core.annotation.EscapeLogin;
import com.medusa.gruul.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author whh
 * @description
 * @data: 2019/11/29
 */
@RestController(value = "remoteMiniAccount")
public class RemoteMiniAccount {

    @Autowired
    private IMiniAccountService miniAccountService;
    @Autowired
    private IMiniAccountExtendsService miniAccountExtendsService;

    /**
     * 修改用户扩展字段部分数据
     *
     * @param userId                      用户id
     * @param miniAccountExtendsUpdateDto com.medusa.gruul.account.api.model.MiniAccountExtendsUpdateDto
     * @return java.lang.Boolean
     */
    @RequestMapping(value = "/portion/attribute/modify/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户扩展字段部分数据")
    @EscapeLogin
    public Boolean portionAttributeModify(@PathVariable(value = "userId", required = true) @NotNull(message = "用户id不能为null") String userId,
                                          @RequestBody @NotNull(message = "修改数据不能为null") MiniAccountExtendsUpdateDto miniAccountExtendsUpdateDto) {
        return miniAccountExtendsService.portionAttributeModify(userId, miniAccountExtendsUpdateDto);
    }


    /**
     * 获取用户信息接口
     *
     * @param shopUserId 用户id
     * @param infos      [1,2,3,4]  1,基本信息,2,扩展信息,3-地址信息,4-授权信息   需要哪些发哪些 list
     * @return com.medusa.gruul.account.api.model.AccountInfoDto
     */
    @RequestMapping(value = "/account/info/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息,注意判空")
    @EscapeLogin
    public AccountInfoDto accountInfo(@PathVariable(value = "userId", required = true) @NotNull(message = "用户id不能为null") String shopUserId,
                                      @RequestParam(value = "infos", required = true) @NotNull(message = "获取数据数组不能为空") List<Integer> infos) {
        return miniAccountService.accountInfo(shopUserId, infos);
    }




    /**
     * 批量获取指定用户id(用户店铺id)的用户基本信息
     *
     * @param shopUserId 用户id数组
     * @return com.medusa.gruul.account.api.model.AccountInfoDto
     */
    @EscapeLogin
    @RequestMapping(value = "/accounts/info", method = RequestMethod.GET)
    @ApiOperation(value = "批量获取指定用户id(用户店铺id)的用户基本信息")
    public List<MiniAccountExtDto> accountsInfoList(
            @RequestParam(value = "shopUserId", required = true) @NotNull(message = "店铺用户id不能为null") List<String> shopUserId) {
        return miniAccountService.accountsInfoList(shopUserId);
    }
}
