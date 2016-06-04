package com.housingonitoringagent.homeworryagent.utils.net;

import com.housingonitoringagent.homeworryagent.User;

public class VolleyManager {

    public static String getCookies() {

        String sessionId = User.getSessionId();
        String cookie = null;
        if (sessionId != null) {
            cookie = "ANDROID_SESSID=" + sessionId;
        }
        return cookie;
    }

}
