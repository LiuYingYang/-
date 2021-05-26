package com.medusa.basemall.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.product.entity.LogisticCancel;
import com.medusa.basemall.product.entity.LogisticCharge;
import com.medusa.basemall.product.entity.LogisticDistrobution;
import com.medusa.basemall.product.entity.LogisticFree;
import com.medusa.basemall.product.vo.LogisticModelVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogisticControllerTest extends BasemallApplicationTests {


    /**
     * 添加更新商家配送
     */
    @Test
    public void saveOrUpdateDistrobution() {

        LogisticDistrobution distrobution = new LogisticDistrobution();

        distrobution.setDistrobutionId(1);
        distrobution.setShopAddress("{\"address\":[\"浙江省\",\"宁波市\",\"江北区\",\"长兴路158号\"],\"latLng\":{\"lat\":29.93784,\"lng\":121.49234}}");
        distrobution.setMinPayPrice(RegexMatches.getRadomDouble());
        distrobution.setDeliveryRange(RegexMatches.getInt(20, 10));
        distrobution.setSellerPrice(RegexMatches.getRadomDouble());
        distrobution.setTurnState(true);
        distrobution.setAppmodelId(Constant.appmodelId);
        distrobution.setDeliveryStaffs("[{\"name\":\"测试严\",\"phone\":\"17682454582\"},{\"name\":\"测试严2\",\"phone\":\"17682454582\"}]");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        JSONObject post = MockMvcUtil.sendRequest("/Logistic/saveOrUpdateDistrobution", JSON.toJSONString(distrobution), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 查询商家配送
     */
    @Test
    public void findDistrobution() {


        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, String> object = new HashMap<>();
        object.put("appmodelId", Constant.appmodelId);

        JSONObject post = MockMvcUtil.sendRequest("/Logistic/findDistrobution", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 添加更新物流模板，包含计价和包邮
     */
    @Test
    public void saveOrUpdateModel() {

        LogisticModelVo logisticModel = new LogisticModelVo();
        logisticModel.setLogisticModelId(-1);
        logisticModel.setAppmodelId(Constant.appmodelId);
        logisticModel.setFreeState(true);
        logisticModel.setLogisticModelName("物流模板2");
        logisticModel.setValuationType(1);

        List<LogisticCharge> chargeList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            LogisticCharge charge = new LogisticCharge();
            charge.setLogisticModelId(-1);
            charge.setLogisticChargeId(-1);
            charge.setChargeAddress(RegexMatches.getRoad());

            charge.setFirstPrice(RegexMatches.getRadomDouble());
            charge.setFirstWeight(RegexMatches.getInt(20, 10));
            charge.setNextPrice(RegexMatches.getRadomDouble());
            charge.setNextWeight(RegexMatches.getInt(20, 10));
            if (i == 0)
                charge.setDefaultState(true);
            else
                charge.setDefaultState(false);
            chargeList.add(charge);
        }

        List<LogisticFree> freeList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LogisticFree free = new LogisticFree();
            free.setLogisticModelId(-1);
            free.setFreeId(-1);
            free.setConditionShip(1);
            free.setMaxPrice(RegexMatches.getRadomDouble());
            free.setFreeAddress(RegexMatches.getRoad());
            free.setUnitType(2);

            freeList.add(free);
        }

        logisticModel.setChargeList(chargeList);
        logisticModel.setFreeList(freeList);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/Logistic/saveOrUpdateModel", JSON.toJSONString(logisticModel), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 查询物流模板
     */
    @Test
    public void findModelByAppmodelId() {


        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, String> object = new HashMap<>();
        object.put("appmodelId", Constant.appmodelId);

        JSONObject post = MockMvcUtil.sendRequest("/Logistic/findModelByAppmodelId", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 查询物流模板（包含计价和包邮）
     */
    @Test
    public void findModelByAppmodelIdDetail() {


        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, String> object = new HashMap<>();
        object.put("appmodelId", Constant.appmodelId);

        JSONObject post = MockMvcUtil.sendRequest("/Logistic/findModelByAppmodelIdDetail", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 开启或关闭物流模板
     */
    @Test
    public void openOrCloseModel() {


        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, Object> object = new HashMap<>();
        object.put("appmodelId", Constant.appmodelId);
        object.put("turnState", false);

        JSONObject post = MockMvcUtil.sendRequest("/Logistic/openOrCloseModel", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 删除物流模板
     */
    @Test
    public void deleteModelById() {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, Object> object = new HashMap<>();
        object.put("logisticModelId", 4);

        JSONObject post = MockMvcUtil.sendRequest("/Logistic/deleteModelById", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 保存或更新退货地址
     */
    @Test
    public void saveOrUpdateLogisticCancel() {
        LogisticCancel logisticCancel = new LogisticCancel();
        logisticCancel.setLogisticCancelId(-1);
        logisticCancel.setAppmodelId(Constant.appmodelId);
        logisticCancel.setDefaultState(true);
        logisticCancel.setLocationJson(JSON.toJSONString(RegexMatches.getRoad()));
        logisticCancel.setPhone(RegexMatches.getTel());
        logisticCancel.setUserName("商家姓名");
        logisticCancel.setPostCode("636000");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/Logistic/saveOrUpdateLogisticCancel", JSON.toJSONString(logisticCancel), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 删除退货地址
     */
    @Test
    public void deleteLogisticCancel() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, Object> object = new HashMap<>();
        object.put("logisticCancelId", 1);
        JSONObject post = MockMvcUtil.sendRequest("/Logistic/deleteLogisticCancel", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    /*
     * 查询退货地址
     * */
    @Test
    public void findLogisticCancel() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, String> object = new HashMap<>();
        object.put("appmodelId", Constant.appmodelId);
        JSONObject post = MockMvcUtil.sendRequest("/Logistic/findLogisticCancel", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    @Test
    public void findWLMsg() {
        Map<String, Object> map = new HashMap<>();
        map.put("wlCode", "ANE");
        map.put("wlNum", "210001633605");
        JSONObject post = MockMvcUtil.sendRequest("/Logistic/findWLMsg", JSON.toJSONString(map), null, "post");
    }

}
