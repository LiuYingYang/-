package com.medusa.basemall;

import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * 单元测试继承该类即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BasemallApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasemallApplicationTests {

    @Autowired
    public WebApplicationContext wac;

    @Before
    public void setUp(){
        MockMvcUtil.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

}
