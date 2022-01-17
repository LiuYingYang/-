

package com.medusa.gruul.shops.api.feign;

import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.shops.api.entity.ShopsPartner;
import com.medusa.gruul.shops.api.model.AccountCenterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * @author create by zq
 * @date created in 2019/11/15
 */
@FeignClient(value = "shops-open")
public interface RemoteShopsService {

    /**
     * 根据平台用户id获取店铺信息
     *
     * @param platformId 平台用户id
     * @return Result
     */
    @RequestMapping(value = "/get/platform_id/{platformId}", method = RequestMethod.GET)
    ShopsPartner getByPlatformId(@PathVariable(name = "platformId") Long platformId);

//    /**
//     * 新增默认店铺
//     *
//     * @param pass 密码
//     * @param phone 手机号
//     * @param platformId 平台用户id
//     * @return Result
//     */
//    @RequestMapping(value = "/shops_partner/save", method = RequestMethod.GET)
//    Result<ShopsPartner> save(@RequestParam(value = "pass") @NotNull String pass,
//                @RequestParam(value = "phone") @NotNull String phone,
//                @RequestParam(value = "platformId") @NotNull Long platformId);
//
//
//    /**
//     * 获取店铺
//     *
//     * @return ShopsPartner
//     */
//    @RequestMapping(value = "/shops_partner/one", method = RequestMethod.GET)
//    ShopsPartner oneByShopId();
//

    /**
     * 获取店铺
     *
     * @return ShopsPartner
     */
    @RequestMapping(value = "/feign/account-center", method = RequestMethod.GET)
    AccountCenterVo accountCenterSetting();

    /**
     *专区删除时匹配删除 t_shop_renovation_page info
     *
     * @param modelId 专区id
     * @return 删除结果
     */
    @RequestMapping(value = "/del/shopRenovationPage", method = RequestMethod.DELETE)
    Boolean delShopRenovationPage(@RequestParam(value = "modelId",required = true) @NotNull(message="modelId不能为null") String modelId);

    /**
     * 修改专修专区名称
     * @param linkName 专区原名称
     * @param newLinkName 专区现名称
     * @return
     */
    @RequestMapping(value = "/feign/updateSpecialArea", method = RequestMethod.GET)
    boolean updateSpecialArea(@RequestParam(value = "linkName",required = true)String linkName,
                                      @RequestParam(value = "newLinkName",required = true)String newLinkName);


}
