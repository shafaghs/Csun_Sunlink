package com.csun_sunlink.csuncareercenter.Search;

public class ItemInfo {

    private String jobTitle,jobDuties,jobSummary,essentialSkills,postedDate,companyName,companyAddress,jobId;

    public ItemInfo(String mJobId, String mJobTitle, String mCompanyName, String mPostedDate, String mCompanyAddress) {
        this.jobId = mJobId;
        this.jobTitle = mJobTitle;
        this.companyName = mCompanyName;
        this.postedDate = mPostedDate;
        this.companyAddress = mCompanyAddress;
    }

    String getJobId() {
        return jobId;
    }
    public void setJobId(String mJobId) {
        this.jobId = mJobId;
    }

    String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String mJobTitle) {
        this.jobTitle = mJobTitle;
    }

    String getJobDuties() {
        return jobDuties;
    }
    public void setJobDuties(String mJobDuties) {
        this.jobDuties = mJobDuties;
    }

    String getJobSummary() {
        return jobSummary;
    }
    public void setJobSummary(String mJobSummary) {
        this.jobSummary = mJobSummary;
    }

    String getEssentialSkills() {
        return essentialSkills;
    }
    public void setEssentialSkills(String mEssentialSkills) {
        this.essentialSkills = mEssentialSkills;
    }

    String getPostedDate() {
        return postedDate;
    }
    public void setPostedDate(String mPostedDate) {
        this.postedDate = mPostedDate;
    }

    String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String mCompanyName) {
        this.companyName = mCompanyName;
    }

    String getCompanyAddress() {
        return companyAddress;
    }
    public void setCompanyAddress(String mCompanyAddress) {
        this.companyAddress = mCompanyAddress;
    }
}
