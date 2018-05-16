package cn.bts.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年4月18日
* @Description 数据库连接
*/
public class DbUtil {
	
	private String dbUrl=PropertiesUtil.getValue("dbUrl");
	private String dbUserName=PropertiesUtil.getValue("dbUserName");
	private String dbPassword=PropertiesUtil.getValue("dbPassword");
	private String jdbcName=PropertiesUtil.getValue("jdbcName");
	
	public Connection getConnection() throws Exception{
		
		Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		return con;
	}
	
	public void CloseConnection(Connection con) throws Exception {
		
		if(con!=null) {
			con.close();
		}
	}
	
	public static void main(String[] args) {
		
		DbUtil dbUtil=new DbUtil();
		try {
			dbUtil.getConnection();
			/*System.out.println("数据库连接成功!");*/
		} catch (Exception e) {
			e.printStackTrace();
			/*System.out.println("数据库连接失败!");*/
		}
		
	}
}
