package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.ParameterClassMapper;
import com.medusa.basemall.product.dao.ParameterMapper;
import com.medusa.basemall.product.entity.Parameter;
import com.medusa.basemall.product.service.ParameterService;
import com.medusa.basemall.product.vo.ParameterVo;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by psy on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service
@Transactional
public class ParameterServiceImpl extends AbstractService<Parameter> implements ParameterService {

    @Resource
    private ParameterClassMapper parameterClassMapper;

    @Resource
    private ParameterMapper parameterMapper;

    @Override
    public int saveOrUpdate(ParameterVo parameterVo) {

        //保存或更新属性
        int rs = 0;
        if (parameterVo.getParamClassId() == -1) {
            parameterVo.setParamClassId(null);
            parameterVo.setCreateTime(TimeUtil.getNowTime());
            rs += parameterClassMapper.insertSelective(parameterVo);
        } else {
            rs += parameterClassMapper.updateByPrimaryKeySelective(parameterVo);
            //删除之前的参数
            Parameter p = new Parameter();
            p.setParamClassId(parameterVo.getParamClassId());
            parameterMapper.delete(p);
        }

        List<Parameter> list = parameterVo.getParameterList();
        if (list != null) {
            list.forEach(parameter -> {
                parameter.setParamId(null);
                parameter.setAppmodelId(parameterVo.getAppmodelId());
                parameter.setParamClassId(parameterVo.getParamClassId());
            });
            rs += parameterMapper.insertList(list);
        }

        return rs;
    }

    /**
     * 查询商品属性
     *
     * @param appmodelId
     * @return
     */
    @Override
    public List<ParameterVo> findByAppmodelId(String appmodelId) {
        return parameterClassMapper.findByAppmodelId(appmodelId);
    }

    /**
     * 删除商品属性模板
     *
     * @param paramClassId
     * @return
     */
    @Override
    public int deleteByParamClassId(Integer paramClassId) {

        Parameter parameter = new Parameter();
        parameter.setParamClassId(paramClassId);
        int rs = parameterMapper.delete(parameter);

        rs += parameterClassMapper.deleteByPrimaryKey(paramClassId);

        return rs;
    }
}
