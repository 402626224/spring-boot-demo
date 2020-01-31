package com.imain.elasticsearchdemo.repository;

import com.imain.elasticsearchdemo.model.RequestLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RequestLogRepository extends ElasticsearchRepository<RequestLog,Long> {
}
