package com.medusa.basemall.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.product.entity.Specification;
import com.medusa.basemall.product.vo.SpecificationVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

public class SpecificationControllerTest extends BasemallApplicationTests {

    /**
     * 添加或更新规格分类
     */
    @Test
    public void saveOrUpdateSpecificationClass() {

        SpecificationVo specificationVo = new SpecificationVo();
        specificationVo.setSpecificationClassId(-1);
        specificationVo.setName("尺寸");
        specificationVo.setAppmodelId(Constant.appmodelId);
        List<Specification> list = new ArrayList<>();
        for (int i = 4; i < 6; i++) {
            Specification specification = new Specification();
            /*specification.setSpecificationClassId(4);*/
            specification.setSpecificationId(i);
            specification.setName(i * 10 + "CM");
            list.add(specification);
        }
        specificationVo.setSpecificationList(list);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/Specification/saveOrUpdateSpecificationClass", JSON.toJSONString(specificationVo), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 查询所有规格
     */
    @Test
    public void findSpecificationClassByAppmodelId() {

        SpecificationVo specificationVo = new SpecificationVo();
        specificationVo.setAppmodelId(Constant.appmodelId);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/Specification/findSpecificationClassByAppmodelId", JSON.toJSONString(specificationVo), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 删除规格分类
     */
    @Test
    public void deleteSpecificationClassById() {

        SpecificationVo specificationVo = new SpecificationVo();
        specificationVo.setSpecificationClassId(3);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/Specification/deleteSpecificationClassById", JSON.toJSONString(specificationVo), map, "post");
        System.out.println(post.toJSONString());
    }
}
