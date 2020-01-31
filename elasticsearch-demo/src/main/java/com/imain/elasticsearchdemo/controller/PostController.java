package com.imain.elasticsearchdemo.controller;

import com.imain.elasticsearchdemo.model.PostModel;
import com.imain.elasticsearchdemo.repository.PostModelRepository;
import com.imain.elasticsearchdemo.service.InitService;
import org.apache.lucene.queryparser.xml.QueryBuilderFactory;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    private InitService initService;

    @Autowired
    private PostModelRepository postModelRepository;

    @RequestMapping("init")
    public String init() {
        //initService.init();
        return "success";
    }

    @RequestMapping("all")
    public Iterator<PostModel> all() {
        Iterable<PostModel> list = postModelRepository.findAll();
        return list.iterator();
    }

    /**
     * 单字符串模糊查询，默认排序。将从所有字段中查找包含传来的word分词后字符串的数据集
     *
     * @param word
     * @param pageable
     * @return
     */
    @RequestMapping("/singleWord")
    public Iterator<PostModel> find(String word, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable) {
        //使用queryStringQuery完成单字符串查询
        //QueryBuilder queryBuilder = new QueryStringQueryBuilder(word);
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(word);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
        Iterable<PostModel> list = postModelRepository.search(searchQuery);
        return list.iterator();
    }

    /**
     * 单字段对某字符串模糊查询
     *
     * @param content
     * @param userId
     * @param pageable
     * @return
     */
    @RequestMapping("/singleMatch")
    public Object singleMatch(String content, Integer userId, @PageableDefault Pageable pageable) {
        QueryBuilder queryBuilder = new MatchQueryBuilder("content", content);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("userId", userId)).withPageable(pageable).build();
        return postModelRepository.search(searchQuery).iterator();
    }

    /**
     * 单字段对某短语进行匹配查询，短语分词的顺序会影响结果
     * [slop  相隔多远：意思是你需要移动一个词条多少次来让查询和文档匹配]
     * @param content
     * @param pageable
     * @return
     */
    @RequestMapping("/singlePhraseMatch")
    public Object singlePhraseMatch(String content, @PageableDefault Pageable pageable) {
        MatchPhraseQueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("content",content);
       // MatchPhraseQueryBuilder queryBuilder = new MatchPhraseQueryBuilder("content",content);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build();
        return postModelRepository.search(searchQuery).iterator();
    }


}
