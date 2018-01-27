package com.sdxd.api.vo.certification;

import com.sdxd.common.web.util.FieldIgnore;
import com.sdxd.user.api.constant.UserIdentityStatus;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class ProfileCertification extends Certification {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @FieldIgnore
    private String name;

    private String cellphone;

    private String sex;

    private Date birthday;

    private String address;

    @FieldIgnore
    private String idNo;

    @FieldIgnore
    private UserIdentityStatus faceStatus;

    @ApiParam(value = "人脸识别Code")
    @FormParam(value = "face")
    private String face;

    @ApiParam(value = "人脸识别Path")
    @FormParam(value = "face_path")
    private String facePath;

    @ApiParam(value = "人脸识别Delta")
    @FormParam(value = "face_delta")
    private String faceDelta;

    @ApiParam(value = "婚姻状况", required = true)
    @NotNull(message = "婚姻状况未填写")
    @FormParam(value = "marital_status")
    private String maritalStatus;

    @ApiParam(value = "学历", required = true)
    @NotNull(message = "学历未填写")
    @FormParam(value = "education")
    private String education;

    @ApiParam(value = "邮箱")
    @FormParam(value = "mail")
    private String mail;

    @ApiModelProperty("身份证签发机关")
    private String issuedBy;

    @ApiModelProperty("身份证有效期开始")
    private Date validDateStart;

    @ApiModelProperty("身份证有效期结束")
    private Date validDateEnd;

    @ApiModelProperty("身份证正面URL地址")
    private String frontSide;

    @ApiModelProperty("身份证正面图片code")
    private String frontSideCode;

    @ApiModelProperty("身份证反面URL地址")
    private String backSide;

    @ApiModelProperty("身份证反面图片code")
    private String backSideCode;

    @ApiModelProperty("活体图片URL地址")
    private String facePicture;

    @ApiModelProperty("活体图片code")
    private String facePictureCode;

    @ApiModelProperty("网纹照URL地址")
    private String webTexturedPhoto;

    @ApiModelProperty("网纹照code")
    private String webTexturedPhotoCode;

    @ApiModelProperty("专案代码")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday == null ? null : DATE_FORMAT.format(birthday);
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public UserIdentityStatus getFaceStatus() {
        return faceStatus;
    }

    public void setFaceStatus(UserIdentityStatus faceStatus) {
        this.faceStatus = faceStatus;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFacePath() {
        return facePath;
    }

    public void setFacePath(String facePath) {
        this.facePath = facePath;
    }

    public String getFaceDelta() {
        return faceDelta;
    }

    public void setFaceDelta(String faceDelta) {
        this.faceDelta = faceDelta;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Date getValidDateStart() {
        return validDateStart;
    }

    public void setValidDateStart(Date validDateStart) {
        this.validDateStart = validDateStart;
    }

    public Date getValidDateEnd() {
        return validDateEnd;
    }

    public void setValidDateEnd(Date validDateEnd) {
        this.validDateEnd = validDateEnd;
    }

    public String getFrontSide() {
        return frontSide;
    }

    public void setFrontSide(String frontSide) {
        this.frontSide = frontSide;
    }

    public String getFrontSideCode() {
        return frontSideCode;
    }

    public void setFrontSideCode(String frontSideCode) {
        this.frontSideCode = frontSideCode;
    }

    public String getBackSide() {
        return backSide;
    }

    public void setBackSide(String backSide) {
        this.backSide = backSide;
    }

    public String getBackSideCode() {
        return backSideCode;
    }

    public void setBackSideCode(String backSideCode) {
        this.backSideCode = backSideCode;
    }

    public String getFacePicture() {
        return facePicture;
    }

    public void setFacePicture(String facePicture) {
        this.facePicture = facePicture;
    }

    public String getFacePictureCode() {
        return facePictureCode;
    }

    public void setFacePictureCode(String facePictureCode) {
        this.facePictureCode = facePictureCode;
    }

    public String getWebTexturedPhoto() {
        return webTexturedPhoto;
    }

    public void setWebTexturedPhoto(String webTexturedPhoto) {
        this.webTexturedPhoto = webTexturedPhoto;
    }

    public String getWebTexturedPhotoCode() {
        return webTexturedPhotoCode;
    }

    public void setWebTexturedPhotoCode(String webTexturedPhotoCode) {
        this.webTexturedPhotoCode = webTexturedPhotoCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
