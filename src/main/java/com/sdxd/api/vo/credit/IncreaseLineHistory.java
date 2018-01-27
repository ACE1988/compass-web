package com.sdxd.api.vo.credit;

/**
 * Created by lenovo on 2017/7/24.
 */
public class IncreaseLineHistory {
    public Integer id;
    public String phone;
    public String name;
    public String creditLine;

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

    public String getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(String creditLine) {
        this.creditLine = creditLine;
    }

    @Override
    public String toString() {
        return "DrawHistory{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", creditLine='" + creditLine + '\'' +
                '}';
    }
}