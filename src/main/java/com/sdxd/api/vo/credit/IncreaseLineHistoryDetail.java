package com.sdxd.api.vo.credit;

/**
 * Created by lenovo on 2017/7/24.
 */
public class IncreaseLineHistoryDetail {
    public Integer id;
    public String phone;
    public String name;
    public String currentCreditLine;
    public String targetCreditLine;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getCurrentCreditLine() {
        return currentCreditLine;
    }

    public void setCurrentCreditLine(String currentCreditLine) {
        this.currentCreditLine = currentCreditLine;
    }

    public String getTargetCreditLine() {
        return targetCreditLine;
    }

    public void setTargetCreditLine(String targetCreditLine) {
        this.targetCreditLine = targetCreditLine;
    }

    @Override
    public String toString() {
        return "IncreaseLineHistoryDetail{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", currentCreditLine='" + currentCreditLine + '\'' +
                ", targetCreditLine='" + targetCreditLine + '\'' +
                '}';
    }
}
