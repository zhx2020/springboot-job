package com.zwl.job;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwl.job.dao.JobDao;
import com.zwl.job.dao.JobItemRepository;
import com.zwl.job.entity.Job;
import com.zwl.job.entity.JobItem;
import org.apache.commons.collections.IteratorUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private JobItemRepository jobItemRepository;

    @Test
    public void createIndex() {
        elasticsearchTemplate.createIndex(JobItem.class);
        elasticsearchTemplate.putMapping(JobItem.class);
    }

    @Test
    public void clear() {
        jobItemRepository.deleteAll();
    }

    @Test
    public void put() {
        int page = 1;
        int num = 0;
        do {
            PageHelper.startPage(page, 500);
            List<Job> jobs = jobDao.selectAll();
            PageInfo<Job> pageInfo = new PageInfo<>(jobs);
            List<JobItem> jobItems = new ArrayList<>();
            for (Job job : jobs) {
                JobItem jobItem = new JobItem();
                BeanUtils.copyProperties(job, jobItem);
                jobItems.add(jobItem);
            }
            jobItemRepository.saveAll(jobItems);
            page++;
            num = pageInfo.getSize();
        } while (num == 500);
    }

    @Test
    public void delete() {
        System.out.println(jobDao.deleteByExample(null));
    }

    @Test
    public void search() {
        //词条查询
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("jobArea", "苏州");
        //执行
        Iterable<JobItem> items = jobItemRepository.search(queryBuilder);
        List<JobItem> list = IteratorUtils.toList(items.iterator());
        System.out.println("amount = " + list.size());
    }

    @Test
    public void search2() {
        //分页查询
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //查询条件
        queryBuilder.withQuery(QueryBuilders.termQuery("companyType", "民营公司"));
        //分页参数
        int page = 0, size = 5;
        queryBuilder.withPageable(PageRequest.of(page, size));
        Page<JobItem> items = jobItemRepository.search(queryBuilder.build());
        System.out.println("总条数:" + items.getTotalElements());
        System.out.println("总页数:" + items.getTotalPages());
        System.out.println("当前页:" + items.getNumber());
    }

    @Test
    public void search3() {
        //分页查询
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //查询条件
        queryBuilder.withQuery(QueryBuilders.termQuery("companyType", "民营公司"));
        //排序
        queryBuilder.withSort(SortBuilders.fieldSort("salaryMin").order(SortOrder.ASC));
        Page<JobItem> items = jobItemRepository.search(queryBuilder.build());
        System.out.println("---");
    }

    @Test
    public void search4() {
        //聚合查询
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //定义一个聚合
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        queryBuilder.addAggregation(AggregationBuilders.terms("area").field("jobArea").size(500));
        //查询
        AggregatedPage<JobItem> page = (AggregatedPage<JobItem>) jobItemRepository.search(queryBuilder.build());
        StringTerms ag = (StringTerms) page.getAggregation("area");
        //获取桶子
        List<StringTerms.Bucket> buckets = ag.getBuckets();
        for (StringTerms.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString() + ":" + bucket.getDocCount());
        }
    }

    @Test
    public void search5() {
        //聚合查询,平均价格
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //定义一个聚合
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        queryBuilder.addAggregation(AggregationBuilders.terms("companyType").field("companyType").subAggregation(
                                    AggregationBuilders.avg("avgPrice").field("salaryMin")));
        //查询
        AggregatedPage<JobItem> page = (AggregatedPage<JobItem>) jobItemRepository.search(queryBuilder.build());
        StringTerms ag = (StringTerms) page.getAggregation("companyType");
        //获取桶子
        List<StringTerms.Bucket> buckets = ag.getBuckets();
        for (StringTerms.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString() + ":" + bucket.getDocCount());
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("avgPrice");
            System.out.println("avg = " + avg.getValue());
        }
    }

    @Test
    public void search6() {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

        SearchRequest searchRequest = new SearchRequest("test").types("job");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        TermsAggregationBuilder aggregationColor = AggregationBuilders
                .terms("type")  //聚合名称
                .field("companyType");   //分组属性

        //最小值
        aggregationColor.subAggregation(
                AggregationBuilders.min("min_price")
                        .field("salaryMin")
        );

        //最大值
        aggregationColor.subAggregation(
                AggregationBuilders.max("max_price")
                        .field("salaryMin")
        );

        //平均值
        aggregationColor.subAggregation(
                AggregationBuilders.avg("avg_price")
                        .field("salaryMin")
        );

        //求和
        aggregationColor.subAggregation(
                AggregationBuilders.sum("sum_price")
                        .field("salaryMin")
        );

        searchSourceBuilder.aggregation(aggregationColor);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;

        try{
            searchResponse = client.search(searchRequest);
        }catch (IOException e){
            e.printStackTrace();
        }

        Aggregations aggregations = searchResponse.getAggregations();
        Terms groupby_colors = aggregations.get("type");
        List<? extends Terms.Bucket> colorsBuckets = groupby_colors.getBuckets();
        for(Terms.Bucket colorsbucket : colorsBuckets){
            String color = (String)colorsbucket.getKey();
            Min min_price = colorsbucket.getAggregations().get("min_price");
            Max max_price = colorsbucket.getAggregations().get("max_price");
            Avg avg_price = colorsbucket.getAggregations().get("avg_price");
            Sum sum_price = colorsbucket.getAggregations().get("sum_price");

            System.out.println("color : " + color
                    + ", min : " + min_price.getValue()
                    + ", max : " + max_price.getValue()
                    + ", avg : " + avg_price.getValue()
                    + ", sum : " + sum_price.getValue());
        }
    }

    @Test
    public void search7() {
        //聚合查询,平均价格
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //定义一个聚合
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        queryBuilder.addAggregation(AggregationBuilders.terms("companyType").field("companyType").subAggregation(
                AggregationBuilders.sum("sumPrice").field("salaryMin")));
        //查询
        AggregatedPage<JobItem> page = (AggregatedPage<JobItem>) jobItemRepository.search(queryBuilder.build());
        StringTerms ag = (StringTerms) page.getAggregation("companyType");
        //获取桶子
        List<StringTerms.Bucket> buckets = ag.getBuckets();
        for (StringTerms.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString() + ":" + bucket.getDocCount());
            InternalSum avg = (InternalSum) bucket.getAggregations().asMap().get("sumPrice");
            Sum sum =  bucket.getAggregations().get("sumPrice");
//            System.out.println("sum = " + sum.getValue());
            System.out.println("sum = " + avg.getValue());
        }
    }

    @Test
    public void search8() {
        AggregatedPage<JobItem> aggregatedPage = search9();
        System.out.println("---");
    }

    public AggregatedPage<JobItem> search9() {
        HighlightBuilder.Field nameField = new HighlightBuilder
                .Field("*")
                .preTags("<span style='color:red'>")
                .postTags("</span>").requireFieldMatch(false);
        //多字段查询，可同时在name和description查询 对应实体类中的属性名
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("开发", "jobName", "companyName"))
                .withPageable(PageRequest.of(1, 10))
                .withHighlightFields(nameField)
                .build();
        AggregatedPage<JobItem> products = elasticsearchTemplate.
                queryForPage(nativeSearchQuery, JobItem.class, new SearchResultMapper() {
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                        SearchHits searchHits = response.getHits();
                        SearchHit[] hits = searchHits.getHits();
                        ArrayList<JobItem> products = new ArrayList<>();
                        for (SearchHit hit : hits) {
                            JobItem jobItem = new JobItem();
                            //原始map
                            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                            System.out.println("---");
                            jobItem.setJobId(sourceAsMap.get("jobId").toString());
                            jobItem.setJobName(sourceAsMap.get("jobName").toString());
                            jobItem.setJobArea(sourceAsMap.get("companyName").toString());
                            //高亮
                            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                            System.err.println(highlightFields);
                            if (highlightFields.get("jobName") != null) {
                                String nameHighlight = highlightFields.get("jobName").getFragments()[0].toString();
                                jobItem.setJobName(nameHighlight);
                            }
                            if (highlightFields.get("companyName") != null) {
                                String contentHighlight = highlightFields.get("companyName").getFragments()[0].toString();
                                jobItem.setJobArea(contentHighlight);
                            }
                            products.add(jobItem);
                        }
                        return new AggregatedPageImpl<T>((List<T>) products);
                    }
                    @Override
                    public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                        return null;
                    }
                });
        return products;
    }

    @Test
    public void search10() {
        String str = "苏州-南山区";
        String[] arr = str.split("-");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void test01() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.addAggregation(AggregationBuilders.sum("min").field("salaryMin"));
        AggregatedPage<JobItem> page = (AggregatedPage<JobItem>) jobItemRepository.search(queryBuilder.build());
        InternalSum sum = (InternalSum) page.getAggregations().asList().get(0);
        System.out.println("s1 = " + sum.getValue());
        Sum s = page.getAggregations().get("min");
        System.out.println("s2 = " + s);
    }

    @Test
    public void test02() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("jobName", "java"));
        queryBuilder.addAggregation(AggregationBuilders.avg("min").field("salaryMin"));
        queryBuilder.addAggregation(AggregationBuilders.avg("max").field("salaryMax"));
        AggregatedPage<JobItem> page = (AggregatedPage<JobItem>) jobItemRepository.search(queryBuilder.build());
        Avg min = page.getAggregations().get("min");
        System.out.println("min = " + min);
        Avg max = page.getAggregations().get("max");
        System.out.println("max = " + max);
    }
}
