package com.medusa.basemall.agent.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.agent.dao.AgentGradeMapper;
import com.medusa.basemall.agent.dao.AgentMapper;
import com.medusa.basemall.agent.dao.AgentRegisterMapper;
import com.medusa.basemall.agent.entity.Agent;
import com.medusa.basemall.agent.entity.AgentGrade;
import com.medusa.basemall.agent.entity.AgentRegister;
import com.medusa.basemall.agent.service.AgentRegisterService;
import com.medusa.basemall.agent.vo.BindAgentVo;
import com.medusa.basemall.agent.vo.RegisterAgentVo;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.shop.dao.SmsMapper;
import com.medusa.basemall.shop.entity.Sms;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.service.WxuserService;
import com.medusa.basemall.utils.RegexMatches;
import com.medusa.basemall.utils.SSMSend;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author medusa
 * @date 2018/06/02
 * 需要事物时添加  @Transactional
 */

@Service
@Transactional
public class AgentRegisterServiceImpl extends AbstractService<AgentRegister> implements AgentRegisterService {
	@Resource
	private AgentRegisterMapper tAgentRegisterMapper;

	@Resource
	private SmsMapper tSmsMapper;

	@Resource
	private AgentMapper tAgentMapper;

	@Resource
	private AgentGradeMapper tAgentGradeMapper;

	@Resource
	private ActiveMqClient activeMqClient;
	@Autowired
	private WxuserService wxuserService;


	@Override
	public Result agentRegister(RegisterAgentVo agentVo) {
		AgentRegister agentRegister = BeanMapper.map(agentVo, AgentRegister.class);
		agentRegister.setRegTime(TimeUtil.getNowTime());
		agentRegister.setRegState(0);
		AgentRegister temp = new AgentRegister();
		temp.setRegPhone(agentRegister.getRegPhone());

		if (null == tAgentRegisterMapper.selectOne(temp)) {
			//前端   校验验证码
			try {
				if (agentRegister.getType().equals(1)) {
					Sms sms = getSms(agentRegister.getRegPhone(), agentRegister.getAppmodelId(), 3);
					if (sms != null && sms.getSmsCode().equals(agentRegister.getRegCode())) {
						Agent t = new Agent();
						t.setWxuserId(agentRegister.getWxuserId());
						if (tAgentMapper.selectOne(t) != null) {
							return ResultGenerator.genFailResult("该用户已注册为代理人");
						}
						agentRegister.setRegCode("");
						agentRegister.setBinding(1);
						if (tAgentRegisterMapper.insert(agentRegister) > 0) {
							StringBuilder sb = new StringBuilder();
							sb.append("姓名:").append(agentRegister.getRegName()).append("; 联系方式: ")
									.append(agentRegister.getRegPhone()).append(";所在区域:")
									.append(agentRegister.getAgentDomain());
							List<String> keywords = new LinkedList<>();
							keywords.add(agentRegister.getRegName());
							keywords.add(sb.toString());
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("first", "代理商申请通知");
							jsonObject.put("keywords", keywords);
							jsonObject.put("remark", "请登录商家后台审核处理");
							jsonObject.put("appmodelId", agentRegister.getAppmodelId());
							jsonObject.put("type", 5);
							activeMqClient.send(jsonObject.toString(), ActiviMqQueueName.ORDER_FWH_MESSAGE);
							return ResultGenerator.genSuccessResult();
						}
						return ResultGenerator.genFailResult("数据添加失败");
					}
				}
				//后端   发送绑定码
				if (agentRegister.getType().equals(2)) {
					String random = RegexMatches.getInt(999999, 100000).toString();
					ArrayList<String> params = new ArrayList<>();
					params.add(random);
					agentRegister.setRegCode(random);
					agentRegister.setBinding(0);
					if (tAgentRegisterMapper.insert(agentRegister) > 0) {
						try {
							SSMSend.sendNoto(1400038028, "040e0e068f9653eb7600b788f4499ef7", params,
									agentRegister.getRegPhone(), 132074);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return ResultGenerator.genSuccessResult();
					}
				}
				Wxuser wxuser = wxuserService.findById(agentVo.getWxuserid());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("wxuserId", wxuser.getWxuserId());
				jsonObject.put("module", 2);
				jsonObject.put("type", 3);
				jsonObject.put("appmodelId",wxuser.getAppmodelId());
				activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.MESSAGE_NOTIFY);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResultGenerator.genFailResult("申请失败");
		} else {
			return ResultGenerator.genFailResult("该手机号已被注册");
		}
	}

	/**
	 * 获取短信记录
	 *
	 * @param phone
	 * @param appmodelId
	 * @param type
	 * @return
	 */
	private Sms getSms(String phone, String appmodelId, Integer type) {
		Map<String, Object> map = new HashMap<>();
		map.put("phone", phone);
		map.put("appmodelId", appmodelId);
		map.put("type", type);
		return tSmsMapper.selectByPhone(map);
	}

	/**
	 * 校验验证码是否超时
	 *
	 * @param sms
	 * @return
	 */
	private boolean codeCheckTimeOut(Sms sms) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date ctd = simpleDateFormat.parse(sms.getUpdateTime());
			if (System.currentTimeMillis() - ctd.getTime() > 5 * 60 * 1000) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Result bindingRegister(BindAgentVo agentVo) {
		Sms sms = getSms(agentVo.getRegPhone(), agentVo.getAppmodelId(), 2);
		if (null != sms && sms.getSmsCode().equals(agentVo.getRegCode())) {
			if (codeCheckTimeOut(sms)) {
				return ResultGenerator.genFailResult("验证码超时");
			}
			AgentRegister register = new AgentRegister();
			register.setRegPhone(agentVo.getRegPhone());
			register.setAppmodelId(agentVo.getAppmodelId());
			AgentRegister reg = tAgentRegisterMapper.selectOne(register);
			if (null != reg.getRegCode() && reg.getRegCode().equals(agentVo.getBingCode())) {
				if (reg.getBinding().equals(0)) {
					reg.setBinding(1);
					reg.setRegState(2);
					reg.setWxuserId(agentVo.getWxuserid());
					if (tAgentRegisterMapper.updateByPrimaryKeySelective(reg) > 0) {
						Agent agent = getAgent(reg);
						tAgentMapper.insertSelective(agent);
						List<String> keywords = new LinkedList<>();
						keywords.add(reg.getRegName());
						keywords.add(reg.getRegPhone());
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("first", "代理商申请通知");
						jsonObject.put("keywords", keywords);
						jsonObject.put("remark", "客户已成功绑定代理商，如需查看请登陆后台");
						jsonObject.put("appmodelId", reg.getAppmodelId());
						jsonObject.put("type", 6);
						activeMqClient.send(jsonObject.toString(), ActiviMqQueueName.ORDER_FWH_MESSAGE);

						Wxuser wxuser = wxuserService.findById(agentVo.getWxuserid());
						JSONObject jsonObject2 = new JSONObject();
						jsonObject2.put("wxuserId", wxuser.getWxuserId());
						jsonObject2.put("module", 2);
						jsonObject2.put("type", 4);
						jsonObject2.put("appmodelId",wxuser.getAppmodelId());
						activeMqClient.send(jsonObject2.toJSONString(), ActiviMqQueueName.MESSAGE_NOTIFY);

						return ResultGenerator.genSuccessResult();
					}
					return ResultGenerator.genFailResult("绑定失败");
				}
				return ResultGenerator.genFailResult("绑定码已被绑定");
			}
			return ResultGenerator.genFailResult("绑定码无效");
		}
		return ResultGenerator.genFailResult("验证码错误");
	}

	/**
	 * 设置代理人信息
	 *
	 * @param reg
	 * @return
	 */
	private Agent getAgent(AgentRegister reg) {
		Agent agent = new Agent();
		agent.setAgentDomain(reg.getAgentDomain());
		agent.setWxuserId(reg.getWxuserId());
		agent.setAgentName(reg.getRegName());
		agent.setAgentPhone(reg.getRegPhone());
		Condition condition = new Condition(AgentGrade.class);
		condition.createCriteria().andEqualTo("appmodelId", reg.getAppmodelId());
		List<AgentGrade> agentGrades = tAgentGradeMapper.selectByCondition(condition);
		agentGrades.sort(Comparator.comparing(AgentGrade::getGradeDiscount));
		agent.setAgentGradeId(agentGrades.get(agentGrades.size() - 1).getAgentGradeId());
		agent.setAgentBalance(0.0);
		agent.setAgentStatus(1);
		agent.setAuditDate(TimeUtil.getNowTime());
		agent.setAppmodelId(reg.getAppmodelId());
		return agent;
	}

	@Override
	public List<AgentRegister> findRegisteAgent(Integer type, String appmodelId) {
		AgentRegister register = new AgentRegister();
		//查询待验证
		Condition condition = new Condition(AgentRegister.class);
		if (null != type && type.equals(2)) {
			condition.createCriteria().andEqualTo("appmodelId", appmodelId).andIsNotNull("binding")
					.andEqualTo("type", 2).andEqualTo("regState", 0);
			//已拒绝
		} else if (null != type && type.equals(1)) {
			condition.createCriteria().andEqualTo("appmodelId", appmodelId).andEqualTo("type", 1)
					.andEqualTo("regState", 1);
			//查询待审核
		} else if (null != type && type.equals(0)) {
			condition.createCriteria().andEqualTo("appmodelId", appmodelId).andEqualTo("type", 1)
					.andEqualTo("regState", 0);
		}
		return tAgentRegisterMapper.selectByCondition(condition);
	}

	@Override
	public Result refuse(Integer regState, String refuseRemark, String regIds, String appmodelId) {
		//todo 模板消息暂时搁置
		if (regState < 1 || regState > 2) {
			return ResultGenerator.genFailResult("参数有误");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("appmodelId", appmodelId);
		//拒绝申请
		if (regState.equals(1)) {
			map.put("regState", 1);
			map.put("refuseRemark", refuseRemark);
			map.put("regIds", regIds.split(","));
			tAgentRegisterMapper.refuse(map);
			return ResultGenerator.genSuccessResult();
		}
		//同意添加代理信息
		if (regState.equals(2)) {
			List<Agent> agents = new ArrayList<>();
			List<AgentRegister> agentRegisters = tAgentRegisterMapper.selectByIds(regIds);
			agentRegisters.forEach(obj -> {
				Agent agent = getAgent(obj);
				agents.add(agent);
			});
			if (tAgentMapper.insertList(agents) > 0) {
				map.put("regIds", regIds.split(","));
				map.put("regState", 2);
				tAgentRegisterMapper.refuse(map);
				return ResultGenerator.genSuccessResult();
			}
		}

		return ResultGenerator.genFailResult("操作失败");
	}

	@Override
	public Result updateRegister(AgentRegister register) {
		return tAgentRegisterMapper.updateByPrimaryKeySelective(register) > 0 ?
				ResultGenerator.genSuccessResult() :
				ResultGenerator.genFailResult("更新失败");
	}

}
