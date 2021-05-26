package com.medusa.basemall.product.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.product.entity.Wlcompany;
import com.medusa.basemall.product.service.WlcompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author psy
 * @date 2018/05/24
 */
@RestController
@RequestMapping("/wlcompany")
@VersionManager
public class WlcompanyController {
    @Resource
    private WlcompanyService wlcompanyService;

	@GetMapping("/v1/list")
	public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
        List<Wlcompany> list = wlcompanyService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
