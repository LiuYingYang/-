package com.medusa.basemall.activemq;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.agent.entity.AgentGrade;
import com.medusa.basemall.agent.service.AgentGradeService;
import com.medusa.basemall.article.dao.ArticleCategoryRepository;
import com.medusa.basemall.article.entity.ArticleCategory;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.integral.entity.PrizeRule;
import com.medusa.basemall.integral.service.PrizeRuleService;
import com.medusa.basemall.shop.entity.ColumnFlag;
import com.medusa.basemall.shop.entity.FirstpageClassify;
import com.medusa.basemall.shop.entity.Footer;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.shop.service.ColumnFlagService;
import com.medusa.basemall.shop.service.FirstpageClassifyService;
import com.medusa.basemall.shop.service.FooterService;
import com.medusa.basemall.shop.service.ManagerService;
import com.medusa.basemall.user.entity.MemberRank;
import com.medusa.basemall.user.entity.MemberRankRule;
import com.medusa.basemall.user.service.MemberRankRuleService;
import com.medusa.basemall.user.service.MemberRankService;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author whh
 */
@Component
public class ManagerListerner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerListerner.class);

	@Resource
	private ManagerService managerService;

	@Resource
	private MemberRankService memberRankService;

	@Resource
	private MemberRankRuleService memberRankRuleService;

	@Resource
	private AgentGradeService agentGradeService;

	@Resource
	private ColumnFlagService columnFlagService;

	@Resource
	private ArticleCategoryRepository articleCategoryRepository;

	@Resource
	private PrizeRuleService prizeRuleService;

	@Resource
	private FooterService footerService;

	@Resource
	private ActiveMqClient activeMqClient;

	@Resource
	private FirstpageClassifyService firstpageClassifyService;

	@JmsListener(destination = ActiviMqQueueName.MANAGER_CREATE)
	public void basemallManagerCreate(String jsonData) {
		JSONObject jsonObject = JSONObject.parseObject(jsonData);
		String appmodelId = jsonObject.getString("appmodelId");
		if (appmodelId == null && appmodelId.equals("")) {
			return;
		}
		Manager manager = managerService.selectByAppmodelId(appmodelId);
		if (manager == null) {
			manager = new Manager();
			manager.setVersion(jsonObject.getInteger("version"));
			String createTime = jsonObject.getString("createTime");
			manager.setCreateTime(createTime);
			//设置到期日期
			Date currentDay = null;
			try {
				currentDay = DateFormatUtil.ISO_ON_DATE_FORMAT.parse(createTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Integer duration = jsonObject.getInteger("duration");
			manager.setExpiryDate(DateUtils.addMonths(currentDay, duration));
			manager.setAppId(appmodelId.substring(9, appmodelId.length()));
			manager.setAppmodelId(appmodelId);
			manager.setMiniName(jsonObject.getString("miniName"));
			manager.setMiniCode(jsonObject.getString("miniCode"));
			manager.setLogo(jsonObject.getString("logo"));
			manager.setVersionSubscript(jsonObject.getString("versionSubscript"));
			managerService.save(manager);
			Basemall_initialiseData(manager);
			JSONObject json = new JSONObject();
			json.put("module", 4);
			json.put("type", 7);
			json.put("managerId", manager.getId());
			json.put("appmodelId", manager.getAppmodelId());
			activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.MESSAGE_NOTIFY);
		}
	}


	@JmsListener(destination = "Basemall_ManagerUpdate")
	public void basemallManagerUpdate(String jsonData) {

	}

	/**
	 *更新小程序信息
	 * @param jsonData
	 */
	@JmsListener(destination = ActiviMqQueueName.MANAGER_INFO_UPDATE)
	public void ManagerInfoUpdate(String jsonData) {
		JSONObject jsonObject = JSONObject.parseObject(jsonData);
		Manager  manager = BeanMapper.map(jsonObject, Manager.class);
		managerService.updateInfo(manager);

	}

	private void Basemall_initialiseData(Manager manager) {
		String appmodelId = manager.getAppmodelId();
		initialiseMemberGrade(appmodelId);
		initialiseFirstpageClassify(appmodelId);
		initialiseColumnFlag(appmodelId, manager.getVersion());
		initialiseFooter(appmodelId);
		switch (manager.getVersion()) {
			case 2:
				break;
			case 3:
				initialiseAgenGrade(appmodelId);
				initialiseArticleCategory(appmodelId);
				initialisePrizeRule(appmodelId);
				break;
			default:
				break;
		}
	}

	/**
	 * 初始化会员等级规则
	 */
	private void initialiseMemberGrade(String appmodelId) {
		MemberRankRule rule = memberRankRuleService.findBy("appmodelId", appmodelId);
		if (rule != null) {
			return;
		}
		List<MemberRank> memberRanks = new ArrayList<>();
		MemberRank memberRank1 = new MemberRank();
		memberRank1.setCreateTime(TimeUtil.getNowTime());
		memberRank1.setAppmodelId(appmodelId);
		memberRank1.setDiscount(9.9);
		memberRank1.setGrowthValue(0);
		memberRank1.setDeleteState(2);
		memberRank1.setBackGroundPicUrl(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180929135112111-234.png");
		memberRank1.setRankName("普卡会员");
		memberRank1.setDeductGrowth(0);
		MemberRank memberRank2 = new MemberRank();
		BeanUtils.copyProperties(memberRank1, memberRank2);
		memberRank2.setDiscount(9.8);
		memberRank2.setGrowthValue(1000);
		memberRank2.setRankName("银卡会员");
		MemberRank memberRank3 = new MemberRank();
		BeanUtils.copyProperties(memberRank1, memberRank3);
		memberRank3.setDiscount(9.7);
		memberRank3.setGrowthValue(1500);
		memberRank3.setRankName("金卡会员");
		memberRanks.add(memberRank1);
		memberRanks.add(memberRank2);
		memberRanks.add(memberRank3);
		memberRankService.save(memberRanks);
		MemberRankRule rankRule = new MemberRankRule();
		rankRule.setExplainInfo("会员店内是基于会员一定时期内在超级棱镜商城累计获取的成长值所决定的，会员达到相应的等级门槛即可升级。会员通过微信小程序登录、分享小程序、小程序中购买商品获取成长值。");
		rankRule.setShareIntegral(5);
		rankRule.setLoginIntegral(5);
		rankRule.setConsumeIntegral(1);
		rankRule.setValidity(12);
		rankRule.setExplainState(0);
		rankRule.setAppmodelId(appmodelId);
		memberRankRuleService.save(rankRule);
	}

	/**
	 * 初始化代理等级
	 */
	private void initialiseAgenGrade(String appmodelId) {
		AgentGrade agentGrade = agentGradeService.findBy("appmodelId", appmodelId);
		if (agentGrade != null) {
			return;
		}
		List<AgentGrade> grades = new ArrayList<>();
		AgentGrade grade1 = new AgentGrade();
		grade1.setAppmodelId(appmodelId);
		grade1.setGradeDiscount(new BigDecimal(9.8));
		grade1.setGradeInfo("");
		grade1.setCreateTime(TimeUtil.getNowTime());
		grade1.setEditState(1);
		grade1.setUpgradePrice(new BigDecimal(0.0));
		grade1.setGradeName("普通会员");
		grades.add(grade1);
		AgentGrade grade2 = new AgentGrade();
		grade2.setAppmodelId(appmodelId);
		grade2.setGradeDiscount(new BigDecimal(9.8));
		grade2.setGradeInfo("");
		grade2.setCreateTime(TimeUtil.getNowTime());
		grade2.setEditState(1);
		grade2.setUpgradePrice(new BigDecimal(0.0));
		grade2.setGradeName("金牌会员");
		grades.add(grade2);
		agentGradeService.save(grades);
	}

	/**
	 * 初始化店铺版本栏目开关
	 */
	private void initialiseColumnFlag(String appmodelId, Integer version) {
		ColumnFlag columnFlag = columnFlagService.findByAppmodelId(appmodelId);
		if (columnFlag == null) {
			ColumnFlag columnFlagNew = new ColumnFlag();
			columnFlagNew.setAppmodelId(appmodelId);
			columnFlagNew.setArticleFlag(false);
			columnFlagNew.setMemberFlag(false);
			columnFlagNew.setMemberRechargeFlag(false);
			columnFlagNew.setFootFlag(false);
			columnFlagNew.setShopFlag(false);
			columnFlagNew.setShopinfoFlag(false);
			columnFlagNew.setProxyFlag(false);
			columnFlagNew.setSerarchFlag(false);
			columnFlagNew.setNoticeFlag(false);
			columnFlagNew.setClassifyFlag(false);
			/*if (version.equals(2)) {
				columnFlagNew.setShopinfoFlag(true);
				columnFlagNew.setSerarchFlag(true);
				columnFlagNew.setMemberFlag(true);
				columnFlagNew.setMemberRechargeFlag(true);
				columnFlagNew.setShopFlag(true);
			}
			if (version.equals(3)) {
				columnFlagNew.setProxyFlag(true);
				columnFlagNew.setArticleFlag(true);
				columnFlagNew.setFootFlag(true);
			}*/
			columnFlagService.save(columnFlagNew);
		}

	}

	/**
	 * 初始化文章分类
	 */
	private void initialiseArticleCategory(String appmodelId) {
		ArticleCategory articleCategoryNew = new ArticleCategory();
		articleCategoryNew.setCategoryName("所有");
		articleCategoryNew.setCategoryType(0);
		articleCategoryNew.setDeleteState(0);
		articleCategoryNew.setAppmodelId(appmodelId);
		articleCategoryRepository.save(articleCategoryNew);
	}

	/**
	 * 初始化首页分类导航(默认1,默认2...)
	 */
	private void initialiseFirstpageClassify(String appmodelId) {
		List<FirstpageClassify> firstpageClassifies = firstpageClassifyService.findByAppmodelId(appmodelId);
		if (firstpageClassifies.size() != 0) {
			return;
		}
		for (int i = 1; i < 5; i++) {
			FirstpageClassify firstpageClassify = new FirstpageClassify();
			firstpageClassify.setClassifyName("默认分类导航" + i);
			firstpageClassify.setSort(i);
			firstpageClassifies.add(firstpageClassify);
		}
		firstpageClassifies.get(1).setClassifyImg(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20181023162246071-46.jpg");
		firstpageClassifies.get(2).setClassifyImg(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20181009163136881-1.jpg");
		firstpageClassifies.get(3).setClassifyImg(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20181009163152071-2.jpg");
		firstpageClassifies.get(4).setClassifyImg(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20181009163249897-4.jpg");
		firstpageClassifyService.save(firstpageClassifies.get(1));
		firstpageClassifyService.save(firstpageClassifies.get(2));
		firstpageClassifyService.save(firstpageClassifies.get(3));
		firstpageClassifyService.save(firstpageClassifies.get(4));
	}

	/**
	 * 初始化积分规则
	 */
	private void initialisePrizeRule(String appmodelId) {
		PrizeRule prizeRuleNew = new PrizeRule();
		prizeRuleNew.setAppmodelId(appmodelId);
		prizeRuleService.save(prizeRuleNew);
	}

	/**
	 * 初始化底部导航
	 */
	private void initialiseFooter(String appmodelId) {
		List<Footer> footerList = new ArrayList<Footer>();
		Footer footerOen = new Footer();
		footerOen.setAppmodelId(appmodelId);
		footerOen.setFooterName("首页");
		footerOen.setFooterLink("小程序主页");
		footerOen.setFooterImgNo(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180925131939454-154.png");
		footerOen.setFooterImgYes(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180925131943537-155.png");
		footerOen.setFooterFlag(true);
		footerList.add(footerOen);
		Footer footerTwo = new Footer();
		footerTwo.setAppmodelId(appmodelId);
		footerTwo.setFooterName("商品分类");
		footerTwo.setFooterLink("商品分类");
		footerTwo.setFooterFlag(true);
		footerOen.setFooterImgNo(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180925131947795-156.png");
		footerOen.setFooterImgYes(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180925131950986-157.png");
		footerList.add(footerTwo);
		Footer footerThree = new Footer();
		footerThree.setAppmodelId(appmodelId);
		footerThree.setFooterName("发现");
		footerThree.setFooterLink("发现");
		footerThree.setFooterFlag(true);
		footerOen.setFooterImgNo(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180925132120873-158.png");
		footerOen.setFooterImgYes(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180925132127110-159.png");
		footerList.add(footerThree);
		Footer footerFour = new Footer();
		footerFour.setAppmodelId(appmodelId);
		footerFour.setFooterName("购物车");
		footerFour.setFooterLink("购物车");
		footerFour.setFooterFlag(true);
		footerOen.setFooterImgNo(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180925132203217-160.png");
		footerOen.setFooterImgYes(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180925132206000-161.png");
		footerList.add(footerFour);
		Footer footerFive = new Footer();
		footerFive.setAppmodelId(appmodelId);
		footerFive.setFooterName("我的");
		footerFive.setFooterLink("我的");
		footerFive.setFooterFlag(true);
		footerOen.setFooterImgNo(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180925132209031-162.png");
		footerOen.setFooterImgYes(
				"https://www.superprism.cn/mdsfile/medusa/basemall/S00040001wx4f8730e96f773fa3/image20180925132211698-163.png");
		footerList.add(footerFive);
		footerService.save(footerList);
	}
}
