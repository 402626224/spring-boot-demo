package com.imain.elasticsearchdemo.estemplatecase;

import com.imain.elasticsearchdemo.ElasticsearchDemoApplicationTests;
import com.imain.elasticsearchdemo.model.PostModel;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;
import java.util.Map;

public class QueryBuildersCaseTests extends ElasticsearchDemoApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryBuildersCaseTests.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void test() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery()).build();
        List<PostModel> list = elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
        LOGGER.info("size:{},content:{}", list.size(), list);
    }

    @Test
    public void testPage() {
        String[] include = {"title", "userId", "weight", "content"};
        FetchSourceFilter fetchSourceFilter = new FetchSourceFilter(include, null);   //两个参数分别是要显示的和不显示的

        QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("title", "浣溪沙");

        Sort sort = Sort.by(Sort.Order.desc("userId"), Sort.Order.desc("weight"));
        Pageable pageable = PageRequest.of(0, 20, sort);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSourceFilter(fetchSourceFilter)
                .withQuery(queryBuilder)
                .withHighlightFields(new HighlightBuilder.Field("title"))
                .withPageable(pageable)
                .withIndices("projectname").withTypes("post")
//                .withSort(SortBuilders.fieldSort("userId").order(SortOrder.ASC))
//                .withSort(SortBuilders.fieldSort("weight").order(SortOrder.ASC))
                .build();
        List<PostModel> list = elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
        list.forEach(System.out::println);
    }


}
