package com.zwl.job.dao;

import com.zwl.job.entity.JobItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobItemRepository extends ElasticsearchRepository<JobItem, String> {

    Page<JobItem> findJobItemByJobNameOrJobAreaOrJobMsgOrCompanyNameOrCompanyAreaOrCompanyMsg(
            String jobName, String jobArea, String jobMsg, String companyName,
            String companyArea, String companyMsg, Pageable pageable);

    Page<JobItem> findJobItemByJobName(String jobName, Pageable pageable);

    Page<JobItem> findJobItemByIssueDate(String issueDate, Pageable pageable);
}
