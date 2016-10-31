package com.h5.weibo.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

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
	
	@Transient
	private String timeStr=null;
	
	private static SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
	public String getTimeStr() {
		if(timeStr == null) {
			int now = (int)(System.currentTimeMillis() / 1000);
			int inteval = now - sendTime; 
			if(inteval < 60) {	
				// 小于1分钟
				timeStr = "刚刚";
			}else if(inteval < 60*60) {
				// 小于一小时
				timeStr = (inteval / 60) + "分钟前";
			}else if(inteval < 60*60*24) {
				// 小于24小时
				timeStr = (inteval / (60*60)) + "小时前";
			}else {
				// 日期
				Long lTime = Long.valueOf(String.valueOf(sendTime))*1000;
				Date d = new Date(lTime);
				timeStr = df.format(d);
			}
		}
		return timeStr;
	}

	@Column(name="forward_id")
	private long forwardId;

	@Transient
	private Weibo forwardWibo;
	
	
	
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

	public Weibo getForwardWibo() {
		return forwardWibo;
	}

	public void setForwardWibo(Weibo forwardWibo) {
		this.forwardWibo = forwardWibo;
	}
}
