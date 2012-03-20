package com.listenMyApp.jsf.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public interface HttpResources
{
    /**
     * Get the current HttpSession object
     */
    public HttpSession getSession(boolean create);

    /**
     * Get the current HttpServletRequest object.
     */
    public HttpServletRequest getRequest();

    /**
     * Get the current HttpServletResponse object.
     */
    public HttpServletResponse getResponse();

    /**
     * Get the current HttpSession object, passing in true for create
     */
    public HttpSession getSession();
}
