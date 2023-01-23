package peaksoft.services.job;

import peaksoft.dao.job.JobDao;
import peaksoft.dao.job.JobDaoImpl;
import peaksoft.models.Job;

import java.util.List;

public class ServiceJobImpl implements ServiceJob{
    private JobDao jobDao = new JobDaoImpl();
    @Override
    public void createJobTable() {
jobDao.createJobTable();
    }

    @Override
    public void addJob(Job job) {
jobDao.addJob(job);
    }

    @Override
    public Job getJobById(Long jobId) {
        return jobDao.getJobById(jobId);
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        return jobDao.sortByExperience(ascOrDesc);
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        return jobDao.getJobByEmployeeId(employeeId);
    }

    @Override
    public void deleteDescriptionColumn() {
jobDao.deleteDescriptionColumn();
    }
}
