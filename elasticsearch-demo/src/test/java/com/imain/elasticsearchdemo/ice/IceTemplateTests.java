package com.imain.elasticsearchdemo.ice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imain.elasticsearchdemo.ElasticsearchDemoApplicationTests;
import com.imain.elasticsearchdemo.model.PostModel;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IceTemplateTests extends ElasticsearchDemoApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(IceTemplateTests.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void test() {
        //匹配所有文件，相当于就没有设置查询条件
        //QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("如梦", "title", "content");
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(
                QueryBuilders.matchPhraseQuery("title", "如梦")
        ).should(
                QueryBuilders.matchPhraseQuery("content", "李清照")
        );

        HighlightBuilder highlightBuilder = new HighlightBuilder(); //生成高亮查询器
        highlightBuilder.field("title");      //高亮查询字段
        highlightBuilder.field("content");    //高亮查询字段
        highlightBuilder.requireFieldMatch(false);     //如果要多个字段高亮,这项要为false
        highlightBuilder.numOfFragments(0);
        highlightBuilder.preTags("<em di>").postTags("</em>");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withHighlightBuilder(highlightBuilder)
                .build();

        SearchResponse searchResponse = elasticsearchTemplate.query(searchQuery, (response -> {
            return response;
        }));

        List<PostModel> result = elasticsearchTemplate.query(searchQuery, (response -> {
            List<PostModel> subResult = new ArrayList<>();
            SearchHits searchHits = response.getHits();
            SearchHit[] searchHit = searchHits.getHits();
            for (SearchHit item : searchHit) {
                try {
                    PostModel postModel = new ObjectMapper().readValue(item.getSourceAsString(), PostModel.class);
                    postModel.setTitle(Optional.ofNullable(item.getHighlightFields().get("title")).map(f -> f.getFragments()).map(f-> f[0].toString()).orElse(postModel.getTitle()));
                    postModel.setContent(Optional.ofNullable(item.getHighlightFields().get("content")).map(f -> f.getFragments()).map(f-> f[0].toString()).orElse(postModel.getContent()));
                    subResult.add(postModel);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
            return subResult;
        }));

        LOGGER.info("{}", searchResponse);


        // List<PostModel> result = elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
        result.forEach(System.out::println);
    }

}
