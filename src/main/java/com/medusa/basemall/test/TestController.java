package com.medusa.basemall.test;

import com.medusa.basemall.order.entity.Buycar;
import com.medusa.basemall.order.service.OrderService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private OrderService orderService;

	@Resource
	private MongoTemplate mongoTemplate;

	@GetMapping("test")
    public void test(){
	    Query query = new Query(Criteria.where("productId").is(1542008738881447L));
	    mongoTemplate.updateMulti(query, Update.update("joinActiveInfo.activeInfo", ""), Buycar.class);
    }


//    @GetMapping(value = "/export")
//    public void exportTest(HttpServletResponse response) throws IOException {
//        List<Order> orders = orderService.findAll();
//
//        HSSFWorkbook hwb = new HSSFWorkbook();// 03以下
//
//        XSSFWorkbook xwb = new XSSFWorkbook();// 07以上
//
//        HSSFSheet sheet = hwb.createSheet("获取excel订单表格");
//
//        HSSFRow row = null;
//        row = sheet.createRow(0);
//        row.setHeight((short) (22.50 * 20));
//        row.createCell(0).setCellValue("订单号");
//        row.createCell(1).setCellValue("创建时间");
//        row.createCell(2).setCellValue("商品主图");
//        row.createCell(3).setCellValue("商品名称");
//        row.createCell(4).setCellValue("商品单价");
//        row.createCell(5).setCellValue("数量");
//        row.createCell(6).setCellValue("配送方式");
//        row.createCell(7).setCellValue("地址");
//        row.createCell(8).setCellValue("买家");
//        row.createCell(9).setCellValue("订单状态");
//        row.createCell(10).setCellValue("实收款");
//        row.createCell(11).setCellValue("备注");
//
//
//        for (int i = 0; i < orders.size(); i++) {
//            row = sheet.createRow(i + 1);
//
//        }
//        sheet.setDefaultRowHeight((short) (16.5 * 20));
//        //列宽自适应
//        for (int i = 0; i <= 13; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        String time = TimeUtil.getNowDate();
//        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        OutputStream os = response.getOutputStream();
//        response.setHeader("Content-disposition", "attachment;filename=订单" + time + ".xls");//默认Excel名称
//        hwb.write(os);
//        os.flush();
//        os.close();
//    }


}
