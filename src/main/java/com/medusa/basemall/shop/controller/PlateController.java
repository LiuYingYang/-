package com.medusa.basemall.shop.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.HandType;
import com.medusa.basemall.constant.QuantitativeRestriction;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.shop.dao.PlateMapper;
import com.medusa.basemall.shop.entity.Plate;
import com.medusa.basemall.shop.entity.PlateProduct;
import com.medusa.basemall.shop.service.PlateProductService;
import com.medusa.basemall.shop.service.PlateService;
import com.medusa.basemall.shop.vo.PlateSortVO;
import com.medusa.basemall.shop.vo.PlateVO;
import com.medusa.basemall.utils.TimeUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 商品展示区
 *
 * @author Created by wx on 2018/05/26.
 */
@Api(tags = "所有接口")
@RequestMapping("/Plate")
@RestController
@VersionManager
public class PlateController {
    @Autowired
    private PlateService plateService;
    @Autowired
    private PlateProductService plateProductService;
    @Autowired
    private ProductService productService;
    @Resource
    private PlateMapper plateMapper;

    @ApiOperation(value = "新增商品展示区", tags = "添加接口")
    @PostMapping("/v1/save")
    public Result save(@RequestBody Plate plate) {
        List<Plate> plates = plateService.findByAppmodelId(plate.getAppmodelId());
        if (plates.size() >= QuantitativeRestriction.PLATEUPPERLIMIT) {
            return ResultGenerator.genFailResult("已达上限，无法添加");
        }
        String productIds = plate.getProductIds();
        if ("".equals(productIds) || productIds == null) {
            return ResultGenerator.genSuccessResult("必须添加展示商品");
        }
        String[] productId = productIds.split(",");
        plate.setCreateTime(TimeUtil.getNowTime());
        plate.setPlateFlag(true);
        plate.setProductNum(productId.length);
        if (plates.size() > 0) {
            for (Plate plateNew : plates) {
                plateNew.setSort(plateNew.getSort() + 1);
                plateService.update(plateNew);
            }
        }
        plate.setSort(1);
        plateService.save(plate);

        for (int i = 0; i < productId.length; i++) {
            PlateProduct plateProduct = new PlateProduct();
            plateProduct.setCreateTime(TimeUtil.getNowTime());
            plateProduct.setPlateId(plate.getPlateId());
            plateProduct.setAppmodelId(plate.getAppmodelId());
            plateProduct.setProductId(Long.valueOf(productId[i]));
            plateProductService.save(plateProduct);
        }
        return ResultGenerator.genSuccessResult("添加成功");
    }

    @ApiOperation(value = "更新商品展示区", tags = "更新接口")
    @PutMapping("/v1/update")
    public Result update(@RequestBody Plate plate) {
        String productIds = plate.getProductIds();
        if ("".equals(productIds) || productIds == null)  {
            return ResultGenerator.genSuccessResult("必须添加展示商品");
        }
        String[] productId = productIds.split(",");
        plateProductService.deleteByPlateId(plate.getPlateId());
        for (int i = 0; i < productId.length; i++) {
            PlateProduct plateProduct = new PlateProduct();
            plateProduct.setCreateTime(TimeUtil.getNowTime());
            plateProduct.setPlateId(plate.getPlateId());
            plateProduct.setAppmodelId(plate.getAppmodelId());
            plateProduct.setProductId(Long.valueOf(productId[i]));
            plateProductService.save(plateProduct);
        }
        plate.setProductNum(productId.length);
        plateService.update(plate);
        return ResultGenerator.genSuccessResult("更新成功");

    }

    @ApiOperation(value = "根据appmodelId查询商品展示区", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = Plate.class, responseContainer = "List"),})
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(String appmodelId) {
        List<Plate> list = plateService.findByAppmodelId(appmodelId);
        return ResultGenerator.genSuccessResult(list);

    }

    @ApiOperation(value = "开启或关闭(可批量)", tags = "更新接口")
    @PutMapping("/v1/batchUpdate")
    public Result batchUpdate(@RequestBody PlateVO plateVO) {
        String[] plateId = plateVO.getPlateIds().split(",");
        plateVO.setPlateId(plateId);
        int result = plateService.batchUpdate(plateVO);
        if (result > 0) {
            return ResultGenerator.genSuccessResult("操作成功");
        } else {
            return ResultGenerator.genFailResult("操作失败");
        }
    }

    @ApiOperation(value = "删除商品展示区(可批量)", tags = "删除接口")
    @DeleteMapping("/v1/batchDelete")
    public Result batchDelete(@ApiParam(value = "商品展示区id字符串")@RequestParam String plateIds) {
        String[] plateId = plateIds.split(",");
        int result = plateService.batchDelete(plateId);
        if (result > 0) {
            return ResultGenerator.genSuccessResult("删除成功");
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }


    @ApiOperation(value = "商品展示区排序", tags = "更新接口")
    @PutMapping("/v1/sort")
    public Result sort(@RequestBody PlateSortVO plateSortVO) {
        Plate plate = plateService.findById(plateSortVO.getPlateId());
        if (plateSortVO.getHandleType().equals(HandType.TOP)) {
            List<Plate> plates = plateMapper.findByAppmodelIdDesc(plateSortVO.getAppmodelId());
            if (plates.size() > 1) {
                for (Plate plateNew : plates) {
                    if (plate.getSort() > plateNew.getSort()) {
                        change(plate, plateNew);
                    }
                }
                return ResultGenerator.genSuccessResult("置顶成功");
            }
        }
        if (plateSortVO.getHandleType().equals(HandType.FOOT)) {
            List<Plate> plates = plateMapper.findByAppmodelId(plateSortVO.getAppmodelId());
            if (plates.size() > 1) {
                for (Plate plateNew : plates) {
                    if (plateNew.getSort() > plate.getSort()) {
                        change(plate, plateNew);
                    }
                }
                return ResultGenerator.genSuccessResult("置底成功");
            }
        }
        if (plateSortVO.getHandleType().equals(HandType.UP)) {
            List<Plate> plates = plateMapper.findByAppmodelIdDesc(plateSortVO.getAppmodelId());
            if (plates.size() > 1) {
                for (Plate plateNew : plates) {
                    if (plate.getSort() > plateNew.getSort()) {
                        change(plate, plateNew);
                        return ResultGenerator.genSuccessResult("上移成功");
                    }
                }
            }
        }
        if (plateSortVO.getHandleType().equals(HandType.DOWN)) {
            List<Plate> plates = plateMapper.findByAppmodelId(plateSortVO.getAppmodelId());
            if (plates.size() > 1) {
                for (Plate plateNew : plates) {
                    if (plateNew.getSort() > plate.getSort()) {
                        change(plate, plateNew);
                        return ResultGenerator.genSuccessResult("下移成功");
                    }
                }
            }
        }
        return ResultGenerator.genSuccessResult("操作没有生效");
    }

    private void change(Plate plate, Plate plateNew) {
        Integer sortNew = plateNew.getSort();
        plateNew.setSort(plate.getSort());
        plateService.update(plateNew);
        plate.setSort(sortNew);
        plateService.update(plate);
    }
}
