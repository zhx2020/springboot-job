package com.zwl.job.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.job.dao.JobItemRepository;
import com.zwl.job.dao.UserDao;
import com.zwl.job.entity.JobItem;
import com.zwl.job.entity.Result;
import com.zwl.job.entity.User;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobItemService {

    @Autowired
    private JobItemRepository jobItemRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private UserDao userDao;

    public Page<JobItem> find(String keyWord, int page) {
        return jobItemRepository.findJobItemByJobNameOrJobAreaOrJobMsgOrCompanyNameOrCompanyAreaOrCompanyMsg(
                keyWord, keyWord, keyWord, keyWord, keyWord, keyWord, PageRequest.of(page - 1, 50)
        );
    }

    public Page<JobItem> findByJobName(String jobName, int page) {
        return jobItemRepository.findJobItemByJobName(jobName, PageRequest.of(page - 1, 50));
    }

    public Page<JobItem> findByIssueDate(String issueDate, int page) {
        return jobItemRepository.findJobItemByIssueDate(issueDate, PageRequest.of(page - 1, 50));
    }

    public Result getJobEducation() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        queryBuilder.addAggregation(AggregationBuilders.terms("education").field("jobEducation").size(100));
        AggregatedPage<JobItem> page = (AggregatedPage<JobItem>) jobItemRepository.search(queryBuilder.build());
        StringTerms ag = (StringTerms) page.getAggregation("education");
        List<StringTerms.Bucket> buckets = ag.getBuckets();
        List<JSONObject> list = new ArrayList<>();
        String[] arr = new String[buckets.size()];
        int i = 0;
        for (StringTerms.Bucket bucket : buckets) {
            JSONObject object = new JSONObject();
            object.put("value", bucket.getDocCount());
            object.put("name", bucket.getKeyAsString());
            arr[i++] = bucket.getKeyAsString();
            list.add(object);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("arr", arr);
        map.put("list", list);
        return new Result(true, "OK", map);
    }

    public Result getJobYear() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        queryBuilder.addAggregation(AggregationBuilders.terms("year").field("jobYear").size(100));
        AggregatedPage<JobItem> page = (AggregatedPage<JobItem>) jobItemRepository.search(queryBuilder.build());
        StringTerms ag = (StringTerms) page.getAggregation("year");
        List<StringTerms.Bucket> buckets = ag.getBuckets();
        List<JSONObject> list = new ArrayList<>();
        String[] arr = new String[buckets.size()];
        int i = 0;
        for (StringTerms.Bucket bucket : buckets) {
            JSONObject object = new JSONObject();
            object.put("value", bucket.getDocCount());
            object.put("name", bucket.getKeyAsString());
            arr[i++] = bucket.getKeyAsString();
            list.add(object);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("arr", arr);
        map.put("list", list);
        return new Result(true, "OK", map);
    }

    public Result getCompanyType() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        queryBuilder.addAggregation(AggregationBuilders.terms("type").field("companyType").size(100));
        AggregatedPage<JobItem> page = (AggregatedPage<JobItem>) jobItemRepository.search(queryBuilder.build());
        StringTerms ag = (StringTerms) page.getAggregation("type");
        List<StringTerms.Bucket> buckets = ag.getBuckets();
        List<JSONObject> list = new ArrayList<>();
        String[] arr = new String[buckets.size()];
        int i = 0;
        for (StringTerms.Bucket bucket : buckets) {
            JSONObject object = new JSONObject();
            object.put("value", bucket.getDocCount());
            object.put("name", bucket.getKeyAsString());
            arr[i++] = bucket.getKeyAsString();
            list.add(object);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("arr", arr);
        map.put("list", list);
        return new Result(true, "OK", map);
    }

    public Long getJobAmountByJobName(String jobName) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("jobName", jobName));
        Page<JobItem> page = jobItemRepository.search(queryBuilder.build());
        return page.getTotalElements();
    }

    public Long getJobAmountByJobArea(String jobArea) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.termQuery("jobArea", jobArea));
        Page<JobItem> page = jobItemRepository.search(queryBuilder.build());
        return page.getTotalElements();
    }

    public Result getJobAmount() {
        String[] arr = {"java", "php", "c/c++", "python", "大数据", "android", "ios", "前端", "测试", "运维"};
        Long[] amount = new Long[arr.length];
        for (int i = 0; i < arr.length; i++) {
            amount[i] = getJobAmountByJobName(arr[i]);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("arr", arr);
        map.put("amount", amount);
        return new Result(true, "OK", map);
    }

    public Result getJobArea() {
        String[] arr = {"北京", "上海", "广州", "深圳", "武汉", "西安", "杭州", "南京", "成都", "重庆", "东莞",
                        "大连", "沈阳", "苏州", "昆明", "长沙", "合肥", "宁波", "郑州", "天津", "青岛", "济南",
                        "哈尔滨", "长春", "福州"};
        Long[] amount = new Long[arr.length];
        for (int i = 0; i < arr.length; i++) {
            amount[i] = getJobAmountByJobArea(arr[i]);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("arr", arr);
        map.put("amount", amount);
        return new Result(true, "OK", map);
    }

    public Result getJobSalary() {
        String[] arr = {"java", "php", "c/c++", "python", "大数据", "android", "ios", "前端", "测试", "运维"};
        int[] min = new int[arr.length];
        int[] max = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            min[i] = getJobSalaryByJobName(arr[i])[0];
            max[i] = getJobSalaryByJobName(arr[i])[1];
        }
        Map<String, Object> map = new HashMap<>();
        map.put("arr", arr);
        map.put("min", min);
        map.put("max", max);
        return new Result(true, "OK", map);
    }

    public int[] getJobSalaryByJobName(String jobName) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.matchQuery("jobName", jobName));
        builder.mustNot(QueryBuilders.termQuery("salaryMin", 0));
        builder.mustNot(QueryBuilders.termQuery("salaryMax", 0));
        queryBuilder.withQuery(builder);
        queryBuilder.addAggregation(AggregationBuilders.avg("min").field("salaryMin"));
        queryBuilder.addAggregation(AggregationBuilders.avg("max").field("salaryMax"));
        AggregatedPage<JobItem> page = (AggregatedPage<JobItem>) jobItemRepository.search(queryBuilder.build());
        int[] arr = new int[2];
        Avg min = page.getAggregations().get("min");
        Avg max = page.getAggregations().get("max");
        arr[0] = (int) min.getValue();
        arr[1] = (int) max.getValue();
        return arr;
    }

    public Result getJobWelf() {
        StringBuilder sb = new StringBuilder();
        Iterable<JobItem> iterable = jobItemRepository.findAll();
        Iterator<JobItem> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            JobItem jobItem = iterator.next();
            sb.append(jobItem.getJobWelf() + " ");
        }
        return new Result(true,"OK", sb.toString());
    }

    public Result getJobName() {
        StringBuilder sb = new StringBuilder();
        Iterable<JobItem> iterable = jobItemRepository.findAll();
        Iterator<JobItem> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            JobItem jobItem = iterator.next();
            sb.append(jobItem.getJobName() + " ");
        }
        return new Result(true,"OK", sb.toString());
    }

    public Result getCompanyInd() {
        StringBuilder sb = new StringBuilder();
        Iterable<JobItem> iterable = jobItemRepository.findAll();
        Iterator<JobItem> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            JobItem jobItem = iterator.next();
            sb.append(jobItem.getCompanyInd() + " ");
        }
        return new Result(true,"OK", sb.toString());
    }

    public Page<JobItem> find(String keyWord, String jobArea, Integer salaryMin, Integer salaryMax, String jobYear, String jobEducation,
                         String companyType, String companySize, Integer page, Integer size) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
//        builder.should(QueryBuilders.matchQuery("jobName", keyWord));
//        builder.should(QueryBuilders.matchQuery("companyName", keyWord));
        if (!"".equals(keyWord) && !"请选择".equals(keyWord)) {
            builder.must(QueryBuilders.multiMatchQuery(keyWord, "jobName", "companyName"));
//            builder.must(QueryBuilders.termsQuery("jobName", "java", "开发"));
        }
        if (!"请选择".equals(jobArea)) {
            builder.must(QueryBuilders.termQuery("jobArea", jobArea));
        }
        if (!"所有".equals(jobEducation) && !"请选择".equals(jobEducation)) {
            builder.must(QueryBuilders.termQuery("jobEducation", jobEducation));
        }
        if (!"所有".equals(jobYear) && !"请选择".equals(jobYear)) {
            builder.must(QueryBuilders.termQuery("jobYear", jobYear));
        }
        if (!"所有".equals(companyType) && !"请选择".equals(companyType)) {
            builder.must(QueryBuilders.termQuery("companyType", companyType));
        }
        if (!"所有".equals(companySize) && !"请选择".equals(companySize)) {
            builder.must(QueryBuilders.termQuery("companySize", companySize));
        }
        builder.must(QueryBuilders.rangeQuery("salaryMin").from(salaryMin).to(salaryMax));
        builder.must(QueryBuilders.rangeQuery("salaryMax").from(salaryMin).to(salaryMax));
        FieldSortBuilder sort = SortBuilders.fieldSort("issueDate").order(SortOrder.DESC);
        queryBuilder.withQuery(builder);
        queryBuilder.withSort(sort);
//        int page = 0, size = 10;
        queryBuilder.withPageable(PageRequest.of(page - 1, size));
        Page<JobItem> items = jobItemRepository.search(queryBuilder.build());
        return items;
    }

    public Result search(String keyWord, String jobArea, Integer salaryMin, Integer salaryMax, String jobYear, String jobEducation,
                                String companyType, String companySize, Integer page, Integer size) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
//        builder.should(QueryBuilders.matchQuery("jobName", keyWord));
//        builder.should(QueryBuilders.matchQuery("companyName", keyWord));
        if (!"".equals(keyWord) && !"请选择".equals(keyWord)) {
            builder.must(QueryBuilders.multiMatchQuery(keyWord, "jobName", "companyName"));
//            builder.must(QueryBuilders.termsQuery("jobName", "java", "开发"));
        }
        if (!"请选择".equals(jobArea)) {
            builder.must(QueryBuilders.termQuery("jobArea", jobArea));
        }
        if (!"所有".equals(jobEducation) && !"请选择".equals(jobEducation)) {
            builder.must(QueryBuilders.termQuery("jobEducation", jobEducation));
        }
        if (!"所有".equals(jobYear) && !"请选择".equals(jobYear)) {
            builder.must(QueryBuilders.termQuery("jobYear", jobYear));
        }
        if (!"所有".equals(companyType) && !"请选择".equals(companyType)) {
            builder.must(QueryBuilders.termQuery("companyType", companyType));
        }
        if (!"所有".equals(companySize) && !"请选择".equals(companySize)) {
            builder.must(QueryBuilders.termQuery("companySize", companySize));
        }
        builder.must(QueryBuilders.rangeQuery("salaryMin").from(salaryMin).to(salaryMax));
        builder.must(QueryBuilders.rangeQuery("salaryMax").from(salaryMin).to(salaryMax));
        FieldSortBuilder sort = SortBuilders.fieldSort("issueDate").order(SortOrder.DESC);
        queryBuilder.withQuery(builder);
        queryBuilder.withSort(sort);
//        int page = 0, size = 10;
        queryBuilder.withPageable(PageRequest.of(page - 1, size));
        Page<JobItem> items = jobItemRepository.search(queryBuilder.build());
        return new Result(true, "OK", items);
    }

    public Result search2(String keyWord, String jobArea, Integer salaryMin, Integer salaryMax, String jobYear, String jobEducation,
                         String companyType, String companySize, Integer page, Integer size) {
        HighlightBuilder.Field nameField = new HighlightBuilder
                .Field("*")
                .preTags("<span style='color:red'>")
                .postTags("</span>").requireFieldMatch(false);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if (!"".equals(keyWord)) {
            builder.must(QueryBuilders.multiMatchQuery(keyWord, "jobName", "companyName"));
        }
        if (!"请选择".equals(jobArea)) {
            builder.must(QueryBuilders.termQuery("jobArea", jobArea));
        }
        if (!"所有".equals(jobEducation)) {
            builder.must(QueryBuilders.termQuery("jobEducation", jobEducation));
        }
        if (!"所有".equals(jobYear)) {
            builder.must(QueryBuilders.termQuery("jobYear", jobYear));
        }
        if (!"所有".equals(companyType)) {
            builder.must(QueryBuilders.termQuery("companyType", companyType));
        }
        if (!"所有".equals(companySize)) {
            builder.must(QueryBuilders.termQuery("companySize", companySize));
        }
        builder.must(QueryBuilders.rangeQuery("salaryMin").from(salaryMin).to(salaryMax));
        builder.must(QueryBuilders.rangeQuery("salaryMax").from(salaryMin).to(salaryMax));
        FieldSortBuilder sort = SortBuilders.fieldSort("issueDate").order(SortOrder.DESC);
        queryBuilder.withQuery(builder);
        queryBuilder.withSort(sort);
        queryBuilder.withHighlightFields(nameField);
        queryBuilder.withPageable(PageRequest.of(page - 1, size));
        AggregatedPage<JobItem> products = elasticsearchTemplate.
                queryForPage(queryBuilder.build(), JobItem.class, new SearchResultMapper() {
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                        SearchHits searchHits = response.getHits();
                        SearchHit[] hits = searchHits.getHits();
                        ArrayList<JobItem> products = new ArrayList<>();
                        for (SearchHit hit : hits) {
                            JobItem jobItem = new JobItem();
                            //原始map
                            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                            jobItem.setJobId(sourceAsMap.get("jobId").toString());
                            jobItem.setJobName(sourceAsMap.get("jobName").toString());
                            jobItem.setJobHref(sourceAsMap.get("jobHref").toString());
                            jobItem.setJobArea(sourceAsMap.get("jobArea").toString());
                            jobItem.setJobWelf(sourceAsMap.get("jobWelf").toString());
                            jobItem.setJobMsg(sourceAsMap.get("jobMsg").toString());
                            jobItem.setJobYear(sourceAsMap.get("jobYear").toString());
                            jobItem.setJobEducation(sourceAsMap.get("jobEducation").toString());
                            jobItem.setJobDemand(sourceAsMap.get("jobDemand").toString());
                            jobItem.setJobAttr(sourceAsMap.get("jobAttr").toString());
                            jobItem.setSalaryMin(Integer.parseInt(sourceAsMap.get("salaryMin").toString()));
                            jobItem.setSalaryMax(Integer.parseInt(sourceAsMap.get("salaryMax").toString()));
                            jobItem.setIssueDate(sourceAsMap.get("issueDate").toString());
                            jobItem.setCompanyName(sourceAsMap.get("companyName").toString());
                            jobItem.setCompanyHref(sourceAsMap.get("companyHref").toString());
                            jobItem.setCompanyArea(sourceAsMap.get("companyArea").toString());
                            jobItem.setCompanyType(sourceAsMap.get("companyType").toString());
                            jobItem.setCompanySize(sourceAsMap.get("companySize").toString());
                            jobItem.setCompanyInd(sourceAsMap.get("companyInd").toString());
                            jobItem.setCompanyAttr(sourceAsMap.get("companyAttr").toString());
                            jobItem.setCompanyMsg(sourceAsMap.get("companyMsg").toString());
                            //高亮
                            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                            if (highlightFields.get("jobName") != null) {
                                String nameHighlight = highlightFields.get("jobName").getFragments()[0].toString();
                                jobItem.setJobName(nameHighlight);
                            }
                            if (highlightFields.get("companyName") != null) {
                                String contentHighlight = highlightFields.get("companyName").getFragments()[0].toString();
                                jobItem.setCompanyName(contentHighlight);
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
        Page<JobItem> items = jobItemRepository.search(queryBuilder.build());
        Map<String, Object> map = new HashMap<>();
        map.put("content", products.getContent());
        map.put("totalPages", items.getTotalPages());
        map.put("totalElements", items.getTotalElements());
        return new Result(true, "OK", map);
    }

    public Result findById(String id) {
        return new Result(true, "OK", jobItemRepository.findById(id).get());
    }

    public Result push(String id) {
        User user = userDao.findById(id);
        String str = user.getSalary().getSalaryName();
        int min = 1000;
        int max = 30000;
        if (!"请选择".equals(str)) {
            str = str.replace("k", "");
            String[] arr = str.split("-");
            min = Integer.parseInt(arr[0]) * 1000;
            max = Integer.parseInt(arr[1]) * 1000;
        }
        Page<JobItem> page = find(user.getPosition().getPositionName(), user.getArea().getAreaName(), min, max,
                user.getYear().getYearName(), user.getEducation().getEducationName(), "所有", "所有", 1, 3);
        int target = new Random().nextInt(page.getTotalPages()) + 1;
        System.out.println("target = " + target);
        return search(user.getPosition().getPositionName(), user.getArea().getAreaName(), min, max,
                user.getYear().getYearName(), user.getEducation().getEducationName(), "所有", "所有", target, 3);
    }
}
