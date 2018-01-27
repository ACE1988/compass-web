package com.sdxd.api.vo.certification;

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
public class Relatives {

    private String name;
    private String relation;
    private String telephone;
    private String operator;
    private Integer incomingCount;
    private Integer outgoingCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getIncomingCount() {
        return incomingCount;
    }

    public void setIncomingCount(Integer incomingCount) {
        this.incomingCount = incomingCount;
    }

    public Integer getOutgoingCount() {
        return outgoingCount;
    }

    public void setOutgoingCount(Integer outgoingCount) {
        this.outgoingCount = outgoingCount;
    }
}
