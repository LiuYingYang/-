package com.medusa.basemall.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.product.entity.Category;
import com.medusa.basemall.product.service.CategoryService;
import com.medusa.basemall.product.vo.CategoryVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.vip.vjtools.vjkit.number.RandomUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Transactional
public class CategoryControllerTest extends BasemallApplicationTests {

	@Resource
	private CategoryService categoryService;

	private static final Integer INIT_PRODUCT_NUMBER = 5;

	@Before
	public void before() {
		for (int i = 0; i < INIT_PRODUCT_NUMBER; i++) {
			saveOrUpdate1();
		}
	}

	private CategoryVo generateCategory() {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setFatherId(-1L);
		categoryVo.setCategoryId(-1L);
		categoryVo.setCategoryIcon(Constant.imgUrl);
		categoryVo.setCategoryName("我是父分类" + RandomUtil.randomAsciiFixLength(5));
		categoryVo.setCategoryType(1);
		categoryVo.setSortIndex(RandomUtil.nextInt(1, 5));
		List<Category> sublist = new ArrayList<>();
		for (int j = 1; j < 3; j++) {
			CategoryVo category = new CategoryVo();
			category.setSortIndex(j);
			category.setCategoryType(2);
			category.setCategoryName("我是子分类" + RandomUtil.randomAsciiFixLength(5));
			category.setCategoryIcon(Constant.imgUrl);
			category.setCategoryId(-1L);
			category.setFatherId(-1L);
			sublist.add(category);
		}
		categoryVo.setSubCategoryList(sublist);
		return categoryVo;
	}

	/**
	 * 新增或更新商品分类
	 */
	@Test
	@Ignore
	public void saveOrUpdate1() {
		List<CategoryVo> categoryList = new ArrayList<>();
		for (int i = 1; i < 3; i++) {
			categoryList.add(generateCategory());
		}
		Map<String, Object> object = new HashMap<>();
		object.put("categoryList", categoryList);
		object.put("appmodelId", Constant.appmodelIdy);
		List<Long> longs = new ArrayList<Long>();
		object.put("categoryIds", longs);
		JSONObject jsonObject = MockMvcUtil
				.sendRequest("/Category/saveOrUpdate/v1", JSON.toJSONString(object), null, "post");
		assertNotNull("返回数据为空", jsonObject);
		assertEquals("请求失败:" + jsonObject.getString("messages"), new Integer(100), jsonObject.getInteger("code"));
	}

	/**
	 * 新增或更新商品分类
	 */
	@Test
	public void saveOrUpdate2() {

		List<CategoryVo> categoryList = categoryService.findFirstAndSecond(Constant.appmodelIdy);
		//更新分类
		categoryList = categoryList.stream().sorted(Comparator.comparing(CategoryVo::getCreateTime).reversed()).limit(3)
				.collect(Collectors.toList());
		Map<Long, String> map = new HashMap<>();
		categoryList.forEach(obj -> {
			String newName = RandomUtil.randomAsciiFixLength(3);
			obj.setCategoryName(newName);
			map.put(obj.getCategoryId(), newName);
		});
		Map<String, Object> object = new HashMap<>();
		object.put("categoryList", categoryList);
		List<Long> categoryIds = new ArrayList<>();
		object.put("categoryIds", categoryIds);
		MockMvcUtil.sendRequest("/Category/saveOrUpdate/v1", JSON.toJSONString(object), null, "post");
		categoryList.forEach(obj -> {
			Category c = categoryService.findById(obj.getCategoryId());
			String expected = map.get(c.getCategoryId());
			assertEquals("分类名修改失败", expected, c.getCategoryName());
		});
		//删除分类
		categoryIds = categoryList.stream().map(Category::getCategoryId).collect(Collectors.toList());
		object.put("categoryIds", categoryIds);
		categoryList.clear();
		object.put("categoryList", categoryList);
		MockMvcUtil.sendRequest("/Category/saveOrUpdate/v1", JSON.toJSONString(object), null, "post");
		for (Long categoryId : categoryIds) {
			Category c = categoryService.findById(categoryId);
			assertNull("分类未正确删除", c);
		}

	}

	/**
	 * 查询商品分类（一级分类和二级分类）
	 */
	@Test
	public void findFirstAndSecond() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("appmodelId", Constant.appmodelIdy);
		JSONObject jsonObject = MockMvcUtil.sendRequest("/Category/findFirstAndSecond/v1", "", map, "get");
		Integer code = jsonObject.getInteger("code");
		assertEquals("请求失败:" + jsonObject.getString("messages"), new Integer(100), code);
	}

	@Test
	public void findCatogoryAndProductTest() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("appmodelId", Constant.appmodelIdy);
		map.add("pageNum", "4");
		map.add("pageSize", "10");
		JSONObject jsonObject = MockMvcUtil.sendRequest("/Category/findCatogoryAndProduct/v1", "", map, "get");
		Integer code = jsonObject.getInteger("code");
		assertEquals("请求失败:" + jsonObject.getString("messages"), new Integer(100), code);
	}
}
