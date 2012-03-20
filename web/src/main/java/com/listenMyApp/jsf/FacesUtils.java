
package com.listenMyApp.jsf;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;



@Component
public class FacesUtils
{
    public static FacesUtils getInstance()
    {
        return new FacesUtils();
    }

    private FacesUtils()
    {}


    /*
     * REST Utility Methods
     */
    /**
     * Convert a PrettyFaces action name, e.g.: "homeAction" into a PrettyFaces
     * enabled navigation action, e.g.: "pretty:homeAction"
     */
    public String beautify(final String action)
    {
        return "pretty:" + action;
    }

    /*
     * Application Helper Methods
     */
    public String getContextRoot()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getExternalContext().getRequestContextPath();
    }

    /*
     * Servlet Utility Methods
     */
    public HttpServletRequest getHttpServletRequest()
    {
        ExternalContext context = getExternalContext();
        return (HttpServletRequest) context.getRequest();
    }

    public HttpServletResponse getHttpServletResponse()
    {
        ExternalContext context = getExternalContext();
        return (HttpServletResponse) context.getResponse();
    }

    public HttpSession getSession()
    {
        ExternalContext context = getExternalContext();
        HttpSession session = (HttpSession) context.getSession(false);
        return session;
    }

    /*
     * GetAttribute Helper Methods
     */
    public String getRequestParameter(final String param)
    {
        ExternalContext context = getExternalContext();
        return context.getRequestParameterMap().get(param);
    }

    public Object getFormValue(final UIComponent component, final String fieldName)
    {
        String componentId = (String) component.getAttributes().get(fieldName);
        UIInput input = (UIInput) getFacesContext().getViewRoot().findComponent(componentId);
        return input.getValue();
    }

    public Object getSessionAttribute(final String key)
    {
        return getExternalContext().getSessionMap().get(key);
    }

    public void setSessionAttribute(final String key, final Object value)
    {
        getExternalContext().getSessionMap().put(key, value);
    }

    /*
     * Faces Message Helper Methods
     */
    public void setInfoMessage(final String summary)
    {
    	final String message = getMessageResourceString(getFacesContext().getApplication().getMessageBundle(), summary, null, getFacesContext().getViewRoot().getLocale());
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    public void setInfoMessage(final UIComponent component, final String summary)
    {
        getFacesContext().addMessage(component.getClientId(getFacesContext()),
                new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    public void setWarningMessage(final String summary)
    {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));
    }

    public void setErrorMessage(final String summary)
    {
    	final String message = getMessageResourceString(getFacesContext().getApplication().getMessageBundle(), summary, null, getFacesContext().getViewRoot().getLocale());
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
    
    
    public void setLocale(final String locale)
    {
    	getFacesContext().getViewRoot().setLocale(new Locale(locale));
    }
    

    public void setFatalMessage(final String summary)
    {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, null));
    }

    /**
     * Returns true if the provided clientId has messages in the current
     * FacesContext
     * 
     * @param clientId
     * @return
     */
    public boolean hasMessages(final String clientId)
    {
        Iterator<String> iterator = getFacesContext().getClientIdsWithMessages();
        while (iterator.hasNext())
        {
            String id = iterator.next();
            if (id.equals(clientId))
            {
                return true;
            }
        }
        return false;
    }

    /*
     * Faces Object Helpers
     */
    public FacesContext getFacesContext()
    {
        return FacesContext.getCurrentInstance();
    }

    public ExternalContext getExternalContext()
    {
        FacesContext faces = getFacesContext();
        ExternalContext context = faces.getExternalContext();
        return context;
    }

    public UIComponent getComponent(final String clientId)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        UIComponent component = viewRoot.findComponent(clientId);
        if (component == null)
        {
            component = findChildComponent(facesContext, viewRoot, clientId);
        }
        return component;
    }

    private UIComponent findChildComponent(final FacesContext facesContext, final UIComponent component,
            final String clientId)
    {
        if (component == null || component.getChildCount() == 0)
        {
            return null;
        }

        UIComponent result = null;
        for (UIComponent c : component.getChildren())
        {
            if (c.getClientId(facesContext).equals(clientId))
            {
                result = c;
                break;
            }
            else
            {
                result = findChildComponent(facesContext, c, clientId);
                if (result != null && clientId.equals(result.getClientId(facesContext)))
                {
                    break;
                }
            }
        }
        return result;
    }
    
    
	protected static String getMessageResourceString(String bundleName, String key, Object params[], Locale locale) {
		String text = null;
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
		try {
			text = bundle.getString(key + "_detail");
		} catch (MissingResourceException e) {
			try {
				text = bundle.getString(key);
			} catch (MissingResourceException ex) {
				text = key;
			}
		}

		if (params != null) {
			MessageFormat mf = new MessageFormat(text, locale);
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}    
}
