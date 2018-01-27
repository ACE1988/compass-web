package com.sdxd.api.vo.certification;

import com.sdxd.user.api.constant.JobStatus;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.certificate
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/8     melvin                 Created
 */
public class JobCertification extends Certification {

    @ApiParam(value = "就业状态, 工薪: WORKER, 个体: INDIVIDUAL, 其他: OTHERS", allowableValues = "WORKER, INDIVIDUAL, OTHERS", required = true)
    @NotNull(message = "就业状态未填写")
    @FormParam(value = "employment_status")
    private JobStatus employmentStatus;

    //以下字段随就业状态变化而变化

    @ApiParam(value = "职业类型", required = true)
    @NotNull(message = "职业类型未填写")
    @FormParam(value = "career_type")
    private String careerType;

    @ApiParam(value = "还款来源")
    @FormParam(value = "repayment_source")
    private String repaymentSource;

    @ApiParam(value = "单位名称")
    @FormParam(value = "company_name")
    private String companyName;

    @ApiParam(value = "单位所在省")
    @FormParam(value = "company_province")
    private String companyProvince;

    @ApiParam(value = "单位所在市")
    @FormParam(value = "company_city")
    private String companyCity;

    @ApiParam(value = "单位所在区")
    @FormParam(value = "company_area")
    private String companyArea;

    @ApiParam(value = "单位地址")
    @FormParam(value = "company_address")
    private String companyAddress;

    @ApiParam(value = "职级")
    @FormParam(value = "job_level")
    private String jobLevel;

    @ApiParam(value = "工作证明")
    @FormParam(value = "proof_of_employment")
    private String proofOfEmployment;

    @ApiParam(value = "工作证明Path")
    @FormParam(value = "proof_of_employment_path")
    private String proofOfEmploymentPath;

    @ApiParam(value = "最近一份工作时长")
    @FormParam(value = "duration_of_last_job")
    private String durationOfLastJob;

    @ApiParam(value = "月均收入")
    @FormParam(value = "monthly_income")
    private String monthlyIncome;

    @ApiParam(value = "区号")
    @FormParam(value = "area_code")
    private String areaCode;

    @ApiParam(value = "单位电话")
    @FormParam(value = "telephone")
    private String telephone;

    public JobStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(JobStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getCareerType() {
        return careerType;
    }

    public void setCareerType(String careerType) {
        this.careerType = careerType;
    }

    public String getRepaymentSource() {
        return repaymentSource;
    }

    public void setRepaymentSource(String repaymentSource) {
        this.repaymentSource = repaymentSource;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyProvince() {
        return companyProvince;
    }

    public void setCompanyProvince(String companyProvince) {
        this.companyProvince = companyProvince;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyArea() {
        return companyArea;
    }

    public void setCompanyArea(String companyArea) {
        this.companyArea = companyArea;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getProofOfEmployment() {
        return proofOfEmployment;
    }

    public void setProofOfEmployment(String proofOfEmployment) {
        this.proofOfEmployment = proofOfEmployment;
    }

    public String getProofOfEmploymentPath() {
        return proofOfEmploymentPath;
    }

    public void setProofOfEmploymentPath(String proofOfEmploymentPath) {
        this.proofOfEmploymentPath = proofOfEmploymentPath;
    }

    public String getDurationOfLastJob() {
        return durationOfLastJob;
    }

    public void setDurationOfLastJob(String durationOfLastJob) {
        this.durationOfLastJob = durationOfLastJob;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
