package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.shop.dao.PlateMapper;
import com.medusa.basemall.shop.dao.PlateProductMapper;
import com.medusa.basemall.shop.entity.Plate;
import com.medusa.basemall.shop.vo.PlateSortVO;
import com.medusa.basemall.shop.vo.PlateVO;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;

public class PlateControllerTest extends BasemallApplicationTests {

    @Resource
    PlateMapper plateMapper;

    @Resource
    PlateProductMapper plateProductMapper;

    /**
     * 新增商品展示区测试
     */
    @Test
    public void saveTest() {
        Plate plate = new Plate();
        plate.setPlateName("test1");
        plate.setPlateImg("xxx.jpg");
        plate.setRemark("简介");
        plate.setAppmodelId(Constant.appmodelIdy);
        plate.setProductIds("1534402482117743,1534402533009687");
        JSONObject post = MockMvcUtil.sendRequest("/Plate/save/v1",
                JSONObject.toJSONString(plate), null, "post");
        Assert.assertEquals("已达上限，无法添加",post.getString("message"));
        Assert.assertEquals(5,plateMapper.findByAppmodelId(Constant.appmodelIdy).size());
    }

    /**
     * 更新商品展示区
     */
    @Test
    public void updateTest() {
        Plate plate = plateMapper.selectByPrimaryKey(7);
        plate.setProductIds("1533519966597908");
        JSONObject post = MockMvcUtil.sendRequest("/Plate/update/v1",
                JSONObject.toJSONString(plate), null, "put");

        Assert.assertEquals(1,plateProductMapper.seleteByPlateId(7).size());
    }

    /**
     * 根据appmodelId查询商品展示区
     */
    @Test
    public void findByAppmodelIdTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId",Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/Plate/findByAppmodelId/v1",
                "", map, "get");

        Assert.assertEquals(5,plateMapper.findByAppmodelId(Constant.appmodelIdy).size());
    }

    /**
     * 批量开启或关闭测试
     */
    @Test
    public void batchUpdateTest() {
        PlateVO plateVO = new PlateVO();
        plateVO.setPlateIds("1,3,5");
        plateVO.setPlateFlag(true);
        JSONObject post = MockMvcUtil.sendRequest("/Plate/batchUpdate/v1",
                JSON.toJSONString(plateVO), null, "put");
    }

    /**
     * 批量删除商品展示区
     */
    @Test
    public void batchDeleteTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("plateIds", "1,3");
        JSONObject post = MockMvcUtil.sendRequest("/Plate/batchDelete/v1",
                "", map, "delete");

        Assert.assertEquals(3,plateMapper.findByAppmodelId(Constant.appmodelIdy).size());
    }

    /**
     * 海报排序操作
     */
    @Test
    public void sort() {
        PlateSortVO sortVo = new PlateSortVO();
        sortVo.setAppmodelId(Constant.appmodelIdy);
        sortVo.setHandleType(2);
        sortVo.setPlateId(7);
        JSONObject post = MockMvcUtil.sendRequest("/Plate/sort/v1",
                JSONObject.toJSONString(sortVo), null, "put");

        Assert.assertEquals("上移成功",post.getString("data"));
        Assert.assertSame(1,plateMapper.selectByPrimaryKey(7).getSort());
    }

}
