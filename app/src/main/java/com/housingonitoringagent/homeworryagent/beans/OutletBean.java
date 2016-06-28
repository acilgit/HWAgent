package com.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XY on 2016/6/21.
 */
public class OutletBean implements Serializable {

    /**
     * resultCode : 1
     * message : 获取门店数据成功
     * content : {"intermediaryStoreId":"16a3aec5-63ef-4c65-a99d-ef36802e6e55","intermediaryStoreName":"合富莞城店","picture":"http://192.168.1.233:9000/upload/image/20160527/1464319785403007482.png","intermediaryCompanyName":"东莞市合富置业有限公司","intermediaryStoreAddress":"城区坝头路188号","visitNumber":0,"secondHouses":[{"id":"86dae364-79c4-45bc-b467-55095fb69f1c","villageName":"霖峰\u2022壹山境","title":"精装2房","secondTitle":"保养好","price":17865,"address":"元美路口","houseSize":82,"houseShape":"二室二厅","tags":"","coverPicture":"http://192.168.1.222:9000/upload/image/20160601/1464781717490034364.jpg"}],"rentHouses":[{"id":"53cd5b1c-24de-4732-bccd-c663bda7ba53","villageName":"名流印象","title":"名流印象精装修","secondTitle":"1号楼一单元","price":2800,"address":"东莞市凤岗镇雁田村凤深大道与龙平路交界处","houseSize":98,"houseShape":"三室二厅","tags":"","coverPicture":"http://192.168.1.222:9000/upload/image/20160601/1464769446441031578.jpg"}],"secondHousesCount":1,"goodEvaluateNumber":0,"normalEvaluateNumber":0,"badEvaluateNumber":0,"rentHousesCount":1}
     */

    private int resultCode;
    private String message;
    /**
     * intermediaryStoreId : 16a3aec5-63ef-4c65-a99d-ef36802e6e55
     * intermediaryStoreName : 合富莞城店
     * picture : http://192.168.1.233:9000/upload/image/20160527/1464319785403007482.png
     * intermediaryCompanyName : 东莞市合富置业有限公司
     * intermediaryStoreAddress : 城区坝头路188号
     * visitNumber : 0
     * secondHouses : [{"id":"86dae364-79c4-45bc-b467-55095fb69f1c","villageName":"霖峰\u2022壹山境","title":"精装2房","secondTitle":"保养好","price":17865,"address":"元美路口","houseSize":82,"houseShape":"二室二厅","tags":"","coverPicture":"http://192.168.1.222:9000/upload/image/20160601/1464781717490034364.jpg"}]
     * rentHouses : [{"id":"53cd5b1c-24de-4732-bccd-c663bda7ba53","villageName":"名流印象","title":"名流印象精装修","secondTitle":"1号楼一单元","price":2800,"address":"东莞市凤岗镇雁田村凤深大道与龙平路交界处","houseSize":98,"houseShape":"三室二厅","tags":"","coverPicture":"http://192.168.1.222:9000/upload/image/20160601/1464769446441031578.jpg"}]
     * secondHousesCount : 1
     * goodEvaluateNumber : 0
     * normalEvaluateNumber : 0
     * badEvaluateNumber : 0
     * rentHousesCount : 1
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
        private String intermediaryStoreId;
        private String intermediaryStoreName;
        private String picture;
        private String intermediaryCompanyName;
        private String intermediaryStoreAddress;
        private int visitNumber;
        private int secondHousesCount;
        private int goodEvaluateNumber;
        private int normalEvaluateNumber;
        private int badEvaluateNumber;
        private int rentHousesCount;
        /**
         * id : 86dae364-79c4-45bc-b467-55095fb69f1c
         * villageName : 霖峰•壹山境
         * title : 精装2房
         * secondTitle : 保养好
         * price : 17865
         * address : 元美路口
         * houseSize : 82
         * houseShape : 二室二厅
         * tags :
         * coverPicture : http://192.168.1.222:9000/upload/image/20160601/1464781717490034364.jpg
         */

        private List<SecondHousesBean> secondHouses;
        /**
         * id : 53cd5b1c-24de-4732-bccd-c663bda7ba53
         * villageName : 名流印象
         * title : 名流印象精装修
         * secondTitle : 1号楼一单元
         * price : 2800
         * address : 东莞市凤岗镇雁田村凤深大道与龙平路交界处
         * houseSize : 98
         * houseShape : 三室二厅
         * tags :
         * coverPicture : http://192.168.1.222:9000/upload/image/20160601/1464769446441031578.jpg
         */

        private List<RentHousesBean> rentHouses;

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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getIntermediaryCompanyName() {
            return intermediaryCompanyName;
        }

        public void setIntermediaryCompanyName(String intermediaryCompanyName) {
            this.intermediaryCompanyName = intermediaryCompanyName;
        }

        public String getIntermediaryStoreAddress() {
            return intermediaryStoreAddress;
        }

        public void setIntermediaryStoreAddress(String intermediaryStoreAddress) {
            this.intermediaryStoreAddress = intermediaryStoreAddress;
        }

        public int getVisitNumber() {
            return visitNumber;
        }

        public void setVisitNumber(int visitNumber) {
            this.visitNumber = visitNumber;
        }

        public int getSecondHousesCount() {
            return secondHousesCount;
        }

        public void setSecondHousesCount(int secondHousesCount) {
            this.secondHousesCount = secondHousesCount;
        }

        public int getGoodEvaluateNumber() {
            return goodEvaluateNumber;
        }

        public void setGoodEvaluateNumber(int goodEvaluateNumber) {
            this.goodEvaluateNumber = goodEvaluateNumber;
        }

        public int getNormalEvaluateNumber() {
            return normalEvaluateNumber;
        }

        public void setNormalEvaluateNumber(int normalEvaluateNumber) {
            this.normalEvaluateNumber = normalEvaluateNumber;
        }

        public int getBadEvaluateNumber() {
            return badEvaluateNumber;
        }

        public void setBadEvaluateNumber(int badEvaluateNumber) {
            this.badEvaluateNumber = badEvaluateNumber;
        }

        public int getRentHousesCount() {
            return rentHousesCount;
        }

        public void setRentHousesCount(int rentHousesCount) {
            this.rentHousesCount = rentHousesCount;
        }

        public List<SecondHousesBean> getSecondHouses() {
            return secondHouses;
        }

        public void setSecondHouses(List<SecondHousesBean> secondHouses) {
            this.secondHouses = secondHouses;
        }

        public List<RentHousesBean> getRentHouses() {
            return rentHouses;
        }

        public void setRentHouses(List<RentHousesBean> rentHouses) {
            this.rentHouses = rentHouses;
        }

        public static class SecondHousesBean {
            private String id;
            private String villageName;
            private String title;
            private String secondTitle;
            private int price;
            private String address;
            private int houseSize;
            private String houseShape;
            private String tags;
            private String coverPicture;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVillageName() {
                return villageName;
            }

            public void setVillageName(String villageName) {
                this.villageName = villageName;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSecondTitle() {
                return secondTitle;
            }

            public void setSecondTitle(String secondTitle) {
                this.secondTitle = secondTitle;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getHouseSize() {
                return houseSize;
            }

            public void setHouseSize(int houseSize) {
                this.houseSize = houseSize;
            }

            public String getHouseShape() {
                return houseShape;
            }

            public void setHouseShape(String houseShape) {
                this.houseShape = houseShape;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getCoverPicture() {
                return coverPicture;
            }

            public void setCoverPicture(String coverPicture) {
                this.coverPicture = coverPicture;
            }
        }

        public static class RentHousesBean {
            private String id;
            private String villageName;
            private String title;
            private String secondTitle;
            private int price;
            private String address;
            private int houseSize;
            private String houseShape;
            private String tags;
            private String coverPicture;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVillageName() {
                return villageName;
            }

            public void setVillageName(String villageName) {
                this.villageName = villageName;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSecondTitle() {
                return secondTitle;
            }

            public void setSecondTitle(String secondTitle) {
                this.secondTitle = secondTitle;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getHouseSize() {
                return houseSize;
            }

            public void setHouseSize(int houseSize) {
                this.houseSize = houseSize;
            }

            public String getHouseShape() {
                return houseShape;
            }

            public void setHouseShape(String houseShape) {
                this.houseShape = houseShape;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getCoverPicture() {
                return coverPicture;
            }

            public void setCoverPicture(String coverPicture) {
                this.coverPicture = coverPicture;
            }
        }
    }
}
