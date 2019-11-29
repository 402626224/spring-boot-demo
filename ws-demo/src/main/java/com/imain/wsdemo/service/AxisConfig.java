package com.imain.wsdemo.service;

import org.apache.axis2.transport.http.AxisServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * author Songrui.Liu
 * date 2019/11/2910:54
 */
@Component
public class AxisConfig {


    @Bean("axisServlet")
    public ServletRegistrationBean servlet() {
        ServletRegistrationBean axisServlet = new ServletRegistrationBean();
        axisServlet.setServlet(new AxisServlet());//这里的AxisServlet就是web.xml中的org.apache.axis2.transport.http.AxisServlet
        axisServlet.addUrlMappings("/cmc/services/*");
        //通过默认路径无法找到services.xml，这里需要指定一下路径，且必须是绝对路径
        //helloWorldServlet.addInitParameter("axis2.repository.path", this.getClass().getResource("/WEB-INF").getPath().toString());
        axisServlet.setLoadOnStartup(1);
        return axisServlet;
    }


}
