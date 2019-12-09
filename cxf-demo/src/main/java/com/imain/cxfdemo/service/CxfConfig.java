package com.imain.cxfdemo.service;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * author Songrui.Liu
 * date 2019/11/3014:58
 */
@Configuration
public class CxfConfig {
    @Autowired
    private Bus bus;

    @Autowired
    CommonService commonService;

    @SuppressWarnings("all")
    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
    }

    /**
     * JAX-WS
     **/
    @Bean
    public Endpoint endpoint2() {
        EndpointImpl endpoint = new EndpointImpl(bus, commonService);
        endpoint.publish("/CommonService");
        return endpoint;
    }
}