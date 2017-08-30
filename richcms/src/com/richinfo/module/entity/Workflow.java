package com.richinfo.module.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.richinfo.common.entity.BaseEntity;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="first")
@Table(name = "MM_SYS_WORKFLOW")
public class Workflow extends BaseEntity
{
	private static final long serialVersionUID = 6304433767085603092L;
 
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "flowid", nullable = false)
	private int flowid;
	
	@Column(name="workflow",length=50)
	private String workflow;
	
	@Column(name="mem",length=200)
	private String mem;
	
	@Column(name="flowlevel", nullable = false)
	private int flowlevel;
	
	@Override
	public String[] getExcludesAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getFlowid() {
		return flowid;
	}

	public void setFlowid(int flowid) {
		this.flowid = flowid;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

	public int getFlowlevel() {
		return flowlevel;
	}

	public void setFlowlevel(int flowlevel) {
		this.flowlevel = flowlevel;
	}
	
	
	
}
