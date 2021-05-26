package com.medusa.basemall.shop.serviceimpl;

import com.medusa.basemall.constant.QuantitativeRestriction;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.shop.dao.FirstpageClassifyMapper;
import com.medusa.basemall.shop.entity.FirstpageClassify;
import com.medusa.basemall.shop.service.FirstpageClassifyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Created by wx on 2018/06/04.
 */

@Service

public class FirstpageClassifyServiceImpl extends AbstractService<FirstpageClassify> implements FirstpageClassifyService {
    @Resource
    private FirstpageClassifyMapper tFirstpageClassifyMapper;

    @Override
    public List<FirstpageClassify> findByAppmodelId(String appmodelId) {
        List<FirstpageClassify> firstpageClassifies =  tFirstpageClassifyMapper.findByAppmodelId(appmodelId);
        if (firstpageClassifies.size() != 0) {
            return firstpageClassifies;
        }
        for (int i = 0; i < QuantitativeRestriction.FIRSTPAGECLASSIFYLOWERLIMIT; i++) {
            FirstpageClassify firstpageClassify = new FirstpageClassify();
            firstpageClassify.setClassifyName("默认分类导航" + (i + 1));
            firstpageClassify.setSort(i + 1);
            firstpageClassify.setAppmodelId(appmodelId);
            firstpageClassifies.add(firstpageClassify);
        }
        tFirstpageClassifyMapper.insertList(firstpageClassifies);
        return tFirstpageClassifyMapper.findByAppmodelId(appmodelId);
    }
}
