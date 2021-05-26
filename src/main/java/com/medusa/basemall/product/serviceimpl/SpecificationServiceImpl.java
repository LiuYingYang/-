package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.SpecificationClassMapper;
import com.medusa.basemall.product.dao.SpecificationMapper;
import com.medusa.basemall.product.entity.Specification;
import com.medusa.basemall.product.entity.SpecificationClass;
import com.medusa.basemall.product.service.SpecificationService;
import com.medusa.basemall.product.vo.SpecificationVo;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;


/**
 * Created by psy on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service
@Transactional
public class SpecificationServiceImpl extends AbstractService<Specification> implements SpecificationService {

    @Resource
    private SpecificationClassMapper specificationClassMapper;

    @Resource
    private SpecificationMapper specificationMapper;

    /**
     * 添加或更新规格分类
     *
     * @param specificationVo
     * @return
     */
    @Override
    public int saveOrUpdateSpecificationClass(SpecificationVo specificationVo) {

        int rs = 0;
        //新增规格分类
        if (specificationVo.getSpecificationClassId() == -1) {
            specificationVo.setSpecificationClassId(null);
            specificationVo.setCreateTime(TimeUtil.getNowTime());
            rs += specificationClassMapper.insertSelective(specificationVo);
            //新增规格
            List<Specification> list = specificationVo.getSpecificationList();
            if (list != null) {
                list.forEach(specification -> {
                    specification.setSpecificationId(null);
                    specification.setDeleteState(false);
                    specification.setAppmodelId(specificationVo.getAppmodelId());
                    specification.setSpecificationClassId(specificationVo.getSpecificationClassId());
                });
                rs += specificationMapper.insertList(list);
            }
        } else {
            rs += update(specificationVo);
        }

        return rs;
    }

    /**
     * 查询所有规格
     *
     * @param appmodelId
     * @return
     */
    @Override
    public List<SpecificationVo> findByAppmodelId(String appmodelId) {
        return specificationClassMapper.findByAppmodelId(appmodelId);
    }

    /**
     * 删除规格分类
     *
     * @param specificationClassId
     * @return
     */
    @Override
    public int deleteSpecificationClassById(Integer specificationClassId) {

        int rs = 0;
        //删除规格分类-----逻辑删除
        SpecificationClass specificationClass = new SpecificationClass();
        specificationClass.setDeleteState(true);
        specificationClass.setSpecificationClassId(specificationClassId);
        rs += specificationClassMapper.updateByPrimaryKeySelective(specificationClass);

        //删除规格-----逻辑删除
        rs += specificationMapper.logicDelete(specificationClassId);

        return rs;
    }

    /**
     * 更新规格
     *
     * @param specificationVo
     * @return
     */
    public int update(SpecificationVo specificationVo) {
        //跟新规格
        int rs = specificationClassMapper.updateByPrimaryKeySelective(specificationVo);
        List<Specification> list = specificationVo.getSpecificationList();
        if (list != null) {

            Integer specificationClassId = specificationVo.getSpecificationClassId();
            Specification newspec = new Specification();
            newspec.setSpecificationClassId(specificationClassId);
            List<Specification> findlist = specificationMapper.select(newspec);

            //请求中和数据库中有ID相同的规格，则跟新规格
            list.forEach(reqspec -> {
                if (findlist != null) {
                    for (Iterator<Specification> itfind = findlist.iterator(); itfind.hasNext(); ) {
                        Specification findspec = itfind.next();
                        if (findspec.getSpecificationId().equals(reqspec.getSpecificationId())) {
                            specificationMapper.updateByPrimaryKeySelective(reqspec);
                            //移除相同的，数组中剩下的都需要删除
                            itfind.remove();
                        }
                    }
                }
            });

            //删除数据库中的数据
            if (findlist != null) {
                findlist.forEach(specification -> {
                    specification.setDeleteState(true);
                    specificationMapper.updateByPrimaryKeySelective(specification);
                });
            }

            //请求中有新增的规格
            list.forEach(specification -> {
                if (specification.getSpecificationId() == -1) {
                    specification.setSpecificationId(null);
                    specification.setDeleteState(false);
                    specification.setAppmodelId(specificationVo.getAppmodelId());
                    specification.setSpecificationClassId(specificationVo.getSpecificationClassId());
                    specificationMapper.insertSelective(specification);
                }
            });
        }
        return rs;
    }
}
