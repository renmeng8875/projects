package cn.javass.commons.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.javass.commons.Constants;

/**
 * 用户设置当前web环境上下文，用于方便如JSP页面使用
 * @author Administrator
 *
 */
public class ContextPathFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String contextPath = ((HttpServletRequest) request).getContextPath();
        request.setAttribute(Constants.CONTEXT_PATH, contextPath);
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
    }
}
