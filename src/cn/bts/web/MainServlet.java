package cn.bts.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.bts.dao.DiaryDao;
import cn.bts.dao.DiaryDateDao;
import cn.bts.dao.DiaryTypeDao;
import cn.bts.model.Diary;
import cn.bts.model.DiaryType;
import cn.bts.model.PageBean;
import cn.bts.util.DbUtil;
import cn.bts.util.PropertiesUtil;
import cn.bts.util.StringUtil;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年4月26日
* @Description 主界面
*/
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 5475156515211640953L;
	
	DbUtil db=new DbUtil();
	DiaryDao dao=new DiaryDao();
	DiaryTypeDao diaryTypeDao=new DiaryTypeDao();
	DiaryDateDao diarydateDao=new DiaryDateDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		HttpSession session=req.getSession();
		String s_typeId=req.getParameter("s_typeId");
		String s_releaseDateStr=req.getParameter("s_releaseDateStr");
		String s_title=req.getParameter("s_title");
		String all=req.getParameter("all");
		String page=req.getParameter("page");
		Diary diary=new Diary();
		if("true".equals(all)) {
			if(StringUtil.isNotEmpty(s_title)) {
				diary.setTitle(s_title);
			}
			session.setAttribute("s_title", s_title);
			session.removeAttribute("s_releaseDateStr");
			session.removeAttribute("s_typeId");
		}else {
			if(StringUtil.isNotEmpty(s_typeId)) {
				diary.setTypeId(Integer.parseInt(s_typeId));
				session.setAttribute("s_typeId", s_typeId);
				session.removeAttribute("s_releaseDateStr");
				session.removeAttribute("s_title");
			}
			if(StringUtil.isNotEmpty(s_releaseDateStr)) {
				/*s_releaseDateStr=new String(s_releaseDateStr.getBytes("ISO-8859-1"),"UTF-8");*/
				diary.setReleaseDateStr(s_releaseDateStr);
				session.setAttribute("s_releaseDateStr", s_releaseDateStr);
				session.removeAttribute("s_typeId");
				session.removeAttribute("s_title");
			}
			if(StringUtil.isEmpty(s_typeId)) {
				Object o=session.getAttribute("s_typeId");
				if(o!=null) {
					diary.setTypeId(Integer.parseInt((String)o));
				}
			}
			if(StringUtil.isEmpty(s_releaseDateStr)) {
				Object o=session.getAttribute("s_releaseDateStr");
				if(o!=null) {
					diary.setReleaseDateStr((String)o);
				}
			}
			if(StringUtil.isEmpty(s_title)) {
				Object o=session.getAttribute("s_title");
				if(o!=null) {
					diary.setTitle((String)o);
				}
			}
		}
		
		/**当前页*/
		if(StringUtil.isEmpty(page)) {
			page="1";
		}
		Connection con=null;
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		try {
			con=db.getConnection();
			List<Diary> list=dao.diaryList(con,pageBean,diary);
			int total= dao.diaryCount(con,diary);
			String pageCode=this.getPagetion(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			req.setAttribute("pageCode", pageCode);
			req.setAttribute("diaryList", list);
			List<DiaryType> list2=diaryTypeDao.diaryTypeCountList(con);
			List<Diary> list3=diarydateDao.diaryCountList(con);
			session.setAttribute("diaryTypeCountList", list2);
			session.setAttribute("diaryDateCountList", list3);
			req.setAttribute("mainPage", "diary/diaryList.jsp");
			req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
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
	
	/**
	 * 拼接分页
	 * @param totalNum
	 * @param currentPage
	 * @param pageSize
	 * @return 分页样式
	 */
	private String getPagetion(int totalNum,int currentPage,int pageSize) {
		
		int totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		pageCode.append("<li><a href='main?page=1'>首页</a></li>");
		if(currentPage==1) {
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}else {
			pageCode.append("<li><a href='main?page="+(currentPage-1)+"'>上一页</a></li>");
		}
		for(int i=currentPage-2;i<=currentPage+2;i++) {
			if(i<1||i>totalPage) {
				continue;
			}
			if(i==currentPage) {
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			}else {
				pageCode.append("<li><a href='main?page="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage==totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		}else {
			pageCode.append("<li><a href='main?page="+(currentPage+1)+"'>下一页</a></li>");
		}
			pageCode.append("<li><a href='main?page="+totalPage+"'>尾页</a></li>");
		return pageCode.toString();
	}
	
}
