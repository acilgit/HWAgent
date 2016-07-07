package com.housingonitoringagent.homeworryagent.beans;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class UpdaterBean {

    /**
     * creator : admin
     * createTime : 1460448627000
     * updater : null
     * updateTime : 1467083900000
     * activate : true
     * id : 00a22c2b-0d9f-490f-88f5-454da5abd208
     * platformType : 1
     * versionType : 1
     * versionCode : test04
     * interiorCode : 11111
     * isCompel : false
     * dlUrl : sdf
     * upgradePoromet : 没啥
     */

    private ContentBean content;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private String creator;
        private long createTime;
        private Object updater;
        private long updateTime;
        private boolean activate;
        private String id;
        private int platformType;
        private int versionType;
        private String versionCode;
        private int interiorCode;
        private boolean isCompel;
        private String dlUrl;
        private String upgradePoromet;

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

        public Object getUpdater() {
            return updater;
        }

        public void setUpdater(Object updater) {
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

        public int getPlatformType() {
            return platformType;
        }

        public void setPlatformType(int platformType) {
            this.platformType = platformType;
        }

        public int getVersionType() {
            return versionType;
        }

        public void setVersionType(int versionType) {
            this.versionType = versionType;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public int getInteriorCode() {
            return interiorCode;
        }

        public void setInteriorCode(int interiorCode) {
            this.interiorCode = interiorCode;
        }

        public boolean isIsCompel() {
            return isCompel;
        }

        public void setIsCompel(boolean isCompel) {
            this.isCompel = isCompel;
        }

        public String getDlUrl() {
            return dlUrl;
        }

        public void setDlUrl(String dlUrl) {
            this.dlUrl = dlUrl;
        }

        public String getUpgradePoromet() {
            return upgradePoromet;
        }

        public void setUpgradePoromet(String upgradePoromet) {
            this.upgradePoromet = upgradePoromet;
        }
    }
}
