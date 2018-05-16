package cn.bts.dao;
/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年5月2日
* @Description 按类别分类日志
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import cn.bts.model.DiaryType;

public class DiaryTypeDao {
	
	/**
	 * 日志类别和列表
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<DiaryType> diaryTypeCountList(Connection con)throws Exception{
	
		List<DiaryType> diaryTypeCountList=new ArrayList<DiaryType>();
		String sql="SELECT diaryTypeId,TypeName,COUNT(diaryId) as diaryCount FROM t_diary RIGHT JOIN t_diarytype ON t_diary.typeId=t_diarytype.diaryTypeId GROUP BY TypeName";
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			DiaryType diaryType=new DiaryType();
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("TypeName"));
			diaryType.setDiaryCount(rs.getInt("diaryCount"));
			diaryTypeCountList.add(diaryType);
		}
		return diaryTypeCountList;
	}
	
	/**
	 * 日志类别列表
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<DiaryType> diaryTypeList(Connection con)throws Exception{
		
		List<DiaryType> list=new ArrayList<DiaryType>();
		String sql="select * from t_diarytype";
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			DiaryType diaryType=new DiaryType();
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("TypeName"));
			list.add(diaryType);
		}
		return list;
	}
	
	/**
	 * 增加日志类别
	 * @param cn
	 * @param diaryType
	 * @return
	 * @throws Exception
	 */
	public int diaryTypeAdd(Connection cn,DiaryType diaryType)throws Exception {
		
		String sql="insert into t_diarytype values(null,?)";
		PreparedStatement ps=cn.prepareStatement(sql);
		ps.setString(1, diaryType.getTypeName());
		return ps.executeUpdate();
	}
	
	/**
	 * 修改日志类别
	 * @param cn
	 * @param diaryType
	 * @return
	 * @throws Exception
	 */
	public int diaryTypeUpdate(Connection cn,DiaryType diaryType)throws Exception {
		
		String sql="update t_diarytype set typeName=? where diaryTypeId=?";
		PreparedStatement ps=cn.prepareStatement(sql);
		ps.setString(1, diaryType.getTypeName());
		ps.setInt(2, diaryType.getDiaryTypeId());
		return ps.executeUpdate();
	}
	
	/**
	 * 单个日志类别查询
	 * @param cn
	 * @param diaryTypeId
	 * @return
	 * @throws Exception
	 */
	public DiaryType diaryTypeShow(Connection cn,String diaryTypeId)throws Exception{
		
		String sql="SELECT * from t_diarytype where diaryTypeId=?";
		PreparedStatement ps=cn.prepareStatement(sql);
		ps.setString(1, diaryTypeId);
		ResultSet rs=ps.executeQuery();
		DiaryType diaryType=new DiaryType();
		if(rs.next()) {
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("TypeName"));
		}
		return diaryType;
	}
	
	/**
	 * 删除日志类别
	 * @param cn
	 * @param diaryTypeId
	 * @return
	 * @throws Exception
	 */
	public int diaryTypeDelete(Connection cn,String diaryTypeId)throws Exception{
		
		String sql="delete from t_diarytype where diaryTypeId=?";
		PreparedStatement ps=cn.prepareStatement(sql);
		ps.setString(1, diaryTypeId);
		return ps.executeUpdate(); 
	}
}
