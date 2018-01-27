package com.sdxd.api.vo.certification;

import com.sdxd.user.api.constant.Indigene;
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
public class ResidenceCertification extends Certification {

    @ApiParam(value = "居住状况", required = true)
    @NotNull(message = "居住状况未填写")
    @FormParam(value = "residence_status")
    private String residenceStatus;

    @ApiParam(value = "居住地所在省", required = true)
    @NotNull(message = "居住地所在省未填写")
    @FormParam(value = "residence_province")
    private String residenceProvince;

    @ApiParam(value = "居住地所在市")
    @FormParam(value = "residence_city")
    private String residenceCity;

    @ApiParam(value = "居住地所在区")
    @FormParam(value = "residence_area")
    private String residenceArea;

    @ApiParam(value = "居住地址", required = true)
    @NotNull(message = "居住地址未填写")
    @FormParam(value = "residence_address")
    private String residenceAddress;

    @ApiParam(value = "是否居住地户籍, 0: 否, 1: 是", allowableValues = "Y,N", required = true)
    @NotNull(message = "是否居住地户籍未填写")
    @FormParam(value = "register_in_residence")
    private Indigene registerInResidence;

    @ApiParam(value = "居住年限", required = true)
    @NotNull(message = "居住年限未填写")
    @FormParam(value = "years_of_residence")
    private String yearsOfResidence;

    @ApiParam(value = "区号")
    @FormParam(value = "area_code")
    private String areaCode;

    @ApiParam(value = "居住电话")
    @FormParam(value = "telephone")
    private String telephone;

    public String getResidenceStatus() {
        return residenceStatus;
    }

    public void setResidenceStatus(String residenceStatus) {
        this.residenceStatus = residenceStatus;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public Indigene getRegisterInResidence() {
        return registerInResidence;
    }

    public void setRegisterInResidence(Indigene registerInResidence) {
        this.registerInResidence = registerInResidence;
    }

    public String getYearsOfResidence() {
        return yearsOfResidence;
    }

    public void setYearsOfResidence(String yearsOfResidence) {
        this.yearsOfResidence = yearsOfResidence;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getResidenceProvince() {
        return residenceProvince;
    }

    public void setResidenceProvince(String residenceProvince) {
        this.residenceProvince = residenceProvince;
    }

    public String getResidenceCity() {
        return residenceCity;
    }

    public void setResidenceCity(String residenceCity) {
        this.residenceCity = residenceCity;
    }

    public String getResidenceArea() {
        return residenceArea;
    }

    public void setResidenceArea(String residenceArea) {
        this.residenceArea = residenceArea;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
