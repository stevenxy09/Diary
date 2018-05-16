package cn.bts.dao;
/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年5月2日
* @Description 按日期分类日志
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.bts.model.Diary;

public class DiaryDateDao {
	
	/**
	 * 日期日志分类
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<Diary> diaryCountList(Connection con)throws Exception{
		
		List<Diary> diaryCountList=new ArrayList<Diary>();
		String sql="SELECT DATE_FORMAT(releaseDate,'%Y年%m月') as diaryDate,COUNT(*) as diaryCount from t_diary GROUP BY DATE_FORMAT(releaseDate,'%Y年%m月') ORDER BY DATE_FORMAT(releaseDate,'%Y年%m月') DESC";
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rSet=ps.executeQuery();
		while(rSet.next()) {
			Diary diary=new Diary();
			diary.setReleaseDateStr(rSet.getString("diaryDate"));
			diary.setDiaryCount(rSet.getInt("diaryCount"));
			diaryCountList.add(diary);
		}
		return diaryCountList;
	}
}
