package com.imain.itchatdemo.model;

/**
 * author Songrui.Liu
 * date 2019/12/1317:44
 */
public class ProductModel {

    /**
     * {"status":1,"result":{"count":{"all":"0","good":"0","normal":"0","bad":"0","pic":"0"},"percent":100,"list":[],"url":"http:\/\/shop225.wqmeng.cn\/.\/"}}
     */
    /**
     * 1 成功
     */
    private Integer status;

    private ResultModel result;

    public Integer getStatus() {
        return status;
    }

    public ProductModel setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ResultModel getResult() {
        return result;
    }

    public ProductModel setResult(ResultModel result) {
        this.result = result;
        return this;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
