package com.medusa.basemall.promotion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.vo.ProductWxVo;
import com.medusa.basemall.promotion.vo.ActivitySeckillVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.TimeUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitySpecialControllerTest extends BasemallApplicationTests {

	/**
     * 查询可供特价选择的商品测试
     */
    @Test
    public void findProductForSpecialTest() {
        Map<String, Object> object = new HashMap<>();
        object.put("pageNum", 1);
        object.put("pageSize", 10);
        object.put("appmodelId", Constant.appmodelId);
        /*object.put("startDate", TimeUtil.getNowTime());*/
        object.put("startDate", TimeUtil.getMonth(4));
        object.put("endDate", TimeUtil.getMonth(5));
        object.put("overlayState", true);
        JSONObject post = MockMvcUtil.sendRequest("/ActivitySpecial/findProductForSpecial",
                JSON.toJSONString(object), null, "post");
    }

    /**
     * 创建特价活动测试
     */
    @Test
    public void save() {

        List<ProductWxVo> productWxVoList = new ArrayList<>();

        ProductWxVo productWxVo = new ProductWxVo();
        /* productWxVo.setActivityPrice(2.00);*/
        productWxVo.setProductId(1530511620853900L);
        productWxVo.setActivityDiscount(0.60);
        productWxVo.setMaxQuantity(10);
        /*  productWxVo.setActivityStock(300);*/


        List<ProductSpecItem> productSpecItemList = new ArrayList<>();
        ProductSpecItem productSpecItem1 = new ProductSpecItem();
        productSpecItem1.setProductSpecItemId(1530511621208330L);
        productSpecItem1.setActivityStock(10);
        ProductSpecItem productSpecItem2 = new ProductSpecItem();
        productSpecItem2.setProductSpecItemId(1530511621255946L);
        productSpecItem2.setActivityStock(20);
   /*     ProductSpecItem productSpecItem3 = new ProductSpecItem();
        productSpecItem3.setProductSpecItemId(1530260770493763L);
        productSpecItem3.setActivityStock(300);
        ProductSpecItem productSpecItem4 = new ProductSpecItem();
        productSpecItem4.setProductSpecItemId(1530260770503681L);
        productSpecItem4.setActivityStock(400);*/
        productSpecItemList.add(productSpecItem1);
        productSpecItemList.add(productSpecItem2);
       /* productSpecItems.add(productSpecItem3);
        productSpecItems.add(productSpecItem4);*/
        productWxVo.setProductSpecItemList(productSpecItemList);
        productWxVoList.add(productWxVo);

        ActivitySeckillVo activitySeckillVo = new ActivitySeckillVo();
       // activitySeckillVo.setProductList(productWxVoList);
        activitySeckillVo.setAppmodelId(Constant.appmodelId);
        activitySeckillVo.setActivitySeckillName("秒杀活动最新有规格");
        activitySeckillVo.setActivityImg(Constant.imgUrl);
        activitySeckillVo.setActivityRemark("秒杀备注最新");
        String start = TimeUtil.DATETIMEFORMAT.format((System.currentTimeMillis() + 30000));
        activitySeckillVo.setStartDate(start);
        String end = TimeUtil.DATETIMEFORMAT.format(System.currentTimeMillis() + 60000);
        activitySeckillVo.setEndDate(end);
        activitySeckillVo.setOverlayState(false);

        JSONObject post = MockMvcUtil.sendRequest("/ActivitySpecial/save",
                JSON.toJSONString(activitySeckillVo), null, "post");
    }

}
