package org.nh.rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    private Logger log = Logger.getLogger(AppConfiguration.class);

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        javax.servlet.Filter securityFilter = new javax.servlet.Filter() {

            @Override
            public void destroy() {
                log.info("destroy");
            }

            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
                    throws IOException, ServletException {
                log.info(">> -- Filtering " + ((HttpServletRequest) req).getRequestURI());
                fc.doFilter(req, res);
                log.info("<< -- Filtering " + ((HttpServletRequest) req).getRequestURI());
            }

            @Override
            public void init(FilterConfig arg0) throws ServletException {
                log.info("init");
            }
        };
        registrationBean.setFilter(securityFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }

}
