package com.voy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;


//@EntityScan(value = {"com.alo.base.model","com.alo.producer.model","com.alo.table.model","com.alo.theater.model"})
@ImportResource("classpath:/base/scy-jasypt-context.xml")
@SpringBootApplication
@EnableEurekaClient
public class VoyAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoyAuthApplication.class, args);
    }


}
