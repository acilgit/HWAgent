package com.housingonitoringagent.homeworryagent;

/**
 * Created by Administrator on 2016/2/27 0027.
 */
public class Const {
//     public static  String SERVER ="http://www.zhijia51.com";
    public static String SERVER = "http://192.168.1.233:9000";


    public interface RequestCode {
        int REQUESTPHONE = 0;
    }

    public interface HeadImage {
        int SIZE = 320;
    }

    public interface serviceMethod {
        /*发送短信验证码*/
        String SENDSMSCAPTCHA = SERVER + "/append/common/sendSmsCaptcha";
        /*登录*/
        String LOGIN = SERVER + "/agent_app/login";
        /*忘记密码*/
        String SAVEPASSWORD = SERVER + "/append/common/savePassword";
        /*忘记密码*/
        String CHANGE_PASSWORD = SERVER + "/agent_app/modifypasswordbycaptcha";
        /*获取中介门店详情*/
        String OUTLET_DETAIL = SERVER + "/append/store_recommend/get";
        /*获取二手房源列表*/
        String OUTLET_HOUSE_SELL_LIST = SERVER + "/append/store_recommend/sell_house_page";
        /*获取等租房源列表*/
        String OUTLET_HOUSE_RENT_LIST = SERVER + "/append/store_recommend/rent_house_page";

        /*看房记录*/
        String VISIT_PERMISSSION_LIST = SERVER + "/append/agent_apply_visit_permit/list";
        /*看房*/
        String VISIT_PERMISSSION_UPDATE = SERVER + "/append/agent_apply_visit_permit/update";
        /*看房*/
        String VISIT_PERMISSSION_END = SERVER + "/append/agent_apply_visit_permit/permitEnd";
        /*资金监管列表*/
        String HOUSE_DEAL_LIST = SERVER + "/append/agent_houseOldDeal/selectPageList";
        /*资金监管详情*/
        String PAYEMENT_DETAIL = SERVER + "/append/agent_houseOldDeal/orderInfo";
        /*选择收款人*/
        String HOUSE_DEAL_CHOOSE_PARTY = SERVER + "/append/agent_houseOldDeal/update_receptionMoneyObject";
        /*提交打款申请*/
        String HOUSE_DEAL_CONFIRM_MONEY = SERVER + "/append/agent_houseOldDeal/confirm_money";

        /*租房详情*/
        String RENTAL_HOUSE_INFO = SERVER + "/append/rentalhouse/rentalhouseinfo?";
        /*二手房详情*/
        String SECOND_HAND_HOUSE_INFO = SERVER + "/append/secondhandhouse/secondhandhouseinfo?";


        /*检查sessionId是否过期*/
        String CHECKSESSIONID = SERVER + "/agent_app/checksessionid";
        /*用户协议*/
        String USERREGISTERDEAL = SERVER + "/frontend/userRegisterDeal";
        /*资金监管协议*/
        String USER_SUPERVISE_DEAL = SERVER + "/frontend/userSuperviseDeal";
        /*关于我们*/
        String ABOUT_US = SERVER + "/frontend/aboutus";

    }

    // 账号
    public interface Account {
        int PHONE_LENGTH = 11;
        int PASSWORD_MIN_LENGTH = 6;
        int PASSWORD_MAX_LENGTH = 16;
    }

    public interface RefreshType {
        int REFRESH = 1;
        int LOAD = 2;
    }
}
