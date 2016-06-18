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
        /*取得手机号*/
        String GET_PHONE = SERVER + "/append/common/getMobilephone";
        /*忘记密码*/
        String CHANGE_PASSWORD = SERVER + "/append/common/modifypasswordbycaptcha";
        /*更换手机*/
        String SECURITY_MODIFYMOBILEPHONE = SERVER + "/append/common/modifymobilephone";
        /*通过手机更换密码*/
        String SECURITY_CHANGEPASSWORDBYPHONE = SERVER + "/append/common/modifypasswordbycaptcha";
        /*通过旧密码更改密码*/
        String SECURITY_CHANGEPASSWRODBYOLDPASSWORD = SERVER + "/append/common/modifypasswordbyoldpassword";

        /*获取租房列表*/
        String RENTALHOUSELIST = SERVER + "/append/rentalhouse/rentalhouselist";

        /*看房记录*/
        String VISIT_PERMISSSION_LIST = SERVER + "/append/apply_visit_permit_agent/list";
        /*看房*/
        String VISIT_PERMISSSION_UPDATE = SERVER + "/append/apply_visit_permit_agent/update";
        /*看房*/
        String VISIT_PERMISSSION_END = SERVER + "/append/apply_visit_permit_agent/permitEnd";
        /*资金监管列表*/
        String HOUSE_DEAL_LIST = SERVER + "/append/agent_houseOldDeal/selectPageList";
        /*选择收款人*/
        String HOUSE_DEAL_CHOOSE_PARTY = SERVER + "/append/agent_houseOldDeal/update_receptionMoneyObject";
        /*提交打款申请*/
        String HOUSE_DEAL_CONFIRM_MONEY = SERVER + "/append/agent_houseOldDeal/confirm_money";



        /*获取邻居列表*/
        String NEIGHBOR_LIST = SERVER + "/append/neighbour/info";
        /*检查sessionId是否过期*/
        String CHECKSESSIONID = SERVER + "/append/common/checksessionid";
        /*用户协议*/
        String USERREGISTERDEAL = SERVER + "/frontend/userRegisterDeal";


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
