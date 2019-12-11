package com.imain.thymeleafdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Resource
    private HttpTraceInterceptor httpTraceInterceptor;

    /**
     * 静态资源配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/p/**")
                .addResourceLocations("classpath:/templates/page/");
        registry.addResourceHandler("/a/**")
                .addResourceLocations("classpath:/templates/assets/").setCacheControl(CacheControl.maxAge(4, TimeUnit.HOURS));
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/").setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
        registry.addResourceHandler("/idm/**")
                .addResourceLocations("classpath:/idm/").setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截规则：除了login，其他都拦截判断
        final String[] excludePathPatterns = new String[]{"/login", "/app/rest/account", "/app/authentication",
                "/app/logout", "/a/**", "/common/*", "**/processes/**", "/flow/form/**",
                "/flow/*/rest/**", "/form/*/rest/**", "/ui/lib/**"};
        registry.addInterceptor(httpTraceInterceptor).addPathPatterns("/**");//.excludePathPatterns(excludePathPatterns);
    }

}
