package cn.bts.model;

import java.io.Serializable;

/**
* @author stevenxy E-mail:random_xy@163.com
* @Date 2018年4月18日
* @Description 用户信息
*/
public class User implements Serializable {

	private static final long serialVersionUID = 1881986969741792483L;
	
	private int id;
	private String userName;
	private String password;
	private String nickName;
	private String imageName;
	private String mood;
	
	public User() {
		
	}
	
	public User(int id, String userName, String password, String nickName, String imageName, String mood) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
		this.imageName = imageName;
		this.mood = mood;
	}
	
	
	
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", nickName=" + nickName
				+ ", imageName=" + imageName + ", mood=" + mood + "]";
	}
	
}
