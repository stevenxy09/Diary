package cn.bts.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.bts.dao.UserDao;
import cn.bts.model.User;
import cn.bts.util.DbUtil;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年4月18日
* @Description 登录
*/
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 341111658421536038L;
	
	DbUtil dbUtil=new DbUtil();
	UserDao userDao=new UserDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setHeader("content-type", "text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session=req.getSession();
		String userName=req.getParameter("userName");
		String password=req.getParameter("password");
		String remember=req.getParameter("remember");
		
		Connection con=null;
		try {
			con=dbUtil.getConnection();
			User user=new User(userName,password);
			User currentUser=userDao.login(con, user);
			if(currentUser==null) {
				req.setAttribute("user", user);
				req.setAttribute("error", "用户名或者密码输入错误!");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}else {
				if("remember-me".equals(remember)) {
					rememberMe(userName,password,resp);
				}
				/*System.out.println("success");*/
				session.setAttribute("currentUser", currentUser);
				req.getRequestDispatcher("main").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.CloseConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 登录页面记住用户功能
	 * @param userName
	 * @param password
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	private void rememberMe(String userName,String password,HttpServletResponse response) throws UnsupportedEncodingException {
		
		Cookie user=new Cookie("user", URLEncoder.encode(userName, "utf-8")+"-"+password);
		user.setMaxAge(1*60*60*24*7);
		response.addCookie(user);
	}
	
}
