package com.imain.elasticsearchdemo.controller;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

public class SearchTemplateControllerTest {


    /**
     * {
     *   "query": {
     *     "bool": {
     *       "should": [
     *         { "match": { "title":  "War and Peace" }},
     *         { "match": { "author": "Leo Tolstoy"   }},
     *         { "bool":  {
     *           "should": [
     *             { "match": { "translator": "Constance Garnett" }},
     *             { "match": { "translator": "Louise Maude"      }}
     *           ]
     *         }}
     *       ]
     *     }
     *   }
     * }
     */
    @Test
    public void singleTitle() {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("title", "War and Peace"))
                .should(QueryBuilders.matchQuery("author", "Leo Tolstoy"))
                .should(
                        QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("translator", "Constance Garnett"))
                                .should(QueryBuilders.matchQuery("translator", "Louise Maude"))
                );

        System.out.println(queryBuilder);

    }
}