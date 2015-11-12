package com.voy;

import com.voy.auth.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;


@EntityScan(value = {"com.alo.base.model","com.alo.producer.model","com.alo.table.model","com.alo.theater.model"})
@SpringBootApplication
public class EventManagerApplication {

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }
}
