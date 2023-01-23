package peaksoft.services.job;

import peaksoft.models.Job;

import java.util.List;

public interface ServiceJob {
    void createJobTable();
    void addJob(Job job);
    Job getJobById(Long jobId);
    List<Job> sortByExperience(String ascOrDesc);
    Job getJobByEmployeeId(Long employeeId);
    void deleteDescriptionColumn();
}
