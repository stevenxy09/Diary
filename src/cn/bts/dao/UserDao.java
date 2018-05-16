package cn.bts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.bts.model.User;
import cn.bts.util.MD5Util;
import cn.bts.util.PropertiesUtil;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年4月18日
* @Description 用户
*/
public class UserDao {
	
	/**
	 * 用户登录
	 * @param con
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User login(Connection con,User user) throws Exception{
		
		User resultUser=null;
		String sql="select * from t_user where userName=? and password=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, user.getUserName());
		ps.setString(2, MD5Util.encoderPwdByMD5(user.getPassword()));
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			resultUser=new User();
			resultUser.setId(rs.getInt("id"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setNickName(rs.getString("nickName"));
			resultUser.setImageName(PropertiesUtil.getValue("imageFile")+rs.getString("imageName"));
			resultUser.setMood(rs.getString("mood"));
		}
		return resultUser;
	}
	
	public int userUpdate(Connection connection,User user)throws Exception{
		
		String sql="update t_user set nickName=?,imageName=?,mood=? where id=?";
		PreparedStatement ps=connection.prepareStatement(sql);
		ps.setString(1, user.getNickName());
		ps.setString(2, user.getImageName());
		ps.setString(3, user.getMood());
		ps.setInt(4, user.getId());
		return ps.executeUpdate();
	}
	
}
