package com.housingonitoringagent.homeworryagent.beans;

/**资金管理操作记录
 * Created by Administrator on 2016/6/16 0016.
 */
public class OperationLogBean {

    /**
     * id : HS1465724570753001
     * operator : lodi
     * operationTime : 1465724579000
     * operatorAction : 申请资金监管成功
     * activate : true
     * issucceed : true
     */

    private String id;
    private String operator;
    private long operationTime;
    private String operatorAction;
    private boolean activate;
    private boolean issucceed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(long operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperatorAction() {
        return operatorAction;
    }

    public void setOperatorAction(String operatorAction) {
        this.operatorAction = operatorAction;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public boolean isIssucceed() {
        return issucceed;
    }

    public void setIssucceed(boolean issucceed) {
        this.issucceed = issucceed;
    }
}
