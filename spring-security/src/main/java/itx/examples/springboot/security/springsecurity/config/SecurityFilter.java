package itx.examples.springboot.security.springsecurity.config;

import itx.examples.springboot.security.springsecurity.services.UserAccessService;
import itx.examples.springboot.security.springsecurity.services.dto.SessionId;
import itx.examples.springboot.security.springsecurity.services.dto.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class SecurityFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);

    private final UserAccessService userAccessService;

    public SecurityFilter(UserAccessService userAccessService) {
        this.userAccessService = userAccessService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        SessionId sessionId = SessionId.from(httpServletRequest.getSession(true).getId());
        Optional<UserData> userData = userAccessService.isAuthenticated(sessionId);
        if (userData.isPresent()) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(new AuthenticationImpl(userData.get().getUserId().getId(), userData.get().getRoles()));
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            chain.doFilter(request, response);
        } else {
            LOG.error("session is not authorized: {}", sessionId);
            HttpServletResponse httpServletResponse = (HttpServletResponse)response;
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }

}
