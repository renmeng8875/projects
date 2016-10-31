package com.h5.weibo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.h5.weibo.model.User;
/************************
** 整个应用的过滤器，用于校验是否登录、关闭数据库连接等
*************************/
public class ApplicationFilter implements Filter{

	

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			ActionContext.setRequest(request);
			ActionContext.setResponse(response);
			
			String uri = request.getRequestURI();
			uri = uri.substring(request.getContextPath().length());
			if(!(uri.startsWith("/user/") || uri.startsWith("/css/") || uri.startsWith("/images/") || uri.startsWith("/js/"))) {
				// 只要user目录和静态资源不需要登录也可以访问，其他的都需要登录
				User u =  (User) request.getSession().getAttribute(ApplicationConstant.SESSION_USER);
				if(u == null) {
					// 没有登录，跳转到登录页面
					response.sendRedirect(request.getContextPath() + "/user/login.jsp");
					return;
				}
			}
			
			chain.doFilter(req, resp);
		}catch(Exception e) {
			throw new ServletException(e);
		}finally {
			// 最终关闭数据库的连接
		}
	}
    
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
