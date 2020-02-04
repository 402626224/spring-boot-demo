package com.imain.elasticsearchdemo.builders;

import com.imain.elasticsearchdemo.ElasticsearchDemoApplicationTests;
import com.imain.elasticsearchdemo.model.PostModel;
import com.imain.elasticsearchdemo.repository.PostModelRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

public class TermsBuilderTests extends ElasticsearchDemoApplicationTests {

    @Autowired
    private PostModelRepository postModelRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void elasticSearchTest() {
        //1.创建QueryBuilder(即设置查询条件)这儿创建的是组合查询(也叫多条件查询),后面会介绍更多的查询方法
        /*组合查询BoolQueryBuilder
         * must(QueryBuilders)   :AND
         * mustNot(QueryBuilders):NOT
         * should:               :OR
         */
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        //builder下有must、should以及mustNot 相当于sql中的and、or以及not

        //设置模糊搜索,博客的简诉中有学习两个字
        builder.must(QueryBuilders.matchQuery("title", "溪"));

        //设置要查询博客的标题中含有关键字
        builder.must(new QueryStringQueryBuilder("李清照").field("content"));

        //按照博客的评论数的排序是依次降低
        FieldSortBuilder sort = SortBuilders.fieldSort("weight").order(SortOrder.DESC);

        //设置分页(从第一页开始，一页显示10条)
        //注意开始是从0开始，有点类似sql中的方法limit 的查询
        PageRequest page = PageRequest.of(0, 10);

        //2.构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //将搜索条件设置到构建中
        nativeSearchQueryBuilder.withQuery(builder);
        //将分页设置到构建中
        nativeSearchQueryBuilder.withPageable(page);
        //将排序设置到构建中
        nativeSearchQueryBuilder.withSort(sort);
        //生产NativeSearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();

        System.out.println(builder.toString());

        //3.执行方法1
        Page<PostModel> resultPage = postModelRepository.search(query);
        //执行方法2：注意，这儿执行的时候还有个方法那就是使用elasticsearchTemplate
        //执行方法2的时候需要加上注解
        //@Autowired
        //private ElasticsearchTemplate elasticsearchTemplate;
        List<PostModel> blogList = elasticsearchTemplate.queryForList(query, PostModel.class);

        //4.获取总条数(用于前端分页)
        int total = (int) resultPage.getTotalElements();

        //5.获取查询到的数据内容（返回给前端）
        List<PostModel> content = resultPage.getContent();

        content.forEach(m -> {
            System.out.println(m);
        });

    }


}
