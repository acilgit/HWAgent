package com.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class UserBean implements Serializable {

    /**
     * resultCode : 1
     * message : 登录成功
     * content : {"creator":"csp","createTime":1463706977000,"updater":"csp","updateTime":1464081035000,"activate":true,"id":"9b870b8c-1eb2-4599-a1a8-b1faa34ea80d","intermediaryStoreId":"16a3aec5-63ef-4c65-a99d-ef36802e6e55","intermediaryStoreName":"合富莞城店","intermediaryCompanyId":"fe4afccc-e256-44cb-b6a0-0dded3a83d18","intermediaryCompanyName":"东莞市合富置业有限公司","yearlyInspection":"2016","workingLife":26,"certificatePicture":"http://localhost:9000/upload/image/20160520/1463706969683027234.png","certificateInfo":"无","duty":2,"academicTitle":2,"complaintCount":0,"complaintAgreeCount":0,"complaintCompleteCount":0,"houseEvaluatePraiseAmount":0,"certStatus":1,"sessionId":"108fde4a-f923-4ba8-98f7-12fc9e51de84","name":"经纪人","avatar":"http://localhost:9000/upload/image/20160520/1463706933924015197.png","realName":false,"idCard":"340711199101010011"}
     */

    private int resultCode;
    private String message;
    /**
     * creator : csp
     * createTime : 1463706977000
     * updater : csp
     * updateTime : 1464081035000
     * activate : true
     * id : 9b870b8c-1eb2-4599-a1a8-b1faa34ea80d
     * intermediaryStoreId : 16a3aec5-63ef-4c65-a99d-ef36802e6e55
     * intermediaryStoreName : 合富莞城店
     * intermediaryCompanyId : fe4afccc-e256-44cb-b6a0-0dded3a83d18
     * intermediaryCompanyName : 东莞市合富置业有限公司
     * yearlyInspection : 2016
     * workingLife : 26
     * certificatePicture : http://localhost:9000/upload/image/20160520/1463706969683027234.png
     * certificateInfo : 无
     * duty : 2
     * academicTitle : 2
     * complaintCount : 0
     * complaintAgreeCount : 0
     * complaintCompleteCount : 0
     * houseEvaluatePraiseAmount : 0
     * certStatus : 1
     * sessionId : 108fde4a-f923-4ba8-98f7-12fc9e51de84
     * name : 经纪人
     * avatar : http://localhost:9000/upload/image/20160520/1463706933924015197.png
     * realName : false
     * idCard : 340711199101010011
     */

    private ContentBean content;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private String creator;
        private long createTime;
        private String updater;
        private long updateTime;
        private boolean activate;
        private String id;
        private String intermediaryStoreId;
        private String intermediaryStoreName;
        private String intermediaryCompanyId;
        private String intermediaryCompanyName;
        private String yearlyInspection;
        private int workingLife;
        private String certificatePicture;
        private String certificateInfo;
        private int duty;
        private int academicTitle;
        private int complaintCount;
        private int complaintAgreeCount;
        private int complaintCompleteCount;
        private int houseEvaluatePraiseAmount;
        private int certStatus;
        private int sex;
        private String sessionId;
        private String huanName;
        private String name;
        private String avatar;
        private String phone;
        private boolean realName;
        private String idCard;


        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getUpdater() {
            return updater;
        }

        public void setUpdater(String updater) {
            this.updater = updater;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isActivate() {
            return activate;
        }

        public void setActivate(boolean activate) {
            this.activate = activate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHuanName() {
            return huanName;
        }

        public void setHuanName(String huanName) {
            this.huanName = huanName;
        }

        public String getIntermediaryStoreId() {
            return intermediaryStoreId;
        }

        public void setIntermediaryStoreId(String intermediaryStoreId) {
            this.intermediaryStoreId = intermediaryStoreId;
        }

        public String getIntermediaryStoreName() {
            return intermediaryStoreName;
        }

        public void setIntermediaryStoreName(String intermediaryStoreName) {
            this.intermediaryStoreName = intermediaryStoreName;
        }

        public String getIntermediaryCompanyId() {
            return intermediaryCompanyId;
        }

        public void setIntermediaryCompanyId(String intermediaryCompanyId) {
            this.intermediaryCompanyId = intermediaryCompanyId;
        }

        public String getIntermediaryCompanyName() {
            return intermediaryCompanyName;
        }

        public void setIntermediaryCompanyName(String intermediaryCompanyName) {
            this.intermediaryCompanyName = intermediaryCompanyName;
        }

        public String getYearlyInspection() {
            return yearlyInspection;
        }

        public void setYearlyInspection(String yearlyInspection) {
            this.yearlyInspection = yearlyInspection;
        }

        public int getWorkingLife() {
            return workingLife;
        }

        public void setWorkingLife(int workingLife) {
            this.workingLife = workingLife;
        }

        public String getCertificatePicture() {
            return certificatePicture;
        }

        public void setCertificatePicture(String certificatePicture) {
            this.certificatePicture = certificatePicture;
        }

        public String getCertificateInfo() {
            return certificateInfo;
        }

        public void setCertificateInfo(String certificateInfo) {
            this.certificateInfo = certificateInfo;
        }

        public int getDuty() {
            return duty;
        }

        public void setDuty(int duty) {
            this.duty = duty;
        }

        public int getAcademicTitle() {
            return academicTitle;
        }

        public void setAcademicTitle(int academicTitle) {
            this.academicTitle = academicTitle;
        }

        public int getComplaintCount() {
            return complaintCount;
        }

        public void setComplaintCount(int complaintCount) {
            this.complaintCount = complaintCount;
        }

        public int getComplaintAgreeCount() {
            return complaintAgreeCount;
        }

        public void setComplaintAgreeCount(int complaintAgreeCount) {
            this.complaintAgreeCount = complaintAgreeCount;
        }

        public int getComplaintCompleteCount() {
            return complaintCompleteCount;
        }

        public void setComplaintCompleteCount(int complaintCompleteCount) {
            this.complaintCompleteCount = complaintCompleteCount;
        }

        public int getHouseEvaluatePraiseAmount() {
            return houseEvaluatePraiseAmount;
        }

        public void setHouseEvaluatePraiseAmount(int houseEvaluatePraiseAmount) {
            this.houseEvaluatePraiseAmount = houseEvaluatePraiseAmount;
        }

        public int getCertStatus() {
            return certStatus;
        }

        public void setCertStatus(int certStatus) {
            this.certStatus = certStatus;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobilephone() {
            return phone;
        }

        public void setMobilephone(String phone) {
            this.phone = phone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public boolean isRealName() {
            return realName;
        }

        public void setRealName(boolean realName) {
            this.realName = realName;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getSexName() {
            return sex == 1 ? "男" : "女";
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }


    }
}
