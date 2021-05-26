package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.LogisticChargeMapper;
import com.medusa.basemall.product.dao.LogisticFreeMapper;
import com.medusa.basemall.product.dao.LogisticModelMapper;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.entity.LogisticCharge;
import com.medusa.basemall.product.entity.LogisticFree;
import com.medusa.basemall.product.entity.LogisticModel;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.service.LogisticModelService;
import com.medusa.basemall.product.vo.LogisticModelVo;
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
public class LogisticModelServiceImpl extends AbstractService<LogisticModel> implements LogisticModelService {

    @Resource
    private LogisticModelMapper modelMapper;

    @Resource
    private LogisticChargeMapper ChargeMapper;

    @Resource
    private LogisticFreeMapper freeMapper;

    @Resource
    private ProductMapper productMapper;

    @Override
    public int saveOrUpdateModel(LogisticModelVo logisticModelVo) {

        int rs = 0;
        if (logisticModelVo.getLogisticModelId() == -1) {
            //保存物流模板
            logisticModelVo.setLogisticModelId(null);
            logisticModelVo.setCreateTime(TimeUtil.getNowTime());
            rs = modelMapper.insertSelective(logisticModelVo);

            rs += editChargeAndFree(logisticModelVo);
        } else {
            modelMapper.updateByPrimaryKeySelective(logisticModelVo);
            //删除计价和包邮模板
            ChargeMapper.deleteByLogisticModelId(logisticModelVo.getLogisticModelId());
            freeMapper.deleteByLogisticModelId(logisticModelVo.getLogisticModelId());

            rs += editChargeAndFree(logisticModelVo);
        }

        return rs;
    }

    /**
     * 开启或关闭物流模板
     *
     * @param appmodelId 模板ID
     * @param turnState  开启关闭状态
     * @return
     */
    @Override
    public int openOrCloseModel(String appmodelId, Boolean turnState) {

        LogisticModel logisticModel = new LogisticModel();
        logisticModel.setTurnState(turnState);
        logisticModel.setAppmodelId(appmodelId);

        return modelMapper.openOrCloseModel(logisticModel);
    }

    /**
     * 删除物流模板
     *
     * @param logisticModelId
     * @return
     */
    @Override
    public int deleteModelById(Integer logisticModelId) {


        int rs = 0;
        rs = modelMapper.deleteByPrimaryKey(logisticModelId);

        //删除计价模板和包邮模板
        //删除计价和包邮模板
        rs += ChargeMapper.deleteByLogisticModelId(logisticModelId);
        rs += freeMapper.deleteByLogisticModelId(logisticModelId);

        //todo 删除物流模板之后,需要对对应的商品进行处理
        //更新商品的物流模板信息
        Product product = new Product();
        product.setDeliveryType(true);
        product.setLogisticModelId(-1);
        product.setDeliveryFees(0.00);
        rs += productMapper.updateByLogisticModel(product);

        return rs;
    }

    /**
     * 查询物流模板（包含计价和包邮）
     *
     * @param appmodelId
     * @return
     */
    @Override
    public List<LogisticModelVo> findModelByAppmodelIdDetail(String appmodelId) {
        return modelMapper.findModelByAppmodelIdDetail(appmodelId);
    }

    /**
     * 查询物流模板
     *
     * @param appmodelId
     * @return
     */
    @Override
    public List<LogisticModel> findModelByAppmodelId(String appmodelId) {

        LogisticModel logisticModel = new LogisticModel();
        logisticModel.setAppmodelId(appmodelId);

        return modelMapper.select(logisticModel);
    }

	@Override
	public List<LogisticModelVo> findByWlCalculateFare(String[] logisticModelIds) {
		return modelMapper.findByWlCalculateFare(logisticModelIds);
	}

	/**
     * //保存计价和包邮模板
     *
     * @param logisticModelVo
     */
    public int editChargeAndFree(LogisticModelVo logisticModelVo) {
        int rs = 0;
        //保存计价模板
        List<LogisticCharge> chargeList = logisticModelVo.getChargeList();
        if (chargeList != null) {
            chargeList.forEach(charge -> {
                charge.setLogisticChargeId(null);
                charge.setLogisticModelId(logisticModelVo.getLogisticModelId());
                charge.setAppmodelId(logisticModelVo.getAppmodelId());
            });
            rs += ChargeMapper.insertList(chargeList);
        }
        //判断是否有包邮模板
        if (logisticModelVo.getFreeState()) {
            //保存包邮模板
            List<LogisticFree> freeList = logisticModelVo.getFreeList();
            freeList.forEach(free -> {
                free.setFreeId(null);
                free.setLogisticModelId(logisticModelVo.getLogisticModelId());
                free.setAppmodelId(logisticModelVo.getAppmodelId());
            });
            rs += freeMapper.insertList(freeList);
        }

        return rs;
    }
}


