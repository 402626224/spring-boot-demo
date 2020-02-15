package com.imain.elasticsearchdemo.estemplatecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imain.elasticsearchdemo.ElasticsearchDemoApplicationTests;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

public class AggregationBuilderCaseTests extends ElasticsearchDemoApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationBuilderCaseTests.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 聚合分析
     * 5.x后对排序，聚合这些操作用单独的数据结构(fielddata)缓存到内存里了，需要单独开启
     */
    @Test
    public void test() {

    }

    /**
     * select userId , count(1) from T group by userId
     */
    @Test
    public void testGetCountByGroup() {
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("tCount").field("userId")
                .order(BucketOrder.key(true))
//                .order(BucketOrder.count(Boolean.FALSE))
                .size(5);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("title", "浣溪沙"))
                .addAggregation(aggregationBuilder)
                .build();

        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());

        Terms terms = (Terms) aggregations.asMap().get("tCount");

        for (Terms.Bucket bucket : terms.getBuckets()) {
            LOGGER.info("bucket:{} , count:{}", bucket.getKeyAsString(), bucket.getDocCount());
        }

    }

    /**
     * select userId , count(1) ,avg(weight) from T group by userId
     */
    @Test
    public void testGetAvgByGroup() throws Exception {
        RangeAggregationBuilder aggregationBuilder =
                AggregationBuilders.range("range_by_weight").field("weight").addRange(0, 28).addRange(28, 50)
                        .subAggregation(
                                AggregationBuilders.terms("tCount").field("userId")
                                        .order(BucketOrder.aggregation("avgWeight", Boolean.FALSE))
                                        .subAggregation(
                                                AggregationBuilders.avg("avgWeight").field("weight")
                                        )
                        );

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("title", "浣溪沙"))
                .addAggregation(aggregationBuilder)
                .build();

        SearchResponse searchResponse = elasticsearchTemplate.query(searchQuery, response -> response);
        LOGGER.info("searchResponse:{}", new ObjectMapper().writeValueAsString(searchResponse.toString()));

        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());

        InternalRange terms = (InternalRange) aggregations.asMap().get("range_by_weight");
        for (Object item : terms.getBuckets()) {
            InternalRange.Bucket bucket = (InternalRange.Bucket) item;
            String keyName = bucket.getKeyAsString();
            List<LongTerms.Bucket> tCountList = ((LongTerms) bucket.getAggregations().asMap().get("tCount")).getBuckets();
            LOGGER.info("bucket:{} , Count:{}" , keyName , bucket.getDocCount() );
            for (LongTerms.Bucket sub : tCountList) {
                double avg = ((InternalAvg) sub.getAggregations().asMap().get("avgWeight")).getValue();
                LOGGER.info("|- bucket:{} , subBucket:{} , count:{} , avg:{} ", keyName, sub.getKeyAsString(), sub.getDocCount(), avg);
            }
        }

    }

}
