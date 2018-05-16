package cn.bts.util;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年4月27日
* @Description 字符串判空和非空
*/
public class StringUtil {

	public static boolean isEmpty(String str){
		if("".equals(str)|| str==null){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNotEmpty(String str){
		if(!"".equals(str)&&str!=null){
			return true;
		}else{
			return false;
		}
	}
	
	
}
