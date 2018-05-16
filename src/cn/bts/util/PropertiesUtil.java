package cn.bts.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年4月20日
* @Description 读取properties
*/
public class PropertiesUtil {
	
	public static String getValue(String key) {
		
		Properties prop=new Properties();
		InputStream in=new PropertiesUtil().getClass().getResourceAsStream("/diary.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (String)prop.get(key);
	}
}
