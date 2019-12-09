package com.imain.cxfdemo.service;

import org.springframework.stereotype.Component;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * author Songrui.Liu
 * date 2019/11/3014:56
 */
@WebService(serviceName = "CommonService", // 与接口中指定的name一致
        targetNamespace = "http://service.cxfdemo.imain.com/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.imain.cxfdemo.service.CommonService"// 接口地址
)
@Component
public class CommonServiceImpl implements CommonService {

    @Override
    public String sayHello(@WebParam(name = "param") String param) {
        return "helloworld";
    }

    @Override
    public String getUser(String param) {

        return "helloworld";
    }
}
