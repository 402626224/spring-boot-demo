package com.imain.thymeleafdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imain.thymeleafdemo.service.RequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * HttpTraceInterceptor
 */
@Configuration
public class HttpTraceInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpTraceInterceptor.class);

    @Autowired
    private ObjectMapper objectMapper;

    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long now = System.currentTimeMillis();
        START_TIME.set(now);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        long useTime = System.currentTimeMillis() - START_TIME.get();
        String path = request.getRequestURI();
        Map params = request.getParameterMap();
        if (isStatic(path)) {
            return;
        }
        if (useTime > 3000) {
            LOGGER.warn("Mapped path:{},params:{},useTime:{}ms", path, objectMapper.writeValueAsString(params), useTime);
        } else {
            LOGGER.info("Mapped path:{},params:{},useTime:{}ms", path, objectMapper.writeValueAsString(params), useTime);
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    private static boolean isStatic(String path) {
        return path.contains(".js") || path.contains(".css") || path.contains(".gif") || path.contains(".jpg") || path.contains("png");
    }
}
