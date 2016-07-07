package com.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class AnnouncementInfoBean implements Serializable {
    /**
     * resultCode : 1
     * message : 成功
     * content : {"announcement":{"creator":null,"createTime":null,"updater":null,"updateTime":null,"activate":true,"articleId":"8485d768-461a-4499-bc11-fa54018855e8","imgUrl":null,"articleSorted":1,"contentSorted":1,"label":null,"title":"这是一个测试通知公告","knowledgesId":"","publisherId":"c2a771c1-05f4-4de4-94e5-4be4389d0efd","publisherName":"万可可","publisherTime":1463448735000,"updateId":null,"updateName":null,"content":"<p>这是一个测试通知公告这是一个测试通知公告这是一个测试通知公告<\/p>","villageId":"621c7135-0f5a-4833-bc7c-dab7384106fa"}}
     */

    private int resultCode;
    private String message;
    /**
     * announcement : {"creator":null,"createTime":null,"updater":null,"updateTime":null,"activate":true,"articleId":"8485d768-461a-4499-bc11-fa54018855e8","imgUrl":null,"articleSorted":1,"contentSorted":1,"label":null,"title":"这是一个测试通知公告","knowledgesId":"","publisherId":"c2a771c1-05f4-4de4-94e5-4be4389d0efd","publisherName":"万可可","publisherTime":1463448735000,"updateId":null,"updateName":null,"content":"<p>这是一个测试通知公告这是一个测试通知公告这是一个测试通知公告<\/p>","villageId":"621c7135-0f5a-4833-bc7c-dab7384106fa"}
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
        /**
         * creator : null
         * createTime : null
         * updater : null
         * updateTime : null
         * activate : true
         * articleId : 8485d768-461a-4499-bc11-fa54018855e8
         * imgUrl : null
         * articleSorted : 1
         * contentSorted : 1
         * label : null
         * title : 这是一个测试通知公告
         * knowledgesId :
         * publisherId : c2a771c1-05f4-4de4-94e5-4be4389d0efd
         * publisherName : 万可可
         * publisherTime : 1463448735000
         * updateId : null
         * updateName : null
         * content : <p>这是一个测试通知公告这是一个测试通知公告这是一个测试通知公告</p>
         * villageId : 621c7135-0f5a-4833-bc7c-dab7384106fa
         */

        private AnnouncementBean announcement;

        public AnnouncementBean getAnnouncement() {
            return announcement;
        }

        public void setAnnouncement(AnnouncementBean announcement) {
            this.announcement = announcement;
        }

        public static class AnnouncementBean {
            private Object creator;
            private Object createTime;
            private Object updater;
            private Object updateTime;
            private boolean activate;
            private String articleId;
            private Object imgUrl;
            private int articleSorted;
            private int contentSorted;
            private Object label;
            private String title;
            private String knowledgesId;
            private String publisherId;
            private String publisherName;
            private long publisherTime;
            private Object updateId;
            private Object updateName;
            private String content;
            private String villageId;

            public Object getCreator() {
                return creator;
            }

            public void setCreator(Object creator) {
                this.creator = creator;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
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

            public String getArticleId() {
                return articleId;
            }

            public void setArticleId(String articleId) {
                this.articleId = articleId;
            }

            public Object getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(Object imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getArticleSorted() {
                return articleSorted;
            }

            public void setArticleSorted(int articleSorted) {
                this.articleSorted = articleSorted;
            }

            public int getContentSorted() {
                return contentSorted;
            }

            public void setContentSorted(int contentSorted) {
                this.contentSorted = contentSorted;
            }

            public Object getLabel() {
                return label;
            }

            public void setLabel(Object label) {
                this.label = label;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getKnowledgesId() {
                return knowledgesId;
            }

            public void setKnowledgesId(String knowledgesId) {
                this.knowledgesId = knowledgesId;
            }

            public String getPublisherId() {
                return publisherId;
            }

            public void setPublisherId(String publisherId) {
                this.publisherId = publisherId;
            }

            public String getPublisherName() {
                return publisherName;
            }

            public void setPublisherName(String publisherName) {
                this.publisherName = publisherName;
            }

            public long getPublisherTime() {
                return publisherTime;
            }

            public void setPublisherTime(long publisherTime) {
                this.publisherTime = publisherTime;
            }

            public Object getUpdateId() {
                return updateId;
            }

            public void setUpdateId(Object updateId) {
                this.updateId = updateId;
            }

            public Object getUpdateName() {
                return updateName;
            }

            public void setUpdateName(Object updateName) {
                this.updateName = updateName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getVillageId() {
                return villageId;
            }

            public void setVillageId(String villageId) {
                this.villageId = villageId;
            }
        }
    }/*

    *//**
     * resultCode : 1
     * message : 成功
     * announcement : {"creator":null,"createTime":null,"updater":null,"updateTime":1460170103000,"activate":true,"articleId":"0129b67a-2e81-41ee-a0c4-a9e1ab3b711a","imgUrl":"","articleSorted":1,"contentSorted":1,"label":"234","title":"w345","knowledgesId":null,"publisherId":"3c74e03f-3b17-47ab-bdc5-bd94da659a82","publisherName":"admin","publisherTime":1459849452000,"updateId":"3c74e03f-3b17-47ab-bdc5-bd94da659a82","updateName":"admin","content":"<p>234<br/><\/p>","villageId":"0ef61a38-f883-40f3-86a6-fc9f9e54ef6f"}
     *//*

    *//**
     * creator : null
     * createTime : null
     * updater : null
     * updateTime : 1460170103000
     * activate : true
     * articleId : 0129b67a-2e81-41ee-a0c4-a9e1ab3b711a
     * imgUrl :
     * articleSorted : 1
     * contentSorted : 1
     * label : 234
     * title : w345
     * knowledgesId : null
     * publisherId : 3c74e03f-3b17-47ab-bdc5-bd94da659a82
     * publisherName : admin
     * publisherTime : 1459849452000
     * updateId : 3c74e03f-3b17-47ab-bdc5-bd94da659a82
     * updateName : admin
     * content : <p>234<br/></p>
     * villageId : 0ef61a38-f883-40f3-86a6-fc9f9e54ef6f
     *//*

    private AnnouncementEntity announcement;

    public AnnouncementEntity getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(AnnouncementEntity announcement) {
        this.announcement = announcement;
    }

    public static class AnnouncementEntity {
        private Object creator;
        private Object createTime;
        private Object updater;
        private long updateTime;
        private boolean activate;
        private String articleId;
        private String imgUrl;
        private int articleSorted;
        private int contentSorted;
        private String label;
        private String title;
        private Object knowledgesId;
        private String publisherId;
        private String publisherName;
        private long publisherTime;
        private String updateId;
        private String updateName;
        private String content;
        private String villageId;

        public Object getCreator() {
            return creator;
        }

        public void setCreator(Object creator) {
            this.creator = creator;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
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

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getArticleSorted() {
            return articleSorted;
        }

        public void setArticleSorted(int articleSorted) {
            this.articleSorted = articleSorted;
        }

        public int getContentSorted() {
            return contentSorted;
        }

        public void setContentSorted(int contentSorted) {
            this.contentSorted = contentSorted;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getKnowledgesId() {
            return knowledgesId;
        }

        public void setKnowledgesId(Object knowledgesId) {
            this.knowledgesId = knowledgesId;
        }

        public String getPublisherId() {
            return publisherId;
        }

        public void setPublisherId(String publisherId) {
            this.publisherId = publisherId;
        }

        public String getPublisherName() {
            return publisherName;
        }

        public void setPublisherName(String publisherName) {
            this.publisherName = publisherName;
        }

        public long getPublisherTime() {
            return publisherTime;
        }

        public void setPublisherTime(long publisherTime) {
            this.publisherTime = publisherTime;
        }

        public String getUpdateId() {
            return updateId;
        }

        public void setUpdateId(String updateId) {
            this.updateId = updateId;
        }

        public String getUpdateName() {
            return updateName;
        }

        public void setUpdateName(String updateName) {
            this.updateName = updateName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getVillageId() {
            return villageId;
        }

        public void setVillageId(String villageId) {
            this.villageId = villageId;
        }
    }*/
}
