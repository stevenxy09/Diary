package cn.bts.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import cn.bts.dao.UserDao;
import cn.bts.model.User;
import cn.bts.util.DateUtil;
import cn.bts.util.DbUtil;
import cn.bts.util.PropertiesUtil;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年5月13日
* @Description 
*/
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 7689530533630295330L;
	
	DbUtil db=new DbUtil();
	UserDao dao=new UserDao();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("preSave".equals(action)) {
			userPreSave(request,response);
		}if("save".equals(action)) {
			userSave(request,response);
		}
	}
	
	private void userPreSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
			
		req.setAttribute("mainPage", "user/userSv.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
	}
	
	private void userSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items=null;
		try {
			/*items=upload.parseRequest((RequestContext)req);*/
			items = (List<FileItem>)upload.parseRequest(new ServletRequestContext(req));
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
			
		Iterator<FileItem> iterator=items.iterator();
		HttpSession session=req.getSession();
		User user=(User)session.getAttribute("currentUser");
		boolean imageChange=false;
		while(iterator.hasNext()) {
			FileItem item=(FileItem)iterator.next();
			if(item.isFormField()) {
				String fileName=item.getFieldName();
				if("nickName".equals(fileName)) {
					user.setNickName(item.getString("utf-8"));
				}if("mood".equals(fileName)) {
					user.setMood(item.getString("utf-8"));
				}
			}else if(!"".equals(item.getName())) {
				try {
					imageChange=true;
					String imageName=DateUtil.getCurrentDateStr();
					user.setImageName(imageName+"."+item.getName().split("\\.")[1]);
					String filePath=PropertiesUtil.getValue("imagePath")+imageName+"."+item.getName().split("\\.")[1];
					item.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		if(!imageChange) {
			user.setImageName(user.getImageName().replaceFirst(PropertiesUtil.getValue("imageFile"),""));
		}
		
		Connection con=null;
		try {
			con=db.getConnection();
			int saveNums=dao.userUpdate(con, user);
			if(saveNums>0) {
				user.setImageName(PropertiesUtil.getValue("imagePath")+user.getImageName());
				session.setAttribute("currentUser", user);
				req.getRequestDispatcher("main?all=true").forward(req, resp);
			}else {
				req.setAttribute("currentUser", user);
				req.setAttribute("error", "保存失败!");
				req.setAttribute("mainPage", "user/userSv.jsp");
				req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				db.CloseConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
