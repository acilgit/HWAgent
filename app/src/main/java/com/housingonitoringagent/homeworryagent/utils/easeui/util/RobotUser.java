package com.housingonitoringagent.homeworryagent.utils.easeui.util;


import com.hyphenate.easeui.domain.EaseUser;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class RobotUser extends EaseUser {
    public RobotUser(String username) {
        super(username.toLowerCase());
    }
}
