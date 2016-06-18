package com.housingonitoringagent.homeworryagent.beans;

import java.io.Serializable;

/**
 * Created by XY on 2016/6/14.
 */
public class CustomerMsgBean implements Serializable{

    /**
     * type : houseSells
     * id : 86dae364-79c4-45bc-b467-55095fb69f1c
     * houseSellPicture : http://192.168.1.222:9000/upload/image/20160601/1464781717490034364.jpg
     * title : 精装2房
     * houseShape : 二室二厅
     * address : 元美路口
     * price : 150.0
     * storeId : 16a3aec5-63ef-4c65-a99d-ef36802e6e55
     * agentId : 9b870b8c-1eb2-4599-a1a8-b1faa34ea80d
     */

    private String permitType;
    private String id;
    private String houseSellPicture;
    private String title;
    private String houseShape;
    private String address;
    private String price;
    private String storeId;
    private String agentId;

    public String getType() {
        return permitType;
    }

    public void setType(String permitType) {
        this.permitType = permitType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseSellPicture() {
        return houseSellPicture;
    }

    public void setHouseSellPicture(String houseSellPicture) {
        this.houseSellPicture = houseSellPicture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHouseShape() {
        return houseShape;
    }

    public void setHouseShape(String houseShape) {
        this.houseShape = houseShape;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
