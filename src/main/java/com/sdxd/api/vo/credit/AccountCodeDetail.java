package com.sdxd.api.vo.credit;

/**
 * Created by lenovo on 2017/7/24.
 */
public class AccountCodeDetail {

    private Long id;
    private String phone;
    private String name;
    private String accountCode;
    private String annotation;
    private String priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "AccountCodeDetail{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", accountCode='" + accountCode + '\'' +
                ", annotation='" + annotation + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
