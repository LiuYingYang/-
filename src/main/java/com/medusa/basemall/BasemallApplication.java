package com.medusa.basemall;

import com.medusa.basemall.core.Mapper;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author whh
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableWebSocket
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@MapperScan(value = "com.medusa.basemall.*", markerInterface = Mapper.class)
public class BasemallApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BasemallApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BasemallApplication.class, args);
    }


}
