package com.imain.elasticsearchdemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "projectname", type = "post", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class PostModel {
    @Id
    private String id;

    private String title;

    private String content;

    private Integer userId;

    private Integer weight;

    @Override
    public String toString() {
        return "PostModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                ", weight=" + weight +
                ", content='" + content + '\'' +
                '}';
    }


    public String getId() {
        return id;
    }

    public PostModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PostModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostModel setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public PostModel setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public PostModel setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }
}
