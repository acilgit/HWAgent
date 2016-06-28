package com.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XY on 2016/6/21.
 */
public class HouseRecommendBean implements Serializable {

    /**
     * resultCode : 1
     * message : 租房列表
     * content : {"content":[{"id":"2cccb42f-5377-425c-9018-bb0b76f7fb29","villageName":"这是一个测试小区","title":"312123","secondTitle":"123","price":31212,"address":"东莞市长安镇这是一个测试小区","houseSize":50,"houseShape":"五室(以上)一厅","tags":"","coverPicture":null},{"id":"53cd5b1c-24de-4732-bccd-c663bda7ba53","villageName":"名流印象","title":"名流印象精装修","secondTitle":"1号楼一单元","price":2800,"address":"东莞市凤岗镇雁田村凤深大道与龙平路交界处","houseSize":98,"houseShape":"三室二厅","tags":"","coverPicture":"http://192.168.1.222:9000/upload/image/20160601/1464769446441031578.jpg"}],"number":0,"size":10,"totalElements":2,"sort":null,"numberOfElements":2,"lastPage":true,"firstPage":true,"totalPages":1}
     */

    private int resultCode;
    private String message;
    /**
     * content : [{"id":"2cccb42f-5377-425c-9018-bb0b76f7fb29","villageName":"这是一个测试小区","title":"312123","secondTitle":"123","price":31212,"address":"东莞市长安镇这是一个测试小区","houseSize":50,"houseShape":"五室(以上)一厅","tags":"","coverPicture":null},{"id":"53cd5b1c-24de-4732-bccd-c663bda7ba53","villageName":"名流印象","title":"名流印象精装修","secondTitle":"1号楼一单元","price":2800,"address":"东莞市凤岗镇雁田村凤深大道与龙平路交界处","houseSize":98,"houseShape":"三室二厅","tags":"","coverPicture":"http://192.168.1.222:9000/upload/image/20160601/1464769446441031578.jpg"}]
     * number : 0
     * size : 10
     * totalElements : 2
     * sort : null
     * numberOfElements : 2
     * lastPage : true
     * firstPage : true
     * totalPages : 1
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

    public static class ContentBean{
        private int number;
        private int size;
        private int totalElements;
        private Object sort;
        private int numberOfElements;
        private boolean lastPage;
        private boolean firstPage;
        private int totalPages;
        /**
         * id : 2cccb42f-5377-425c-9018-bb0b76f7fb29
         * villageName : 这是一个测试小区
         * title : 312123
         * secondTitle : 123
         * price : 31212
         * address : 东莞市长安镇这是一个测试小区
         * houseSize : 50
         * houseShape : 五室(以上)一厅
         * tags :
         * coverPicture : null
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

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public static class Content implements Serializable{
            private String id;
            private String villageName;
            private String title;
            private String secondTitle;
            private int price;
            private int permitType;
            private String address;
            private int houseSize;
            private String houseShape;
            private String tags;
            private String coverPicture;

            public String getPermitTypeString() {
                switch (permitType) {
                    case 1:
                        return "租房";
                    case 2:
                        return "二手房";
                    default:
                        return "";
                }
            }
            public int getPermitType() {
                return permitType;
            }

            public void setPermitType(int permitType) {
                this.permitType = permitType;
            }

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
