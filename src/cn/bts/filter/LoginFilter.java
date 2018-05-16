package cn.bts.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年4月26日
* @Description 过滤器
*/
public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
		
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		HttpServletResponse response=(HttpServletResponse)servletResponse;
		HttpSession session=request.getSession();
		String path=request.getServletPath();
		/*System.out.println("path:"+path);*/
		Object object=session.getAttribute("currentUser");
		/*System.out.println("object:"+object);*/
		if(object==null&&path.indexOf("login")<0&&path.indexOf("bootstrap")<0&&path.indexOf("images")<0) {
			response.sendRedirect("login.jsp");
		}else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
