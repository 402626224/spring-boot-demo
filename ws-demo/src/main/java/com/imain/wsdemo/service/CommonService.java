package com.imain.wsdemo.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * author Songrui.Liu
 * date 2019/11/3014:56
 */
@WebService(name = "CommonService", // 暴露服务名称
        targetNamespace = "http://webservice.lli.com/"// 命名空间,一般是接口的包名倒序
)
public interface CommonService {
    @WebMethod
    String sayHello(@WebParam(name = "param") String param);

    @WebMethod
    String getUser(@WebParam(name = "param") String param);
}