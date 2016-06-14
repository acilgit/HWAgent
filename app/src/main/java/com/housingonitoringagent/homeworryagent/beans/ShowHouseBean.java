package com.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class ShowHouseBean implements Serializable {

    /**
     * resultCode : 1
     * message : 获取列表成功
     * content : {"content":[{"creator":null,"createTime":1465176760000,"updater":null,"updateTime":1465176760000,"activate":true,"id":"a5a46db5-b182-4cf4-bbac-21b96a47f2f6","houseId":"86dae364-79c4-45bc-b467-55095fb69f1c","userId":"6371ca31-da7a-458a-88a3-98600bff2753","applyUserName":"章瑱彬","applyUserSex":2,"applyUserMobilephone":"13790545364","storeId":"16a3aec5-63ef-4c65-a99d-ef36802e6e55","storeName":"合富莞城店","agentId":"9b870b8c-1eb2-4599-a1a8-b1faa34ea80d","agentName":null,"avatar":"http://192.168.1.222:9000/upload/image/20160531/1464695499885084970.jpg","startTime":1465176760000,"endTime":1465176760000,"applyVisitNumber":1,"realVisitNumber":null,"permitStatus":0,"permitType":2,"village_name":"霖峰\u2022壹山境","house_number":"076914800441","createor":"陈绍鹏","name":"经纪人","isCommented":true}],"number":0,"size":5,"sort":null,"numberOfElements":2,"totalElements":3,"totalPages":1,"firstPage":true,"lastPage":true}
     */

    private int resultCode;
    private String message;
    /**
     * content : [{"creator":null,"createTime":1465176760000,"updater":null,"updateTime":1465176760000,"activate":true,"id":"a5a46db5-b182-4cf4-bbac-21b96a47f2f6","houseId":"86dae364-79c4-45bc-b467-55095fb69f1c","userId":"6371ca31-da7a-458a-88a3-98600bff2753","applyUserName":"章瑱彬","applyUserSex":2,"applyUserMobilephone":"13790545364","storeId":"16a3aec5-63ef-4c65-a99d-ef36802e6e55","storeName":"合富莞城店","agentId":"9b870b8c-1eb2-4599-a1a8-b1faa34ea80d","agentName":null,"avatar":"http://192.168.1.222:9000/upload/image/20160531/1464695499885084970.jpg","startTime":1465176760000,"endTime":1465176760000,"applyVisitNumber":1,"realVisitNumber":null,"permitStatus":0,"permitType":2,"village_name":"霖峰\u2022壹山境","house_number":"076914800441","createor":"陈绍鹏","name":"经纪人","isCommented":true}]
     * number : 0
     * size : 5
     * sort : null
     * numberOfElements : 2
     * totalElements : 3
     * totalPages : 1
     * firstPage : true
     * lastPage : true
     */
    private ContentBean contentBean;

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

    public ContentBean getContentBean() {
        return contentBean;
    }

    public void setContentBean(ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    public static class ContentBean {
        private int number;
        private int size;
        private Object sort;
        private int numberOfElements;
        private int totalElements;
        private int totalPages;
        private boolean firstPage;
        private boolean lastPage;
        /**
         * creator : null
         * createTime : 1465176760000
         * updater : null
         * updateTime : 1465176760000
         * activate : true
         * id : a5a46db5-b182-4cf4-bbac-21b96a47f2f6
         * houseId : 86dae364-79c4-45bc-b467-55095fb69f1c
         * userId : 6371ca31-da7a-458a-88a3-98600bff2753
         * applyUserName : 章瑱彬
         * applyUserSex : 2
         * applyUserMobilephone : 13790545364
         * storeId : 16a3aec5-63ef-4c65-a99d-ef36802e6e55
         * storeName : 合富莞城店
         * agentId : 9b870b8c-1eb2-4599-a1a8-b1faa34ea80d
         * agentName : null
         * avatar : http://192.168.1.222:9000/upload/image/20160531/1464695499885084970.jpg
         * startTime : 1465176760000
         * endTime : 1465176760000
         * applyVisitNumber : 1
         * realVisitNumber : null
         * permitStatus : 0
         * permitType : 2
         * village_name : 霖峰•壹山境
         * house_number : 076914800441
         * createor : 陈绍鹏
         * name : 经纪人
         * isCommented : true
         */

        private List<Content> content;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public static class Content {
            private Object creator;
            private long createTime;
            private Object updater;
            private long updateTime;
            private boolean activate;
            private String id;
            private String houseId;
            private String userId;
            private String applyUserName;
            private int applyUserSex;
            private String applyUserMobilephone;
            private String storeId;
            private String storeName;
            private String agentId;
            private Object agentName;
            private String avatar;
            private long startTime;
            private long endTime;
            private int applyVisitNumber;
            private Object realVisitNumber;
            private int permitStatus;
            private int permitType;
            private String village_name;
            private String house_number;
            private String createor;
            private String name;
            private boolean isCommented;


            public Long getCreateTimeCompare() {
                return createTime;
            }
            public String getPermitStateString() {
                switch (permitStatus) {
                    case 0:
                        return "未确认";
                    case 1:
                        return "已看房";
                    case 2:
                        return "未看房";
                    default:
                        return "";
                }
            }
            public String getPermitTypeString() {
                switch (permitType) {
                    case 0:
                        return "租房";
                    case 1:
                        return "二手房";
                    default:
                        return "";
                }
            }

            public Object getCreator() {
                return creator;
            }

            public void setCreator(Object creator) {
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

            public String getHouseId() {
                return houseId;
            }

            public void setHouseId(String houseId) {
                this.houseId = houseId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getApplyUserName() {
                return applyUserName;
            }

            public void setApplyUserName(String applyUserName) {
                this.applyUserName = applyUserName;
            }

            public int getApplyUserSex() {
                return applyUserSex;
            }

            public void setApplyUserSex(int applyUserSex) {
                this.applyUserSex = applyUserSex;
            }

            public String getApplyUserMobilephone() {
                return applyUserMobilephone;
            }

            public void setApplyUserMobilephone(String applyUserMobilephone) {
                this.applyUserMobilephone = applyUserMobilephone;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getAgentId() {
                return agentId;
            }

            public void setAgentId(String agentId) {
                this.agentId = agentId;
            }

            public Object getAgentName() {
                return agentName;
            }

            public void setAgentName(Object agentName) {
                this.agentName = agentName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public int getApplyVisitNumber() {
                return applyVisitNumber;
            }

            public void setApplyVisitNumber(int applyVisitNumber) {
                this.applyVisitNumber = applyVisitNumber;
            }

            public Object getRealVisitNumber() {
                return realVisitNumber;
            }

            public void setRealVisitNumber(Object realVisitNumber) {
                this.realVisitNumber = realVisitNumber;
            }

            public int getPermitStatus() {
                return permitStatus;
            }

            public void setPermitStatus(int permitStatus) {
                this.permitStatus = permitStatus;
            }

            public int getPermitType() {
                return permitType;
            }

            public void setPermitType(int permitType) {
                this.permitType = permitType;
            }

            public String getVillage_name() {
                return village_name;
            }

            public void setVillage_name(String village_name) {
                this.village_name = village_name;
            }

            public String getHouse_number() {
                return house_number;
            }

            public void setHouse_number(String house_number) {
                this.house_number = house_number;
            }

            public String getCreateor() {
                return createor;
            }

            public void setCreateor(String createor) {
                this.createor = createor;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isIsCommented() {
                return isCommented;
            }

            public void setIsCommented(boolean isCommented) {
                this.isCommented = isCommented;
            }
        }
    }
}
