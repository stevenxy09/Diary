package cn.bts.model;

import java.io.Serializable;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年5月2日
* @Description 日志类别
*/
public class DiaryType implements Serializable {

	private static final long serialVersionUID = 7674402373828129439L;
	
	private int diaryTypeId;
	private String typeName;
	private int diaryCount;
	
	public DiaryType() {
		
	}
	
	public DiaryType(int diaryTypeId, String typeName) {
		
		this.diaryTypeId = diaryTypeId;
		this.typeName = typeName;
	}
	
	public DiaryType(String typeName) {
		
		this.typeName = typeName;
	}

	public int getDiaryTypeId() {
		return diaryTypeId;
	}

	public void setDiaryTypeId(int diaryTypeId) {
		this.diaryTypeId = diaryTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getDiaryCount() {
		return diaryCount;
	}

	public void setDiaryCount(int diaryCount) {
		this.diaryCount = diaryCount;
	}
	
}
