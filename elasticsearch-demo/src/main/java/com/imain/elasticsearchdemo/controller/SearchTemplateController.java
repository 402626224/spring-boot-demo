package com.imain.elasticsearchdemo.controller;

import com.imain.elasticsearchdemo.model.PostModel;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.elasticsearch.index.query.QueryBuilders.*;

@RestController
@RequestMapping("search")
public class SearchTemplateController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 单字符串模糊查询，默认排序。将从所有字段中查找包含传来的word分词后字符串的数据集
     */
    @RequestMapping("/singleWord")
    public Object singleTitle(String word, @PageableDefault Pageable pageable) {
        //使用queryStringQuery完成单字符串查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(word)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
    }

    /**
     * 单字段对某字符串模糊查询
     */
    @RequestMapping("/singleMatch")
    public Object singleMatch(String content, Integer userId, @PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("content", content)).withPageable(pageable).build();
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("userId", userId)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
    }

    /**
     * 单字段对某短语进行匹配查询，短语分词的顺序会影响结果
     * [slop  相隔多远：意思是你需要移动一个词条多少次来让查询和文档匹配]
     */
    @RequestMapping("/singlePhraseMatch")
    public Object singlePhraseMatch(String content, @PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchPhraseQuery("content", content)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
    }

    /**
     * term匹配，即不分词匹配，你传来什么值就会拿你传的值去做完全匹配
     */
    @RequestMapping("/singleTerm")
    public Object singleTerm(Integer userId, @PageableDefault Pageable pageable) {
        //不对传来的值分词，去找完全匹配的
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("userId", userId)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
    }

    /**
     * 多字段匹配
     */
    @RequestMapping("/multiMatch")
    public Object singleUserId(String title, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQuery(title, "title", "content")).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
    }

    /**
     * 单字段包含所有输入[完全包含查询]
     * [之前的查询中，当我们输入“我天”时，ES会把分词后所有包含“我”和“天”的都查询出来，
     * 如果我们希望必须是包含了两个字的才能被查询出来，那么我们就需要设置一下Operator。
     * <p>
     * 无论是matchQuery，multiMatchQuery，queryStringQuery等，都可以设置operator。
     * 默认为Or，设置为And后，就会把符合包含所有输入的才查出来。]
     */
    @RequestMapping("/contain")
    public Object contain(String title) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", title).operator(Operator.AND)).build();
        return elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
    }

    @RequestMapping("page")
    public Object test(String word, @PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(word)).withPageable(pageable).build();
        AggregatedPage<PostModel> page = elasticsearchTemplate.queryForPage(searchQuery, PostModel.class);
        return page;
    }


}
