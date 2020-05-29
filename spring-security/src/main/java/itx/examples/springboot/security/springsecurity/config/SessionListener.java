package itx.examples.springboot.security.springsecurity.config;

import itx.examples.springboot.security.springsecurity.services.UserAccessService;
import itx.examples.springboot.security.springsecurity.services.dto.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

@Component
public class SessionListener implements HttpSessionListener, HttpSessionIdListener, ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(SessionListener.class);

    private final UserAccessService userAccessService;

    @Autowired
    public SessionListener(UserAccessService userAccessService) {
        this.userAccessService = userAccessService;
    }

    @Override
    public void sessionIdChanged(HttpSessionEvent se, String oldSessionId) {
        LOG.info("sessionIdChanged: {}->{}", oldSessionId, se.getSession().getId());
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOG.info("sessionCreated: {}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LOG.info("sessionDestroyed: {}", se.getSession().getId());
        userAccessService.logout(SessionId.from(se.getSession().getId()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOG.info("setApplicationContext");
        if (applicationContext instanceof WebApplicationContext) {
            WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
            webApplicationContext.getServletContext().setSessionTimeout(5);
        } else {
            LOG.warn("ERROR: Must be inside a web application context !");
        }
    }

}
