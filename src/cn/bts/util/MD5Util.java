package cn.bts.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年4月20日
* @Description MD5加密
*/
public class MD5Util {
	
	public static String encoderPwdByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		MessageDigest md5=MessageDigest.getInstance("MD5");
		BASE64Encoder base64en=new BASE64Encoder();
		return base64en.encode(md5.digest(str.getBytes("utf-8")));
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		System.out.println(encoderPwdByMD5("1"));
		
	}
}
