package br.com.bureau.tracking.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import br.com.bureau.tracking.dto.UserDTO;
import br.com.bureau.tracking.exceptions.AuthorizationException;
import br.com.bureau.tracking.models.enuns.Role;
import br.com.bureau.tracking.queue.GetUserSender;

public class CustomAuthorization extends GenericFilterBean {
	
	private GetUserSender userSender;
	
	public CustomAuthorization(GetUserSender userSender) {
		this.userSender = userSender;
	}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, AuthorizationException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
	    if (httpRequest.getHeader("Authorization") != null) {
			UserDTO user = this.userSender.getUserByTokenSync(httpRequest.getHeader("Authorization"));
			if (user == null || !user.getRoles().contains(Role.TRACKING)) {
				this.sendFaileAuthorization(response);
				return;
			}
			UsernamePasswordAuthenticationToken details = new UsernamePasswordAuthenticationToken(user, null, null);
			SecurityContextHolder.getContext().setAuthentication(details);
			chain.doFilter(request, response);
	    } else {
	    	chain.doFilter(request, response);
	    }
    }
	
	private void sendFaileAuthorization(ServletResponse res) throws IOException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(json());
	}

	private String json() {
		long date = new Date().getTime();
		return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"User not authenticated\", "
				+ "\"message\": \"User or password not valid\" }";
	}
	
}
