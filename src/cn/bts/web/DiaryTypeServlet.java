package cn.bts.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bts.dao.DiaryDao;
import cn.bts.dao.DiaryTypeDao;
import cn.bts.model.DiaryType;
import cn.bts.util.DbUtil;
import cn.bts.util.StringUtil;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年5月9日
* @Description 日志类别
*/
public class DiaryTypeServlet extends HttpServlet {

	private static final long serialVersionUID = -6928493816631732690L;
	DbUtil db=new DbUtil();
	DiaryTypeDao dao=new DiaryTypeDao();
	DiaryDao diaryDao=new DiaryDao();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("list".equals(action)) {
			diaryTypeList(request,response);
		}else if("preSave".equals(action)) {
			diaryTypePreSave(request,response);
		}else if("save".equals(action)) {
			diaryTypeSave(request,response);
		}else if("delete".equals(action)) {
			diaryTypeDelete(request,response);
		}
	}
	
	private void diaryTypeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con=null;
		try {
			con=db.getConnection();
			List<DiaryType> diaryTypeList=dao.diaryTypeList(con);
			req.setAttribute("diaryTypeList", diaryTypeList);
			req.setAttribute("mainPage", "diaryType/diaryTypeList.jsp");
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
	
	private void diaryTypePreSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String diaryTypeId=req.getParameter("diaryTypeId");
		if(StringUtil.isNotEmpty(diaryTypeId)) {
			Connection con=null;
			try {
				con=db.getConnection();
				DiaryType diaryType=dao.diaryTypeShow(con,diaryTypeId);
				req.setAttribute("diaryType", diaryType);
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
		req.setAttribute("mainPage", "diaryType/diaryTypeSv.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
		
	}
	
	private void diaryTypeSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String diaryTypeId=req.getParameter("diaryTypeId");
		String typeName=req.getParameter("typeName");
		DiaryType diaryType=new DiaryType(typeName);
		if(StringUtil.isNotEmpty(diaryTypeId)) {
			diaryType.setDiaryTypeId(Integer.valueOf(diaryTypeId));
		}
		Connection con=null;
		try {
			con=db.getConnection();
			int saveNum=0;
			if(StringUtil.isNotEmpty(diaryTypeId)) {
				saveNum=dao.diaryTypeUpdate(con, diaryType);
			}else {
				saveNum=dao.diaryTypeAdd(con, diaryType);
			}
			if(saveNum>0) {
				req.getRequestDispatcher("diaryType?action=list").forward(req, resp);
			}else {
				req.setAttribute("diaryType",diaryType);
				req.setAttribute("error", "保存失败");
				req.setAttribute("mainPage", "diaryType/diaryTypeSv.jsp");
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
	
	private void diaryTypeDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String diaryTypeId=req.getParameter("diaryTypeId");
		System.out.println("diaryTypeId:"+diaryTypeId);
		Connection con=null;
		try {
			con=db.getConnection();
			System.out.println(diaryDao.existDiaryWithTypeId(con, diaryTypeId));
			if(diaryDao.existDiaryWithTypeId(con, diaryTypeId)) {
				req.setAttribute("error", "日志类别下有日志，不能删除该日志");
			}else {
				dao.diaryTypeDelete(con, diaryTypeId);
			}
			req.getRequestDispatcher("diaryType?action=list").forward(req, resp);
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
