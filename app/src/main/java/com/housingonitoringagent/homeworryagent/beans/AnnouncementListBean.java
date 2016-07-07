package com.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class AnnouncementListBean implements Serializable {
    /**
     * resultCode : 1
     * message : 成功
     * content : {"announcementList":{"content":[{"creator":null,"createTime":null,"updater":null,"updateTime":null,"activate":true,"articleId":"8485d768-461a-4499-bc11-fa54018855e8","imgUrl":null,"articleSorted":1,"contentSorted":1,"label":null,"title":"这是一个测试通知公告","knowledgesId":"","publisherId":"c2a771c1-05f4-4de4-94e5-4be4389d0efd","publisherName":"万可可","publisherTime":1463448735000,"updateId":null,"updateName":null,"content":null,"villageId":"621c7135-0f5a-4833-bc7c-dab7384106fa"}],"number":0,"size":10,"sort":null,"totalPages":1,"lastPage":true,"firstPage":true,"numberOfElements":1,"totalElements":1}}
     */

    private int resultCode;
    private String message;
    /**
     * announcementList : {"content":[{"creator":null,"createTime":null,"updater":null,"updateTime":null,"activate":true,"articleId":"8485d768-461a-4499-bc11-fa54018855e8","imgUrl":null,"articleSorted":1,"contentSorted":1,"label":null,"title":"这是一个测试通知公告","knowledgesId":"","publisherId":"c2a771c1-05f4-4de4-94e5-4be4389d0efd","publisherName":"万可可","publisherTime":1463448735000,"updateId":null,"updateName":null,"content":null,"villageId":"621c7135-0f5a-4833-bc7c-dab7384106fa"}],"number":0,"size":10,"sort":null,"totalPages":1,"lastPage":true,"firstPage":true,"numberOfElements":1,"totalElements":1}
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
         * content : [{"creator":null,"createTime":null,"updater":null,"updateTime":null,"activate":true,"articleId":"8485d768-461a-4499-bc11-fa54018855e8","imgUrl":null,"articleSorted":1,"contentSorted":1,"label":null,"title":"这是一个测试通知公告","knowledgesId":"","publisherId":"c2a771c1-05f4-4de4-94e5-4be4389d0efd","publisherName":"万可可","publisherTime":1463448735000,"updateId":null,"updateName":null,"content":null,"villageId":"621c7135-0f5a-4833-bc7c-dab7384106fa"}]
         * number : 0
         * size : 10
         * sort : null
         * totalPages : 1
         * lastPage : true
         * firstPage : true
         * numberOfElements : 1
         * totalElements : 1
         */

        private AnnouncementBean announcementList;

        public AnnouncementBean getAnnouncementList() {
            return announcementList;
        }

        public void setAnnouncementList(AnnouncementBean announcementList) {
            this.announcementList = announcementList;
        }

        public static class AnnouncementBean {
            private int number;
            private int size;
            private Object sort;
            private int totalPages;
            private boolean lastPage;
            private boolean firstPage;
            private int numberOfElements;
            private int totalElements;
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
             * content : null
             * villageId : 621c7135-0f5a-4833-bc7c-dab7384106fa
             */

            private List<ContentInfoBean> content;

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

            public boolean isLastPage() {
                return lastPage;
            }

            public void setLastPage(boolean lastPage) {
                this.lastPage = lastPage;
            }

            public boolean isFirstPage() {
                return firstPage;
            }

            public void setFirstPage(boolean firstPage) {
                this.firstPage = firstPage;
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

            public List<ContentInfoBean> getContent() {
                return content;
            }

            public void setContent(List<ContentInfoBean> content) {
                this.content = content;
            }

            public static class ContentInfoBean {
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
                private Object content;
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

                public Object getContent() {
                    return content;
                }

                public void setContent(Object content) {
                    this.content = content;
                }

                public String getVillageId() {
                    return villageId;
                }

                public void setVillageId(String villageId) {
                    this.villageId = villageId;
                }
            }
        }
    }/*

    *//**
     * content : [{"creator":null,"createTime":null,"updater":null,"updateTime":1460170103000,"activate":true,"articleId":"0129b67a-2e81-41ee-a0c4-a9e1ab3b711a","imgUrl":"","articleSorted":1,"contentSorted":1,"label":"234","title":"w345","knowledgesId":null,"publisherId":"3c74e03f-3b17-47ab-bdc5-bd94da659a82","publisherName":"admin","publisherTime":1459849452000,"updateId":"3c74e03f-3b17-47ab-bdc5-bd94da659a82","updateName":"admin","content":null,"villageId":"0ef61a38-f883-40f3-86a6-fc9f9e54ef6f"}]
     * number : 0
     * size : 10
     * totalPages : 1
     * totalElements : 1
     * firstPage : true
     * lastPage : true
     * sort : null
     * numberOfElements : 1
     *//*

    private AnnouncementListEntity announcementList;
    *//**
     * announcementList : {"content":[{"creator":null,"createTime":null,"updater":null,"updateTime":1460170103000,"activate":true,"articleId":"0129b67a-2e81-41ee-a0c4-a9e1ab3b711a","imgUrl":"","articleSorted":1,"contentSorted":1,"label":"234","title":"w345","knowledgesId":null,"publisherId":"3c74e03f-3b17-47ab-bdc5-bd94da659a82","publisherName":"admin","publisherTime":1459849452000,"updateId":"3c74e03f-3b17-47ab-bdc5-bd94da659a82","updateName":"admin","content":null,"villageId":"0ef61a38-f883-40f3-86a6-fc9f9e54ef6f"}],"number":0,"size":10,"totalPages":1,"totalElements":1,"firstPage":true,"lastPage":true,"sort":null,"numberOfElements":1}
     * resultCode : 1
     * message : 成功
     *//*

    public AnnouncementListEntity getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(AnnouncementListEntity announcementList) {
        this.announcementList = announcementList;
    }

    public static class AnnouncementListEntity {
        private int number;
        private int size;
        private int totalPages;
        private int totalElements;
        private boolean firstPage;
        private boolean lastPage;
        private Object sort;
        private int numberOfElements;
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
         * content : null
         * villageId : 0ef61a38-f883-40f3-86a6-fc9f9e54ef6f
         *//*

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

        public List<ContentEntity> getContent() {
            return content;
        }

        public void setContent(List<ContentEntity> content) {
            this.content = content;
        }

        public static class ContentEntity {
            private Object creator;
            private long createTime;
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
            private Object content;
            private String villageId;

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

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public String getVillageId() {
                return villageId;
            }

            public void setVillageId(String villageId) {
                this.villageId = villageId;
            }
        }
    }*/
}
