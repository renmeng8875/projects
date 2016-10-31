package com.h5.weibo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "refer_weibo")
public class ReferWeibo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private long id;

	@Basic(optional = false)
	@Column(name="refer_id")
	private long referId;

	@Basic(optional = false)
	@Column(name="weibo_id")
	private long weiboId;

	public long getId() {
		return id;
	}

	public long getReferId() {
		return referId;
	}

	public long getWeiboId() {
		return weiboId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setReferId(long referId) {
		this.referId = referId;
	}

	public void setWeiboId(long weiboId) {
		this.weiboId = weiboId;
	}

}
