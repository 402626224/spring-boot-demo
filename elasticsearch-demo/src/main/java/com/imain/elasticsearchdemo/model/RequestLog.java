package com.imain.elasticsearchdemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "requestlogindex", type = "requestlog")
public class RequestLog {
    //Id注解Elasticsearch里相应于该列就是主键，查询时可以使用主键查询
    @Id
    private Long id;
    private String orderNo;
    private String userId;
    private String userName;
    private String createTime;

    public RequestLog() {
    }

    public RequestLog(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public RequestLog setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public RequestLog setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RequestLog setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public RequestLog setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public RequestLog setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return "RequestLog{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
