package com.example.mall.service;


import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private String startTime = "2019-08-09 00:00:00";
    private String endTime = "2019-08-14 09:59:59";
    private String deviceId = "54f07f818f5099c6";

    @Test
    public void thirdReqLog() throws IOException {
        Client client = elasticsearchTemplate.getClient();
        SearchRequestBuilder srb = client.prepareSearch("school").setTypes("student");
        /*//排序
        List<SortBuilder> sortBuilderList = Lists.newArrayList();
        SortBuilder sortBuilder = SortBuilders.fieldSort("_id").order(SortOrder.ASC);
        sortBuilderList.add(sortBuilder);
        if(sortBuilderList != null && !sortBuilderList.isEmpty()){
            for(SortBuilder sortBuilders :sortBuilderList){
                srb.addSort(sortBuilders);//排序
            }
        }*/
        TermsAggregationBuilder gradeTermsBuilder = AggregationBuilders.terms("gradeAgg").field("grade.keyword");
        TermsAggregationBuilder classTermsBuilder = AggregationBuilders.terms("classAgg").field("class.keyword");
        gradeTermsBuilder.subAggregation(classTermsBuilder);
        srb.addAggregation(gradeTermsBuilder);
        SearchResponse sr = srb.setSize(10000)
                //这个游标维持多长时间
                .setScroll(TimeValue.timeValueMinutes(888)).execute().actionGet();
        Map<String, Aggregation> aggMap = sr.getAggregations().asMap();

        StringTerms gradeTerms = (StringTerms) aggMap.get("gradeAgg");

        Iterator<StringTerms.Bucket> gradeBucketIt = gradeTerms.getBuckets().iterator();
        while (gradeBucketIt.hasNext()) {

            Terms.Bucket gradeBucket = gradeBucketIt.next();

            System.out.println(gradeBucket.getKey() + "年级有" + gradeBucket.getDocCount() + "个学生。");

            StringTerms classTerms = (StringTerms) gradeBucket.getAggregations().asMap().get("classAgg");

            Iterator<StringTerms.Bucket> classBucketIt = classTerms.getBuckets().iterator();

            while (classBucketIt.hasNext()) {
                Terms.Bucket classBucket = classBucketIt.next();
                System.out.println(gradeBucket.getKey() + "年级" + classBucket.getKey() + "班有" + classBucket.getDocCount() + "个学生。");
            }
            System.out.println();

        }


    }
}