package com.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/27 0027.
 */
public class HousingAssessmentBean implements Serializable {

    /**
     * content : [{"userAvatar":null,"userName":null,"userTelephone":null,"intermediaryCompanyName":"新华社快讯","id":"219adcf5-c877-4c89-a229-6dc9b6fbe6d0","userIntermediaryAgentId":"d028dc02-596f-46f7-87c5-4754155a591e","ownerHouseId":"4f166515-3918-4e3c-8d12-200141acc082","intermediaryStoreId":"12","intermediaryCompanyId":"00df8fa7-5d7a-45fe-b22c-411376d9fbe5","content":"试发射\u201c舞水端\u201d中程弹道导弹，据韩国军方推断，此次发射失败。韩国政府多位消息人士14日曾透露，朝鲜很可能为迎接金日成诞辰（4月15日）而进行该导弹在服役后的首次发射。","approvalUp":0,"creator":"王二小","createTime":1460532103000,"updater":null,"updateTime":null,"activate":true,"isApproval":0}]
     * number : 0
     * size : 10
     * sort : null
     * totalPages : 1
     * totalElements : 1
     * firstPage : true
     * lastPage : true
     * numberOfElements : 1
     */

    private HouseEvaluateListEntity houseEvaluateList;
    /**
     * houseEvaluateList : {"content":[{"userAvatar":null,"userName":null,"userTelephone":null,"intermediaryCompanyName":"新华社快讯","id":"219adcf5-c877-4c89-a229-6dc9b6fbe6d0","userIntermediaryAgentId":"d028dc02-596f-46f7-87c5-4754155a591e","ownerHouseId":"4f166515-3918-4e3c-8d12-200141acc082","intermediaryStoreId":"12","intermediaryCompanyId":"00df8fa7-5d7a-45fe-b22c-411376d9fbe5","content":"试发射\u201c舞水端\u201d中程弹道导弹，据韩国军方推断，此次发射失败。韩国政府多位消息人士14日曾透露，朝鲜很可能为迎接金日成诞辰（4月15日）而进行该导弹在服役后的首次发射。","approvalUp":0,"creator":"王二小","createTime":1460532103000,"updater":null,"updateTime":null,"activate":true,"isApproval":0}],"number":0,"size":10,"sort":null,"totalPages":1,"totalElements":1,"firstPage":true,"lastPage":true,"numberOfElements":1}
     * resultCode : 1
     * message : 成功
     */

    public HouseEvaluateListEntity getHouseEvaluateList() {
        return houseEvaluateList;
    }

    public void setHouseEvaluateList(HouseEvaluateListEntity houseEvaluateList) {
        this.houseEvaluateList = houseEvaluateList;
    }

    public static class HouseEvaluateListEntity {
        private int number;
        private int size;
        private Object sort;
        private int totalPages;
        private int totalElements;
        private boolean firstPage;
        private boolean lastPage;
        private int numberOfElements;
        /**
         * userAvatar : null
         * userName : null
         * userTelephone : null
         * intermediaryCompanyName : 新华社快讯
         * id : 219adcf5-c877-4c89-a229-6dc9b6fbe6d0
         * userIntermediaryAgentId : d028dc02-596f-46f7-87c5-4754155a591e
         * ownerHouseId : 4f166515-3918-4e3c-8d12-200141acc082
         * intermediaryStoreId : 12
         * intermediaryCompanyId : 00df8fa7-5d7a-45fe-b22c-411376d9fbe5
         * content : 试发射“舞水端”中程弹道导弹，据韩国军方推断，此次发射失败。韩国政府多位消息人士14日曾透露，朝鲜很可能为迎接金日成诞辰（4月15日）而进行该导弹在服役后的首次发射。
         * approvalUp : 0
         * creator : 王二小
         * createTime : 1460532103000
         * updater : null
         * updateTime : null
         * activate : true
         * isApproval : 0
         */

        private List<ContentEntity> content;

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

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
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

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public List<ContentEntity> getContent() {
            return content;
        }

        public void setContent(List<ContentEntity> content) {
            this.content = content;
        }

        public static class ContentEntity {
            private String userAvatar;
            private String userName;
            private String userTelephone;
            private String intermediaryCompanyName;
            private String id;
            private String userIntermediaryAgentId;
            private String ownerHouseId;
            private String intermediaryStoreId;
            private String intermediaryCompanyId;
            private String content;
            private int approvalUp;
            private String creator;
            private long createTime;
            private Object updater;
            private Object updateTime;
            private boolean activate;
            private int isApproval;

            public String getUserAvatar() {
                return userAvatar;
            }

            public void setUserAvatar(String userAvatar) {
                this.userAvatar = userAvatar;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserTelephone() {
                return userTelephone;
            }

            public void setUserTelephone(String userTelephone) {
                this.userTelephone = userTelephone;
            }

            public String getIntermediaryCompanyName() {
                return intermediaryCompanyName;
            }

            public void setIntermediaryCompanyName(String intermediaryCompanyName) {
                this.intermediaryCompanyName = intermediaryCompanyName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserIntermediaryAgentId() {
                return userIntermediaryAgentId;
            }

            public void setUserIntermediaryAgentId(String userIntermediaryAgentId) {
                this.userIntermediaryAgentId = userIntermediaryAgentId;
            }

            public String getOwnerHouseId() {
                return ownerHouseId;
            }

            public void setOwnerHouseId(String ownerHouseId) {
                this.ownerHouseId = ownerHouseId;
            }

            public String getIntermediaryStoreId() {
                return intermediaryStoreId;
            }

            public void setIntermediaryStoreId(String intermediaryStoreId) {
                this.intermediaryStoreId = intermediaryStoreId;
            }

            public String getIntermediaryCompanyId() {
                return intermediaryCompanyId;
            }

            public void setIntermediaryCompanyId(String intermediaryCompanyId) {
                this.intermediaryCompanyId = intermediaryCompanyId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getApprovalUp() {
                return approvalUp;
            }

            public void setApprovalUp(int approvalUp) {
                this.approvalUp = approvalUp;
            }

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

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public boolean isActivate() {
                return activate;
            }

            public void setActivate(boolean activate) {
                this.activate = activate;
            }

            public int getIsApproval() {
                return isApproval;
            }

            public void setIsApproval(int isApproval) {
                this.isApproval = isApproval;
            }
        }
    }
}
