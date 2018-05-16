package cn.bts.dao;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年4月26日
* @Description 日记
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.bts.model.Diary;
import cn.bts.model.PageBean;
import cn.bts.util.DateUtil;
import cn.bts.util.StringUtil;

public class DiaryDao {
	
	/**
	 * 日记列表
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public List<Diary> diaryList(Connection connection,PageBean pageBean,Diary s_diary)throws Exception{
		
		List<Diary> diaryList=new ArrayList<Diary>();
		StringBuffer sb=new StringBuffer("select * from t_diary t1,t_diarytype t2 where t1.typeId=t2.diaryTypeId");
		if(StringUtil.isNotEmpty(s_diary.getTitle())) {
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
		}
		if(s_diary.getTypeId()!=-1) {
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
		if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())) {
			sb.append(" and DATE_FORMAT(releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
		}
		sb.append(" order by t1.releaseDate desc");
		if(pageBean!=null) {
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement ps=connection.prepareStatement(sb.toString());
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			Diary diary=new Diary();
			diary.setDiaryId(rs.getInt("diaryId"));
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
			diaryList.add(diary);
		}
		return diaryList;
	}
	
	/**
	 * 日记列表总数
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int diaryCount(Connection con,Diary s_diary)throws Exception {
		
		StringBuffer sb=new StringBuffer("select count(*) as total from t_diary t1,t_diarytype t2 where t1.typeId=t2.diaryTypeId");
		if(StringUtil.isNotEmpty(s_diary.getTitle())) {
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
		}
		if(s_diary.getTypeId()!=-1) {
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
		if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())) {
			sb.append(" and DATE_FORMAT(releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
		}
		PreparedStatement ps=con.prepareStatement(sb.toString());
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		}else {
			return 0;
		}
		
	}
	
	/**
	 * 单个日志查询
	 * @param con
	 * @param diaryId
	 * @return
	 * @throws Exception
	 */
	public Diary diaryShow(Connection con,String diaryId)throws Exception {
		
		String sql="select * from t_diary t1,t_diarytype t2 where t1.typeId=t2.diaryTypeId and t1.diaryId=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, diaryId);
		ResultSet rs=ps.executeQuery();
		Diary diary=new Diary();
		if(rs.next()) {
			diary.setDiaryId(rs.getInt("diaryId"));
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setTypeId(rs.getInt("typeId"));
			diary.setTypeName(rs.getString("typeName"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
		}
		return diary;
	}
	
	/**
	 * 增加日志
	 * @param con
	 * @param diary
	 * @return
	 * @throws Exception
	 */
	public int diaryAdd(Connection con,Diary diary)throws Exception{
		
		String sql="insert into t_diary values(null,?,?,?,now())";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, diary.getTitle());
		ps.setString(2, diary.getContent());
		ps.setInt(3, diary.getTypeId());
		return ps.executeUpdate();
	}
	
	/**
	 * 删除日志
	 * @param con
	 * @param diaryId
	 * @return
	 * @throws Exception
	 */
	public int diaryDelete(Connection con,String diaryId)throws Exception {
		
		String sql="delete from t_diary where diaryId=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, diaryId);
		return ps.executeUpdate();
	}
	
	/**
	 * 修改日志
	 * @param con
	 * @param diary
	 * @return
	 * @throws Exception
	 */
	public int diaryUpdate(Connection con,Diary diary)throws Exception{
		
		String sql="update t_diary set title=?,content=?,typeId=? where diaryId=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, diary.getTitle());
		ps.setString(2, diary.getContent());
		ps.setInt(3, diary.getTypeId());
		ps.setInt(4, diary.getDiaryId());
		return ps.executeUpdate();
	}
	
	/**
	 * 判断日志类别下是否有日志
	 * @param cn
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public boolean existDiaryWithTypeId(Connection cn,String typeId)throws Exception {
		
		String sql="select * from t_diary where typeId=?";
		PreparedStatement ps=cn.prepareStatement(sql);
		ps.setString(1, typeId);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			return true; 
		}else {
			return false;
		}
	}
}
