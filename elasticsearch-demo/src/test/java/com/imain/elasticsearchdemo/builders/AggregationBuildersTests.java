package com.imain.elasticsearchdemo.builders;

import com.imain.elasticsearchdemo.ElasticsearchDemoApplicationTests;
import com.imain.elasticsearchdemo.model.PostModel;
import com.imain.elasticsearchdemo.repository.PostModelRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AggregationBuildersTests extends ElasticsearchDemoApplicationTests {

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

    public void origin() {
        //目标：搜索写博客写得最多的用户（一个博客对应一个用户），通过搜索博客中的用户名的频次来达到想要的结果
        //首先新建一个用于存储数据的集合
        List<String> userNameList = new ArrayList<>();
        //1.创建查询条件，也就是QueryBuild
        QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();//设置查询所有，相当于不设置查询条件
        //2.构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //2.0 设置QueryBuilder
        nativeSearchQueryBuilder.withQuery(matchAllQuery);
        //2.1设置搜索类型，默认值就是QUERY_THEN_FETCH，参考https://blog.csdn.net/wulex/article/details/71081042
        nativeSearchQueryBuilder.withSearchType(SearchType.QUERY_THEN_FETCH);//指定索引的类型，只先从各分片中查询匹配的文档，再重新排序和排名，取前size个文档
        //2.2指定索引库和文档类型
        nativeSearchQueryBuilder.withIndices("myBlog").withTypes("blog");//指定要查询的索引库的名称和类型，其实就是我们文档@Document中设置的indedName和type
        //2.3重点来了！！！指定聚合函数,本例中以某个字段分组聚合为例（可根据你自己的聚合查询需求设置）
        //该聚合函数解释：计算该字段(假设为username)在所有文档中的出现频次，并按照降序排名（常用于某个字段的热度排名）
        TermsAggregationBuilder termsAggregation = AggregationBuilders.terms("给聚合查询取的名").field("username").order(BucketOrder.count(false));
        nativeSearchQueryBuilder.addAggregation(termsAggregation);
        //2.4构建查询对象
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        //3.执行查询
        //3.1方法1,通过reporitory执行查询,获得有Page包装了的结果集
        Page<PostModel> search = postModelRepository.search(nativeSearchQuery);
        List<PostModel> content = search.getContent();
        for (PostModel PostModel : content) {
            userNameList.add(PostModel.getTitle());
        }
        //获得对应的文档之后我就可以获得该文档的作者，那么就可以查出最热门用户了
        //3.2方法2,通过elasticSearch模板elasticsearchTemplate.queryForList方法查询
        List<PostModel> queryForList = elasticsearchTemplate.queryForList(nativeSearchQuery, PostModel.class);
        //3.3方法3,通过elasticSearch模板elasticsearchTemplate.query()方法查询,获得聚合(常用)
        Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        //转换成map集合
        Map<String, Aggregation> aggregationMap = aggregations.asMap();
        //获得对应的聚合函数的聚合子类，该聚合子类也是个map集合,里面的value就是桶Bucket，我们要获得Bucket
        StringTerms stringTerms = (StringTerms) aggregationMap.get("给聚合查询取的名");
        //获得所有的桶
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        //将集合转换成迭代器遍历桶,当然如果你不删除buckets中的元素，直接foreach遍历就可以了
        Iterator<StringTerms.Bucket> iterator = buckets.iterator();

        while (iterator.hasNext()) {
            //bucket桶也是一个map对象，我们取它的key值就可以了
            String username = iterator.next().getKeyAsString();//或者bucket.getKey().toString();
            //根据username去结果中查询即可对应的文档，添加存储数据的集合
            userNameList.add(username);
        }
        //最后根据ueserNameList搜索对应的结果集
//            List<User> listUsersByUsernames = userService.listUsersByUsernames(ueserNameList);

    }

    @Test
    public void testAggregation() throws Exception {
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        //2.构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //2.0 设置QueryBuilder
        nativeSearchQueryBuilder.withQuery(queryBuilder);

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("tCount").field("userId").size(5);
        nativeSearchQueryBuilder.addAggregation(aggregationBuilder);
        nativeSearchQueryBuilder.withIndices("projectname").withTypes("post");

//        List<PostModel> result = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), PostModel.class);
//        result.forEach(System.out::println);

        Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQueryBuilder.build(), new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        Map<String, Aggregation> map = aggregations.asMap();
        System.out.println(map);
        Terms terms = (Terms) map.get("tCount");
        terms.getBuckets().forEach(item -> {
            System.out.println("userId:" + item.getKeyAsString() + " -- count:" + item.getDocCount());
        });


    }

    @Test
    public void indexTest() {
        boolean result = elasticsearchTemplate.createIndex(PostModel.class);
        System.out.println("index result is :" + result);
    }

}
