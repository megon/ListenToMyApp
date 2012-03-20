package com.listenMyApp.jsf.http;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component("httpResources")
public class HttpResourcesBean implements HttpResources
{
    @Override
    public HttpServletRequest getRequest()
    {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    @Override
    public HttpSession getSession()
    {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    @Override
    public HttpSession getSession(final boolean create)
    {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(create);
    }

    @Override
    public HttpServletResponse getResponse()
    {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }
}
