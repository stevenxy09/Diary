package cn.bts.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bts.dao.DiaryDao;
import cn.bts.model.Diary;
import cn.bts.util.DbUtil;
import cn.bts.util.StringUtil;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年5月4日
* @Description 日志
*/
public class DiaryServlet extends HttpServlet {

	private static final long serialVersionUID = -7543624770802932746L;
	
	DbUtil db=new DbUtil();
	DiaryDao dao=new DiaryDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		String action=req.getParameter("action");
		if("show".equals(action)) {
			diaryShow(req,resp);
		}else if("preSave".equals(action)){
			diaryPreSave(req,resp);
		}else if ("save".equals(action)) {
			diarySave(req,resp);
		}else if ("delete".equals(action)) {
			diaryDelete(req,resp);
		}
	}
	
	private void diaryShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String diaryId=req.getParameter("diaryId");
		Connection con=null;
		try {
			con=db.getConnection();
			Diary diary=dao.diaryShow(con, diaryId);
			req.setAttribute("diary", diary);
			req.setAttribute("mainPage", "diary/diaryShow.jsp");
			req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
		} catch (Exception e) {
			
		}finally {
			try {
				db.CloseConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void diaryPreSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String diaryId=req.getParameter("diaryId");
		
		Connection con=null;
		try {
			if(StringUtil.isNotEmpty(diaryId)) {
				con=db.getConnection();
				Diary diary =dao.diaryShow(con, diaryId);
				req.setAttribute("diary", diary);
			}
			req.setAttribute("mainPage", "diary/diarySv.jsp");
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
	
	public void diarySave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String title=req.getParameter("title");
		String content=req.getParameter("content");
		String typeId=req.getParameter("typeId");
		String diaryId=req.getParameter("diaryId");
		Diary diary=new Diary(title,content,Integer.parseInt(typeId));
		if(StringUtil.isNotEmpty(diaryId)) {
			diary.setDiaryId(Integer.valueOf(diaryId));
		}
		Connection con=null;
		try {
			con=db.getConnection();
			int saveNums;
			if(StringUtil.isNotEmpty(diaryId)) {
				saveNums=dao.diaryUpdate(con, diary);	
			}else {
				saveNums=dao.diaryAdd(con, diary);
			}
			if(saveNums>0) {
				req.getRequestDispatcher("main?all=true").forward(req, resp);
			}else {
				req.setAttribute("diary", diary);
				req.setAttribute("error", "保存失败");
				req.setAttribute("mainPage", "diary/diarySv.jsp");
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
	
	public void diaryDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String diaryId=req.getParameter("diaryId"); 
		Connection con=null;
		try {
			con=db.getConnection();
			dao.diaryDelete(con, diaryId);
			req.getRequestDispatcher("main?all=true").forward(req, resp);
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
