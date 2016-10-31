package com.h5.weibo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="weibos")
public class Weibo implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private long id;
	
	@Basic(optional = false)
	@Column(name="writer_id")
	private long writerId;
	
	@Basic(optional = false)
	private String content;
	
	private String img;
	
	@Column(name="send_time")
	private int sendTime;
	
	@Column(name="forward_id")
	private long forwardId;

	public long getId() {
		return id;
	}

	public long getWriterId() {
		return writerId;
	}

	public String getContent() {
		return content;
	}

	public String getImg() {
		return img;
	}

	public int getSendTime() {
		return sendTime;
	}

	public long getForwardId() {
		return forwardId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setWriterId(long writerId) {
		this.writerId = writerId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setSendTime(int sendTime) {
		this.sendTime = sendTime;
	}

	public void setForwardId(long forwardId) {
		this.forwardId = forwardId;
	}
}
