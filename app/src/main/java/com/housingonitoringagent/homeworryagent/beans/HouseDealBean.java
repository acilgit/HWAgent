package com.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XY on 2016/6/17.
 */
public class HouseDealBean implements Serializable {



    /**
     * resultCode : 1
     * message :
     * content : {"ps":{"content":[{"creator":null,"createTime":1465720186000,"updater":null,"updateTime":null,"activate":true,"id":"df1987e7-11fe-4a33-9746-310b4f0c1970","orderNo":"HS1465720186335001","aUserId":"443314bc-3c19-4a69-98e0-8826e69f2137","bUserId":"6371ca31-da7a-458a-88a3-98600bff2753","cUserId":"443314bc-3c19-4a69-98e0-8826e69f2137","aUserName":"lodi","bUserName":"陈绍鹏","cUserName":"经纪人","aUserMobilephone":"15920274119","bUserMobilephone":"18918918910","cUserMobilephone":"18918918909","aIdCard":"44190019910612673x","bIdCard":"44190019910612672x","orderMoney":500,"bankNumber":"6214837679805378","bankName":"中国银行","bankOpenanaccount":null,"orderStatus":2,"dateSignature":1466048819000,"refundAffirm":null,"collectionUserId":null}],"number":0,"size":10,"sort":null,"totalPages":1,"firstPage":true,"lastPage":true,"numberOfElements":1,"totalElements":1},"STATUSS":["YIQUXIAO","DAICUNKUAN","JIANGUANZHONG","DAITUIKUAN","DAKUANCHUKUZHOHNG","YIDAKUAN","YIWANCHENG"]}
     */

    private int resultCode;
    private String message;
    /**
     * ps : {"content":[{"creator":null,"createTime":1465720186000,"updater":null,"updateTime":null,"activate":true,"id":"df1987e7-11fe-4a33-9746-310b4f0c1970","orderNo":"HS1465720186335001","aUserId":"443314bc-3c19-4a69-98e0-8826e69f2137","bUserId":"6371ca31-da7a-458a-88a3-98600bff2753","cUserId":"443314bc-3c19-4a69-98e0-8826e69f2137","aUserName":"lodi","bUserName":"陈绍鹏","cUserName":"经纪人","aUserMobilephone":"15920274119","bUserMobilephone":"18918918910","cUserMobilephone":"18918918909","aIdCard":"44190019910612673x","bIdCard":"44190019910612672x","orderMoney":500,"bankNumber":"6214837679805378","bankName":"中国银行","bankOpenanaccount":null,"orderStatus":2,"dateSignature":1466048819000,"refundAffirm":null,"collectionUserId":null}],"number":0,"size":10,"sort":null,"totalPages":1,"firstPage":true,"lastPage":true,"numberOfElements":1,"totalElements":1}
     * STATUSS : ["YIQUXIAO","DAICUNKUAN","JIANGUANZHONG","DAITUIKUAN","DAKUANCHUKUZHOHNG","YIDAKUAN","YIWANCHENG"]
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
         * content : [{"creator":null,"createTime":1465720186000,"updater":null,"updateTime":null,"activate":true,"id":"df1987e7-11fe-4a33-9746-310b4f0c1970","orderNo":"HS1465720186335001","aUserId":"443314bc-3c19-4a69-98e0-8826e69f2137","bUserId":"6371ca31-da7a-458a-88a3-98600bff2753","cUserId":"443314bc-3c19-4a69-98e0-8826e69f2137","aUserName":"lodi","bUserName":"陈绍鹏","cUserName":"经纪人","aUserMobilephone":"15920274119","bUserMobilephone":"18918918910","cUserMobilephone":"18918918909","aIdCard":"44190019910612673x","bIdCard":"44190019910612672x","orderMoney":500,"bankNumber":"6214837679805378","bankName":"中国银行","bankOpenanaccount":null,"orderStatus":2,"dateSignature":1466048819000,"refundAffirm":null,"collectionUserId":null}]
         * number : 0
         * size : 10
         * sort : null
         * totalPages : 1
         * firstPage : true
         * lastPage : true
         * numberOfElements : 1
         * totalElements : 1
         */

        private PsBean ps;
        private List<String> STATUSS;

        public PsBean getPs() {
            return ps;
        }

        public void setPs(PsBean ps) {
            this.ps = ps;
        }

        public List<String> getSTATUSS() {
            return STATUSS;
        }

        public void setSTATUSS(List<String> STATUSS) {
            this.STATUSS = STATUSS;
        }

        public static class PsBean {
            private int number;
            private int size;
            private Object sort;
            private int totalPages;
            private boolean firstPage;
            private boolean lastPage;
            private int numberOfElements;
            private int totalElements;
            /**
             * creator : null
             * createTime : 1465720186000
             * updater : null
             * updateTime : null
             * activate : true
             * id : df1987e7-11fe-4a33-9746-310b4f0c1970
             * orderNo : HS1465720186335001
             * aUserId : 443314bc-3c19-4a69-98e0-8826e69f2137
             * bUserId : 6371ca31-da7a-458a-88a3-98600bff2753
             * cUserId : 443314bc-3c19-4a69-98e0-8826e69f2137
             * aUserName : lodi
             * bUserName : 陈绍鹏
             * cUserName : 经纪人
             * aUserMobilephone : 15920274119
             * bUserMobilephone : 18918918910
             * cUserMobilephone : 18918918909
             * aIdCard : 44190019910612673x
             * bIdCard : 44190019910612672x
             * orderMoney : 500
             * bankNumber : 6214837679805378
             * bankName : 中国银行
             * bankOpenanaccount : null
             * orderStatus : 2
             * dateSignature : 1466048819000
             * refundAffirm : null
             * collectionUserId : null
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

            public List<Content> getContent() {
                return content;
            }

            public void setContent(List<Content> content) {
                this.content = content;
            }

            public static class Content implements Serializable {
                private Object creator;
                private long createTime;
                private Object updater;
                private Object updateTime;
                private boolean activate;
                private String id;
                private String orderNo;
                private String aUserId;
                private String bUserId;
                private String cUserId;
                private String aUserName;
                private String bUserName;
                private String cUserName;
                private String aUserMobilephone;
                private String bUserMobilephone;
                private String cUserMobilephone;
                private String aIdCard;
                private String bIdCard;
                private int orderMoney;
                private String bankNumber;
                private String bankName;
                private Object bankOpenanaccount;
                private int orderStatus;
                private long dateSignature;
                private Object refundAffirm;
                private Object collectionUserId;


                public Long getCreateTimeCompare() {
                    return createTime;
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

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getOrderNo() {
                    return orderNo;
                }

                public void setOrderNo(String orderNo) {
                    this.orderNo = orderNo;
                }

                public String getAUserId() {
                    return aUserId;
                }

                public void setAUserId(String aUserId) {
                    this.aUserId = aUserId;
                }

                public String getBUserId() {
                    return bUserId;
                }

                public void setBUserId(String bUserId) {
                    this.bUserId = bUserId;
                }

                public String getCUserId() {
                    return cUserId;
                }

                public void setCUserId(String cUserId) {
                    this.cUserId = cUserId;
                }

                public String getAUserName() {
                    return aUserName;
                }

                public void setAUserName(String aUserName) {
                    this.aUserName = aUserName;
                }

                public String getBUserName() {
                    return bUserName;
                }

                public void setBUserName(String bUserName) {
                    this.bUserName = bUserName;
                }

                public String getCUserName() {
                    return cUserName;
                }

                public void setCUserName(String cUserName) {
                    this.cUserName = cUserName;
                }

                public String getAUserMobilephone() {
                    return aUserMobilephone;
                }

                public void setAUserMobilephone(String aUserMobilephone) {
                    this.aUserMobilephone = aUserMobilephone;
                }

                public String getBUserMobilephone() {
                    return bUserMobilephone;
                }

                public void setBUserMobilephone(String bUserMobilephone) {
                    this.bUserMobilephone = bUserMobilephone;
                }

                public String getCUserMobilephone() {
                    return cUserMobilephone;
                }

                public void setCUserMobilephone(String cUserMobilephone) {
                    this.cUserMobilephone = cUserMobilephone;
                }

                public String getAIdCard() {
                    return aIdCard;
                }

                public void setAIdCard(String aIdCard) {
                    this.aIdCard = aIdCard;
                }

                public String getBIdCard() {
                    return bIdCard;
                }

                public void setBIdCard(String bIdCard) {
                    this.bIdCard = bIdCard;
                }

                public int getOrderMoney() {
                    return orderMoney;
                }

                public void setOrderMoney(int orderMoney) {
                    this.orderMoney = orderMoney;
                }

                public String getBankNumber() {
                    return bankNumber;
                }

                public void setBankNumber(String bankNumber) {
                    this.bankNumber = bankNumber;
                }

                public String getBankName() {
                    return bankName;
                }

                public void setBankName(String bankName) {
                    this.bankName = bankName;
                }

                public Object getBankOpenanaccount() {
                    return bankOpenanaccount;
                }

                public void setBankOpenanaccount(Object bankOpenanaccount) {
                    this.bankOpenanaccount = bankOpenanaccount;
                }

                public int getOrderStatus() {
                    return orderStatus;
                }

                public void setOrderStatus(int orderStatus) {
                    this.orderStatus = orderStatus;
                }

                public long getDateSignature() {
                    return dateSignature;
                }

                public void setDateSignature(long dateSignature) {
                    this.dateSignature = dateSignature;
                }

                public Object getRefundAffirm() {
                    return refundAffirm;
                }

                public void setRefundAffirm(Object refundAffirm) {
                    this.refundAffirm = refundAffirm;
                }

                public Object getCollectionUserId() {
                    return collectionUserId;
                }

                public void setCollectionUserId(Object collectionUserId) {
                    this.collectionUserId = collectionUserId;
                }
            }
        }
    }
}
