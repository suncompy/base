package com.base.sso.core.util;

import com.base.sso.core.conf.Conf;
import com.base.sso.core.store.SsoLoginStore;
import com.base.sso.core.user.SsoUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author
 */
public class SsoLoginHelper {


    /**
     * get sessionid by cookie (web)
     *
     * @param request
     * @return
     */
    public static String getSessionIdByCookie(HttpServletRequest request) {
        String cookieSessionId = CookieUtil.getValue(request, Conf.SSO_SESSIONID);
        return cookieSessionId;
    }

    /**
     * set sessionid in cookie (web)
     *
     * @param response
     * @param sessionId
     */
    public static void setSessionIdInCookie(HttpServletResponse response, String sessionId) {
        if (sessionId!=null && sessionId.trim().length()>0) {
            CookieUtil.set(response, Conf.SSO_SESSIONID, sessionId, false);
        }
    }

    /**
     * remove sessionId in cookie (web)
     *
     * @param request
     * @param response
     */
    public static void removeSessionIdInCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, Conf.SSO_SESSIONID);
    }

    /**
     * load cookie sessionid (app)
     *
     * @param request
     * @return
     */
    public static String cookieSessionIdGetByHeader(HttpServletRequest request) {
        String cookieSessionId = request.getHeader(Conf.SSO_SESSIONID);
        return cookieSessionId;
    }

    /**
     * login check
     *
     * @param request
     * @return
     */
    public static SsoUser loginCheck(HttpServletRequest request){
        String cookieSessionId = getSessionIdByCookie(request);
        if (cookieSessionId!=null && cookieSessionId.trim().length()>0) {
            return loginCheck(cookieSessionId);
        }
        return null;
    }

    /**
     * login check
     *
     * @param sessionId
     * @return
     */
    public static SsoUser loginCheck(String  sessionId){
        if (sessionId!=null && sessionId.trim().length()>0) {
            SsoUser xxlUser = SsoLoginStore.get(sessionId);
            if (xxlUser != null) {
                return xxlUser;
            }
        }
        return null;
    }

    /**
     * client login (web)
     *
     * @param response
     * @param sessionId
     * @param xxlUser
     */
    public static void login(HttpServletResponse response,
                             String sessionId,
                             SsoUser xxlUser) {

        SsoLoginStore.put(sessionId, xxlUser);
        CookieUtil.set(response, Conf.SSO_SESSIONID, sessionId, false);
    }

    /**
     * client login (app)
     *
     * @param sessionId
     * @param xxlUser
     */
    public static void login(String sessionId,
                             SsoUser xxlUser) {
        SsoLoginStore.put(sessionId, xxlUser);
    }


    /**
     * client logout (web)
     *
     * @param request
     * @param response
     */
    public static void logout(HttpServletRequest request,
                              HttpServletResponse response) {

        String cookieSessionId = getSessionIdByCookie(request);

        if (cookieSessionId != null) {
            SsoLoginStore.remove(cookieSessionId);
        }
        CookieUtil.remove(request, response, Conf.SSO_SESSIONID);
    }

    /**
     * client logout (app)
     *
     * @param sessionId
     */
    public static void logout(String sessionId) {
        SsoLoginStore.remove(sessionId);
    }

}
