package com.medusa.basemall.agent.serviceImpl;

import com.medusa.basemall.agent.dao.AgentGradeMapper;
import com.medusa.basemall.agent.entity.AgentGrade;
import com.medusa.basemall.agent.service.AgentGradeService;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author medusa
 * @date 2018/07/16
 * 需要事物时添加  @Transactional
 */

@Service

public class AgentGradeServiceImpl extends AbstractService<AgentGrade> implements AgentGradeService {

	@Resource
    private AgentGradeMapper tAgentGradeMapper;

    @Override
    public List<AgentGrade> findByAppmodelId(String appmodelId) {
        AgentGrade agentGrade = new AgentGrade();
        agentGrade.setAppmodelId(appmodelId);
        List<AgentGrade> select = tAgentGradeMapper.select(agentGrade);
        return select;
    }

    @Override
    public Result updateGrade(AgentGrade agentGrade) {
        AgentGrade grade = new AgentGrade();
        grade.setAppmodelId(agentGrade.getAppmodelId());
        grade.setAgentGradeId(agentGrade.getAgentGradeId());
        grade.setGradeInfo(agentGrade.getGradeInfo());
        if (agentGrade.getEditState().equals(2)) {
            grade.setGradeDiscount(agentGrade.getGradeDiscount());
        }
        if (tAgentGradeMapper.updateByPrimaryKeySelective(grade)>0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("修改失败");
    }

    @Override
    public void initAgentGrade(String appmodelId){
        AgentGrade grade1 = new AgentGrade();
        grade1.setAppmodelId(appmodelId);
        grade1.setGradeDiscount(new BigDecimal(9.8));
        grade1.setGradeInfo("");
        grade1.setCreateTime(TimeUtil.getNowTime());
        grade1.setEditState(1);
        grade1.setUpgradePrice(new BigDecimal(0.0));
        grade1.setGradeName("普通会员");
        tAgentGradeMapper.updateByPrimaryKeySelective(grade1);
        AgentGrade grade2 = new AgentGrade();
        grade2.setAppmodelId(appmodelId);
        grade2.setGradeDiscount(new BigDecimal(9.8));
        grade2.setGradeInfo("");
        grade2.setCreateTime(TimeUtil.getNowTime());
        grade2.setEditState(1);
        grade2.setUpgradePrice(new BigDecimal(0.0));
        grade2.setGradeName("金牌会员");
        tAgentGradeMapper.updateByPrimaryKeySelective(grade1);
    }
}
