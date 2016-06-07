package com.housingonitoringagent.homeworryagent;

import android.app.Activity;

import com.housingonitoringagent.homeworryagent.activity.LoginActivity;
import com.housingonitoringagent.homeworryagent.beans.UserBean;
import com.housingonitoringagent.homeworryagent.cache.PreferencesKey;
import com.housingonitoringagent.homeworryagent.cache.SecurityStorage;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class User {
    // 安全存储对象
    public static SecurityStorage storage = new SecurityStorage(App.getInstance(), PreferencesKey.User.NAME);

    public static void clear() {
        storage.clear();
    }

    /**
     * @param activity
     * @return 是否已登录
     */
    public static boolean tryLogin(Activity activity) {
        if (isLogin()) {
            return true;
        } else {
            LoginActivity.start(activity);
            return false;
        }
    }

    /**
     *
     * @return 是否已登录
     */
    public static boolean isLogin() {
        return storage.getBoolean(PreferencesKey.User.LOGIN_STATE, false);
    }

    public static void logIn(String provider, UserBean loginResp) {
        setSessionId(loginResp.getContent().getSessionId());
        setHeadUrl(loginResp.getContent().getAvatar());
        setUsername(loginResp.getContent().getName());
        setNickname(loginResp.getContent().getName());
        setUserId(loginResp.getContent().getId());
        setAccount(loginResp.getContent().getMobilephone());
        setMobilePhone(loginResp.getContent().getMobilephone());
        setUserSex(loginResp.getContent().getSex());

        setIntermediaryStoreId(loginResp.getContent().getIntermediaryStoreId());
        setIntermediaryStoreName(loginResp.getContent().getIntermediaryStoreName());
        setIntermediaryCompanyId(loginResp.getContent().getIntermediaryCompanyId());
        setIntermediaryCompanyName(loginResp.getContent().getIntermediaryCompanyName());
        setYearlyInspection(loginResp.getContent().getYearlyInspection());
        setWorkingLife(loginResp.getContent().getWorkingLife());
        setCertificatePicture(loginResp.getContent().getCertificatePicture());
        setCertificateInfo(loginResp.getContent().getCertificateInfo());
        setDuty(loginResp.getContent().getDuty());
        setAcademicTitle(loginResp.getContent().getAcademicTitle());
        setComplaintCount(loginResp.getContent().getComplaintCount());
        setComplaintAgreeCount(loginResp.getContent().getComplaintAgreeCount());
        setComplaintCompleteCount(loginResp.getContent().getComplaintCompleteCount());
        setHouseEvaluatePraiseAmount(loginResp.getContent().getHouseEvaluatePraiseAmount());
        setCertStatus(loginResp.getContent().getCertStatus());
        setIdCard(loginResp.getContent().getIdCard());

        setLoginState(true);

    }

/*    String id;
    String intermediaryStoreId;     //门店id
    String intermediaryStoreName;   //门店名
    String intermediaryCompanyId;   //公司id
    String intermediaryCompanyName; //公司名
    String yearlyInspection; //年检情况
    Integer workingLife;//从业年限
    String certificatePicture; //持证图片
    String certificateInfo; //持证详情
    Integer duty;//职务
    Integer academicTitle;//职称
    Integer complaintCount; //投诉数
    Integer complaintAgreeCount;    //投诉同意数
    Integer complaintCompleteCount; //投诉完成数
    Integer houseEvaluatePraiseAmount;  //房源点赞数
    Integer certStatus; //审核状态
    String name;	//姓名
    String avatar;	//头像
    Boolean realName;	//是否实名制认证过
    String idCard;  //身份证*/

    public static String getIntermediaryStoreId() {
        return storage.getString("neighbourintermediaryStoreIdsJson", "");
    }

    public static void setIntermediaryStoreId(String intermediaryStoreId) {
        storage.put(PreferencesKey.User.SESSIONID, intermediaryStoreId);
    }

    public static String getIntermediaryStoreName() {
        return storage.getString("intermediaryStoreName", "");
    }

    public static void setIntermediaryStoreName(String intermediaryStoreName) {
        storage.put(PreferencesKey.User.SESSIONID, intermediaryStoreName);
    }

    public static String getIntermediaryCompanyId() {
        return storage.getString("intermediaryCompanyId", "");
    }

    public static void setIntermediaryCompanyId(String intermediaryCompanyId) {
        storage.put(PreferencesKey.User.SESSIONID, intermediaryCompanyId);
    }

    public static String getIntermediaryCompanyName() {
        return storage.getString("intermediaryCompanyName", "");
    }

    public static void setIntermediaryCompanyName(String intermediaryCompanyName) {
        storage.put(PreferencesKey.User.SESSIONID, intermediaryCompanyName);
    }

    public static String getYearlyInspection() {
        return storage.getString("yearlyInspection", "");
    }

    public static void setYearlyInspection(String yearlyInspection) {
        storage.put(PreferencesKey.User.SESSIONID, yearlyInspection);
    }

    public static Integer getWorkingLife() {
        return storage.getInt("workingLife", 0);
    }

    public static void setWorkingLife(Integer workingLife) {
        storage.put(PreferencesKey.User.SESSIONID, workingLife);
    }

    public static String getCertificatePicture() {
        return storage.getString("certificatePicture", "");
    }

    public static void setCertificatePicture(String certificatePicture) {
        storage.put(PreferencesKey.User.SESSIONID, certificatePicture);
    }

    public static String getCertificateInfo() {
        return storage.getString("certificateInfo", "");
    }

    public static void setCertificateInfo(String certificateInfo) {
        storage.put(PreferencesKey.User.SESSIONID, certificateInfo);
    }

    public static Integer getDuty() {
        return storage.getInt("duty", 0);
    }

    public static void setDuty(Integer duty) {
        storage.put(PreferencesKey.User.SESSIONID, duty);
    }

    public static Integer getAcademicTitle() {
        return storage.getInt("academicTitle", 0);
    }

    public static void setAcademicTitle(Integer academicTitle) {
        storage.put(PreferencesKey.User.SESSIONID, academicTitle);
    }

    public static Integer getComplaintCount() {
        return storage.getInt("complaintCount", 0);
    }

    public static void setComplaintCount(Integer complaintCount) {
        storage.put(PreferencesKey.User.SESSIONID, complaintCount);
    }

    public static Integer getComplaintAgreeCount() {
        return storage.getInt("complaintAgreeCount", 0);
    }

    public static void setComplaintAgreeCount(Integer complaintAgreeCount) {
        storage.put(PreferencesKey.User.SESSIONID, complaintAgreeCount);
    }

    public static Integer getComplaintCompleteCount() {
        return storage.getInt("complaintCompleteCount", 0);
    }

    public static void setComplaintCompleteCount(Integer complaintCompleteCount) {
        storage.put(PreferencesKey.User.SESSIONID, complaintCompleteCount);
    }

    public static Integer getHouseEvaluatePraiseAmount() {
        return storage.getInt("houseEvaluatePraiseAmount", 0);
    }

    public static void setHouseEvaluatePraiseAmount(Integer houseEvaluatePraiseAmount) {
        storage.put(PreferencesKey.User.SESSIONID, houseEvaluatePraiseAmount);
    }

    public static Integer getCertStatus() {
        return storage.getInt("certStatus", 0);
    }

    public static void setCertStatus(Integer certStatus) {
        storage.put(PreferencesKey.User.SESSIONID, certStatus);
    }

    public static String getIdCard() {
        return storage.getString("idCard", "");
    }

    public static void setIdCard(String idCard) {
        storage.put(PreferencesKey.User.SESSIONID, idCard);
    }

    protected static void setLoginState(boolean loginState) {
        storage.put(PreferencesKey.User.LOGIN_STATE, loginState);
    }

    public static String getSessionId() {
        return storage.getString(PreferencesKey.User.SESSIONID, null);
    }
    public static void setSessionId(String memberId) {
        storage.put(PreferencesKey.User.SESSIONID, memberId);
    }

    public static void setType(int type) {
        storage.put(PreferencesKey.User.TYPE, type);
    }

    public static void setMobilePhone(String mobilephone) {
        storage.put(PreferencesKey.User.MOBILEPHONE, mobilephone);
    }

    public static void setHeadUrl(String headUrl) {
        storage.put(PreferencesKey.User.PHOTOS, headUrl);
    }
    public static void setUserSex(int sex) {
        storage.put(PreferencesKey.User.sex, sex);
    }


    public static void setAccount(String account) {
        storage.put(PreferencesKey.User.ACCOUNT, account);
    }

    public static void setUserId(String userId) {
        storage.put(PreferencesKey.User.ID, userId);
    }

    public static void setNickname(String nickname) {
        storage.put(PreferencesKey.User.NICKNAME, nickname);
    }

    public static void setUsername(String username) {
        storage.put(PreferencesKey.User.NAMES, username);
    }

    public static void setUserYZ(boolean YZ) {
        storage.put(PreferencesKey.User.YZ, YZ);
    }

    // 涉及到清除缓存等IO操作，建议在子线程里调用
    public static void logOut() {
        App.getInstance().clearAppCache();
        App.getInstance().clearAppData();
    }

    public static int getType() {
        return storage.getInt(PreferencesKey.User.TYPE, 1);
    }

    public static String getUsername() {
        return storage.getString(PreferencesKey.User.NAMES, null);
    }

    public static String getMobilephone() {
        return storage.getString(PreferencesKey.User.MOBILEPHONE, null);
    }
    public static String getHeadUrl() {
        return storage.getString(PreferencesKey.User.PHOTOS, null);
    }
    public static String getAccount() {
        return storage.getString(PreferencesKey.User.ACCOUNT, null);
    }
    public static String getUserId() {
        return storage.getString(PreferencesKey.User.ID, null);
    }
    public static String getNickname() {
        return storage.getString(PreferencesKey.User.NICKNAME, null);
    }

    public static int getUserSex() {
        return storage.getInt(PreferencesKey.User.sex, null);
    }

    public static String getUserSexName() {
        return storage.getInt(PreferencesKey.User.sex, 0) == 1 ? "男" : "女";
    }

}
