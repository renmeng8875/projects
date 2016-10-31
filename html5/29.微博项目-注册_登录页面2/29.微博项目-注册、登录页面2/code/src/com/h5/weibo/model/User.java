package com.h5.weibo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="users")
public class User implements java.io.Serializable{

	private static final long serialVersionUID = -7365697533812324122L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private long id;
	
	@Basic(optional = false)
	@Column(name="user_name")
	private String userName;
	
	@Basic(optional = false)
	private String pwd;
	
	@Basic(optional = false)
	@Column(name="nick_name")
	private String nickName;
	
	private int sex;
	
	private String logo;
	
	@Column(name="birth_year")
	private int birthYear;
	
	@Column(name="birth_month")
	private int birthMonth;
	
	@Column(name="birth_day")
	private int birthDay;
	
	private String intro;

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPwd() {
		return pwd;
	}

	public String getNickName() {
		return nickName;
	}

	public int getSex() {
		return sex;
	}

	public String getLogo() {
		return logo;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public int getBirthMonth() {
		return birthMonth;
	}

	public int getBirthDay() {
		return birthDay;
	}

	public String getIntro() {
		return intro;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}

	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

}
