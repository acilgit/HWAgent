package com.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class CommentListBean implements Serializable {


    /**
     * evaluationList : {"content":[{"creator":"测试1","createTime":1466492348000,"updater":null,"updateTime":1466492348000,"activate":true,"id":"3ad1e6b0-f6ec-4334-9247-a1cfd5f05efd","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"图腾","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"11分钟前"},{"creator":"测试1","createTime":1466421915000,"updater":null,"updateTime":1466421915000,"activate":true,"id":"79709a21-9178-4c26-bf6e-7c6c496bfb92","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"骷","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"20小时前"},{"creator":"陈绍鹏","createTime":1466064191000,"updater":null,"updateTime":1466064191000,"activate":true,"id":"5298dcb9-63a6-4e72-a5b1-21794cf685cf","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"具体","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"2016年06月16日"},{"creator":"陈绍鹏","createTime":1465965974000,"updater":null,"updateTime":1465965974000,"activate":true,"id":"9e288033-c871-4a44-b1f5-fa4ccc8da4b0","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"尽力了","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"2016年06月15日"},{"creator":"陈绍鹏","createTime":1465965963000,"updater":null,"updateTime":1465965963000,"activate":true,"id":"e80359c2-d6d4-456b-9d17-01e46153943f","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"太冷了","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"2016年06月15日"},{"creator":"陈绍鹏","createTime":1465965958000,"updater":null,"updateTime":1465965958000,"activate":true,"id":"0f115f96-251e-4469-a620-430899b7bc2d","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"jll","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"2016年06月15日"}],"number":0,"size":10,"totalElements":6,"sort":null,"numberOfElements":6,"lastPage":true,"firstPage":true,"totalPages":1}
     */

    private ContentBean content;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * content : [{"creator":"测试1","createTime":1466492348000,"updater":null,"updateTime":1466492348000,"activate":true,"id":"3ad1e6b0-f6ec-4334-9247-a1cfd5f05efd","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"图腾","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"11分钟前"},{"creator":"测试1","createTime":1466421915000,"updater":null,"updateTime":1466421915000,"activate":true,"id":"79709a21-9178-4c26-bf6e-7c6c496bfb92","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"骷","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"20小时前"},{"creator":"陈绍鹏","createTime":1466064191000,"updater":null,"updateTime":1466064191000,"activate":true,"id":"5298dcb9-63a6-4e72-a5b1-21794cf685cf","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"具体","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"2016年06月16日"},{"creator":"陈绍鹏","createTime":1465965974000,"updater":null,"updateTime":1465965974000,"activate":true,"id":"9e288033-c871-4a44-b1f5-fa4ccc8da4b0","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"尽力了","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"2016年06月15日"},{"creator":"陈绍鹏","createTime":1465965963000,"updater":null,"updateTime":1465965963000,"activate":true,"id":"e80359c2-d6d4-456b-9d17-01e46153943f","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"太冷了","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"2016年06月15日"},{"creator":"陈绍鹏","createTime":1465965958000,"updater":null,"updateTime":1465965958000,"activate":true,"id":"0f115f96-251e-4469-a620-430899b7bc2d","userId":"6371ca31-da7a-458a-88a3-98600bff2753","content":"jll","villageId":"2e6f187a-8f9b-456b-9106-cd267ffa5d35","publishTime":"2016年06月15日"}]
         * number : 0
         * size : 10
         * totalElements : 6
         * sort : null
         * numberOfElements : 6
         * lastPage : true
         * firstPage : true
         * totalPages : 1
         */

        private EvaluationListBean evaluationList;

        public EvaluationListBean getEvaluationList() {
            return evaluationList;
        }

        public void setEvaluationList(EvaluationListBean evaluationList) {
            this.evaluationList = evaluationList;
        }

        public static class EvaluationListBean {
            private int number;
            private int size;
            private int totalElements;
            private Object sort;
            private int numberOfElements;
            private boolean lastPage;
            private boolean firstPage;
            private int totalPages;
            /**
             * creator : 测试1
             * createTime : 1466492348000
             * updater : null
             * updateTime : 1466492348000
             * activate : true
             * id : 3ad1e6b0-f6ec-4334-9247-a1cfd5f05efd
             * userId : 6371ca31-da7a-458a-88a3-98600bff2753
             * content : 图腾
             * villageId : 2e6f187a-8f9b-456b-9106-cd267ffa5d35
             * publishTime : 11分钟前
             */

            private List<Bean> content;

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

            public int getTotalElements() {
                return totalElements;
            }

            public void setTotalElements(int totalElements) {
                this.totalElements = totalElements;
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

            public int getTotalPages() {
                return totalPages;
            }

            public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
            }

            public List<Bean> getContent() {
                return content;
            }

            public void setContent(List<Bean> content) {
                this.content = content;
            }

            public static class Bean {
                private String creator;
                private long createTime;
                private Object updater;
                private long updateTime;
                private boolean activate;
                private String id;
                private String userId;
                private String content;
                private String villageId;
                private String publishTime;

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

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
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

                public String getPublishTime() {
                    return publishTime;
                }

                public void setPublishTime(String publishTime) {
                    this.publishTime = publishTime;
                }
            }
        }
    }
}
