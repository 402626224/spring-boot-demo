package com.imain.elasticsearchdemo.builders;

import com.imain.elasticsearchdemo.ElasticsearchDemoApplicationTests;
import com.imain.elasticsearchdemo.model.PostModel;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

public class QueryBuildersTests extends ElasticsearchDemoApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 单个匹配termQuery
     */
    @Test
    public void singleMatchTest() {
        //不分词查询 参数1： 字段名，参数2：字段查询值，因为不分词，所以汉字只能查询一个字，英语是一个单词.
        // 汉字只能查询一个字，英语是一个单词 [浣,溪,沙]
        QueryBuilder queryBuilder = QueryBuilders.termQuery("title", "溪");
        //分词查询，采用默认的分词器  [浣溪沙,浣沙,溪]
        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("title", "浣沙");

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        List<PostModel> result = elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
        result.forEach(System.out::println);
    }

    /**
     * 多个匹配
     */
    @Test
    public void multiMatchTest() {
        //不分词查询，参数1： 字段名，参数2：多个字段查询值,因为不分词，所以汉字只能查询一个字，英语是一个单词.
        // title 中含有 "梦" 或者 "唇"
        QueryBuilder queryBuilder = QueryBuilders.termsQuery("title", "梦", "唇");
        //分词查询，采用默认的分词器  [ title 或者 content 中 含有 "日"]
        QueryBuilder queryBuilder1 = QueryBuilders.multiMatchQuery("日", "title", "content");
        //匹配所有文件，相当于就没有设置查询条件
        QueryBuilder queryBuilder2 = QueryBuilders.matchAllQuery();

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        List<PostModel> result = elasticsearchTemplate.queryForList(searchQuery, PostModel.class);
        result.forEach(System.out::println);
    }

    /**
     * 模糊查询
     */
    @Test
    public void fuzzyQueryTest() {
        //模糊查询常见的5个方法如下
        //1.常用的字符串查询
        QueryBuilders.queryStringQuery("fieldValue").field("fieldName");//左右模糊
        //2.常用的用于推荐相似内容的查询
        //QueryBuilders.moreLikeThisQuery(new String[] {"fieldName"}).addLikeText("pipeidhua");//如果不指定filedName，则默认全部，常用在相似内容的推荐上
        //3.前缀查询  如果字段没分词，就匹配整个字段前缀
        QueryBuilders.prefixQuery("fieldName", "fieldValue");
        //4.fuzzy query:分词模糊查询，通过增加fuzziness模糊属性来查询,如能够匹配hotelName为tel前或后加一个字母的文档，fuzziness 的含义是检索的term 前后增加或减少n个单词的匹配查询
        QueryBuilders.fuzzyQuery("hotelName", "tel").fuzziness(Fuzziness.ONE);
        //5.wildcard query:通配符查询，支持* 任意字符串；？任意一个字符
        QueryBuilders.wildcardQuery("fieldName", "ctr*");//前面是fieldname，后面是带匹配字符的字符串
        QueryBuilders.wildcardQuery("fieldName", "c?r?");

    }

    /**
     * 区间查询
     */
    @Test
    public void rangeQueryTest() {
        //闭区间查询
        QueryBuilder queryBuilder0 = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2");
        //开区间查询
        QueryBuilder queryBuilder1 = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2").includeUpper(false).includeLower(false);//默认是true，也就是包含
        //大于
        QueryBuilder queryBuilder2 = QueryBuilders.rangeQuery("fieldName").gt("fieldValue");
        //大于等于
        QueryBuilder queryBuilder3 = QueryBuilders.rangeQuery("fieldName").gte("fieldValue");
        //小于
        QueryBuilder queryBuilder4 = QueryBuilders.rangeQuery("fieldName").lt("fieldValue");
        //小于等于
        QueryBuilder queryBuilder5 = QueryBuilders.rangeQuery("fieldName").lte("fieldValue");
    }

    /**
     * 组合查询/多条件查询/布尔查询
     */
    @Test
    public void boolQuery() {
        QueryBuilders.boolQuery(); // 组合
        QueryBuilders.boolQuery().must();//文档必须完全匹配条件，相当于and
        QueryBuilders.boolQuery().mustNot();//文档必须不匹配条件，相当于not
        QueryBuilders.boolQuery().should();//至少满足一个条件，这个文档就符合should，相当于or
    }


}
