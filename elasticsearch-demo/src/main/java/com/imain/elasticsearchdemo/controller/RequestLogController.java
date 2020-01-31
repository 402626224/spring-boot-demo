package com.imain.elasticsearchdemo.controller;

import com.imain.elasticsearchdemo.model.RequestLog;
import com.imain.elasticsearchdemo.repository.RequestLogRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("request")
public class RequestLogController {

    @Autowired
    private RequestLogRepository requestLogRepository;

    @RequestMapping("add")
    public String add() {
        RequestLog requestLog = new RequestLog()
                .setId(1L)
                .setOrderNo("123")
                .setUserId("1")
                .setUserName("1")
                .setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        requestLogRepository.save(requestLog);
        return "success";
    }

    @RequestMapping("list")
    public List<RequestLog> list() {
        SearchQuery searchQuery = null;
        QueryBuilder queryBuilder = null;

        requestLogRepository.search(searchQuery);
        return null;
    }

    @RequestMapping("all")
    public List<RequestLog> all() {
        List<RequestLog> result = new ArrayList<>();
        Iterable<RequestLog> list = requestLogRepository.findAll();
        list.forEach(m -> {
            result.add(m);
        });
        return result;
    }


}
