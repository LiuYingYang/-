package com.medusa.basemall.shop.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.shop.dao.SearchWordMapper;
import com.medusa.basemall.shop.entity.SearchWord;
import com.medusa.basemall.shop.service.SearchWordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author Created by wx on 2018/05/25.
 *
 */

@Service

public class SearchWordServiceImpl extends AbstractService<SearchWord> implements SearchWordService {
    @Resource
    private SearchWordMapper tSearchWordMapper;

    @Override
    public int batchDelete(String[] searchWordId) {
        return tSearchWordMapper.batchDelete(searchWordId);
    }

    @Override
    public List<SearchWord> findByAppmodelId(String appmodelId) {
        return tSearchWordMapper.findByAppmodelId(appmodelId);
    }

    @Override
    public List<SearchWord> findByType(Map<String, Object> map) {
        return tSearchWordMapper.findByType(map);
    }
}
