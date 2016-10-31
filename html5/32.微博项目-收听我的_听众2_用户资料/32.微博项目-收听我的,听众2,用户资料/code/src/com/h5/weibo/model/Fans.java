package com.h5.weibo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fans")
public class Fans {
	/**
	 * 没有关系
	 */
	public static int RELA_NO = 0;
	/**
	 * 已收听我，粉丝
	 */
	public static int RELA_FANS = 1;
	/**
	 * 我收听的
	 */
	public static int RELA_LISTEN = 2;
	/**
	 * 互相收听
	 */
	public static int RELA_BOTH = 3;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private long id;
	
	@Column(name="listen_id")
	private long listenId;

	@Column(name="fans_id")
	private long fansId;

	private long time;

	public long getId() {
		return id;
	}

	public long getFansId() {
		return fansId;
	}

	public long getTime() {
		return time;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFansId(long fansId) {
		this.fansId = fansId;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getListenId() {
		return listenId;
	}

	public void setListenId(long listenId) {
		this.listenId = listenId;
	}

}
