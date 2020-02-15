package com.imain.elasticsearchdemo.builders;

import com.imain.elasticsearchdemo.ElasticsearchDemoApplicationTests;
import com.imain.elasticsearchdemo.repository.PostModelRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.Map;

public class AggregationBuildersCaseTests extends ElasticsearchDemoApplicationTests {

    private final static Logger LOGGER = LoggerFactory.getLogger(AggregationBuildersCaseTests.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private PostModelRepository postModelRepository;

    public void test() {
        //（1）统计某个字段的数量
        ValueCountAggregationBuilder vcb = AggregationBuilders.count("count_uid").field("uid");
        //（2）去重统计某个字段的数量（有少量误差）
        CardinalityAggregationBuilder cb = AggregationBuilders.cardinality("distinct_count_uid").field("uid");
        //（3）聚合过滤
        FilterAggregationBuilder fab = AggregationBuilders.filter("uid_filter", QueryBuilders.queryStringQuery("uid:001"));
        //（4）按某个字段分组
        TermsAggregationBuilder tb = AggregationBuilders.terms("group_name").field("name");
        //（5）求和
        SumAggregationBuilder sumBuilder = AggregationBuilders.sum("sum_price").field("price");
        //（6）求平均
        AvgAggregationBuilder ab = AggregationBuilders.avg("avg_price").field("price");
        //（7）求最大值
        MaxAggregationBuilder mb = AggregationBuilders.max("max_price").field("price");
        //（8）求最小值
        MinAggregationBuilder min = AggregationBuilders.min("min_price").field("price");
        //（9）按日期间隔分组
        DateHistogramAggregationBuilder dhb = AggregationBuilders.dateHistogram("dh").field("date");
        //（10）获取聚合里面的结果
        TopHitsAggregationBuilder thb = AggregationBuilders.topHits("top_result");
        //（11）嵌套的聚合
        NestedAggregationBuilder nb = AggregationBuilders.nested("negsted_path", "quests");
        //（12）反转嵌套
        AggregationBuilders.reverseNested("res_negsted").path("kps ");

    }

    /**
     * 统计某个字段的数量
     */
    @Test
    public void aggregationCount() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        nativeSearchQueryBuilder.withQuery(queryBuilder);

        ValueCountAggregationBuilder aggregationBuilder = AggregationBuilders.count("tCount").field("userId");
        nativeSearchQueryBuilder.addAggregation(aggregationBuilder);
        nativeSearchQueryBuilder.withIndices("projectname").withTypes("post");

        Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQueryBuilder.build(), new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        Map<String, Aggregation> map = aggregations.asMap();
        System.out.println(map);
        InternalValueCount terms = (InternalValueCount) map.get("tCount");
        System.out.println(terms.getValue());
    }


    @Test
    public void testAggregation() throws Exception {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        nativeSearchQueryBuilder.withQuery(queryBuilder);
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("tCount").field("userId");
        nativeSearchQueryBuilder.addAggregation(aggregationBuilder);
        nativeSearchQueryBuilder.withIndices("projectname").withTypes("post");

        Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQueryBuilder.build(), (response -> response.getAggregations()));

        Map<String, Aggregation> map = aggregations.asMap();
        System.out.println(map);
        Terms terms = (Terms) map.get("tCount");
        terms.getBuckets().forEach(item -> {
            System.out.println("userId:" + item.getKeyAsString() + " -- count:" + item.getDocCount());
        });


    }

}
