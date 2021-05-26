package com.medusa.basemall.shop.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.shop.dao.FooterMapper;
import com.medusa.basemall.shop.entity.Footer;
import com.medusa.basemall.shop.service.FooterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Created by wx on 2018/06/04.
 */

@Service

public class FooterServiceImpl extends AbstractService<Footer> implements FooterService {
	@Resource
	private FooterMapper tFooterMapper;

	@Override
	public List<Footer> findByAppmoedelId(String appmodelId) {
		List<Footer> footers = tFooterMapper.findByAppmoedelId(appmodelId);
		return footers;
	}
}
