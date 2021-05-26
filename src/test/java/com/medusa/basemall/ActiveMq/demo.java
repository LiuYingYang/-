package com.medusa.basemall.ActiveMq;

import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.order.dao.OrderMapper;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.service.OrderDetailService;
import com.medusa.basemall.order.service.OrderRefoundService;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.promotion.dao.ActivityProductMapper;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.user.dao.MemberMapper;
import com.medusa.basemall.user.dao.WxuserMapper;
import com.medusa.basemall.utiles.Constant;
import org.apache.activemq.util.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class demo {

    @Autowired
    public ActiveMqClient client;

    @Autowired
    public OrderRefoundService refoundService;
    @Autowired
    @Qualifier("orderServiceImpl")
    public OrderService orderService;
    @Resource
    public OrderMapper orderMapper;
    @Resource
    private MemberMapper memberMapper;

	@Resource
    private WxuserMapper tWxuserMapper;

	@Resource
    private OrderDetailService orderDetailService;
	
	@Test
    public void test(){
        List<OrderDetail> orderId = orderDetailService.findByList("orderId", "1532078005177111,1532080509617503,1532081532248538");
        System.out.println(orderId.size());
    }

    @Test
    public void init() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.restart();
        client.delaySend("消息1", "demo1", 5000L);
	    stopWatch.stop();
        System.out.println("发送消息耗时: " + stopWatch.taken());
    }

    @Resource
    private ActivityProductMapper tActivitytProductMapper;


	@Test
    public void demo() {
        String productIds = "1527751305558135,123456789361123";
        Condition condition = new Condition(ActivityProduct.class);
        condition.createCriteria()
                .andIn("productId", Arrays.asList(productIds.split(",")))
                .andEqualTo("appmodelId", Constant.appmodelIdy)
                .andEqualTo("activityType", "3001");
        List<ActivityProduct> activityProducts = tActivitytProductMapper.selectByCondition(condition);
        System.out.println(activityProducts.size());
    }


    public static void main(String[] args) {


//        BigDecimal b1 = new BigDecimal(2);
//        BigDecimal b2 = new BigDecimal(2);
//        BigDecimal divide = b1.add(b2);
//        System.out.println(divide);
//        KdniaoTrackQueryAPI kp = new KdniaoTrackQueryAPI();
//        try {
//            String sf = kp.getOrderTracesByJson("HTKY", "71341686977195");
//            System.out.println(sf);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        List<MemberRank> memberRanks = new ArrayList<>();
//        MemberRank memberRank1 = new MemberRank();
//        memberRank1.setRankId(1);
//        memberRank1.setGrowthValue(1000);
//        MemberRank memberRank2 = new MemberRank();
//        memberRank2.setRankId(2);
//        memberRank2.setGrowthValue(3000);
//        MemberRank memberRank3 = new MemberRank();
//        memberRank3.setRankId(3);
//        memberRank3.setGrowthValue(5000);
//        memberRanks.add(memberRank1);
//        memberRanks.add(memberRank2);
//        memberRanks.add(memberRank3);
//        Member member =new Member();
//        MemberRank myRank = new MemberRank();
//        myRank.setRankId(1);
//        myRank.setGrowthValue(1000);
//        member.setRankInfo(myRank);
//        member.setGrowthValue(1123);
//
//        int tempRankId = member.getRankInfo().getRankId();
//        int tempRankGrowthValue = -1;
//
//        for (MemberRank obj : memberRanks) {
//            if (!obj.getRankId().equals(myRank.getRankId())) {
//                if (member.getGrowthValue() > obj.getGrowthValue()) {
//                        //取 达标成长值取-会员当前成长值 相减后值 最小的等级
//                        int min = obj.getGrowthValue() - member.getGrowthValue();
//                        if(tempRankGrowthValue != -1 &&  min > tempRankGrowthValue ){
//                            tempRankId = obj.getRankId();
//                            tempRankGrowthValue = min;
//                        }else if(tempRankGrowthValue == -1 &&  min < tempRankGrowthValue){
//                            tempRankId = obj.getRankId();
//                            tempRankGrowthValue = min;
//                        }
//                }
//            }
//        }
//        if(tempRankId != member.getRankInfo().getRankId()){
//            MemberRank memberRank = new MemberRank();
//            memberRank.setRankId(tempRankId);
//            member.setRankInfo(memberRank);
//        }
//
//        System.out.println(member.getRankInfo().getRankId());
    }

/*
    @Test
    public void generateMarkdownDocs() throws Exception {
        //	输出Markdown格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8081/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("src/docs/markdown/generated"));
    }*/
}
