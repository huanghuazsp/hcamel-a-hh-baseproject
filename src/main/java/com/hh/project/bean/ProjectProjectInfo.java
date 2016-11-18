 package com.hh.project.bean;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Lob;
import com.hh.hibernate.util.base.*;
import com.hh.hibernate.dao.inf.Order;
@Order
@SuppressWarnings("serial")
@Entity
@Table(name="PROJECT_PROJECT_INFO")
public class ProjectProjectInfo  extends BaseTwoEntity{
	//项目名称
	private String text;
	
	@Column(name="TEXT", length = 128)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	//项目经理
	private String manager;
	
	@Column(name="MANAGER", length = 36)
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	
	//项目经理名称
	private String managerText;
	
	@Column(name="MANAGER_TEXT", length = 64)
	public String getManagerText() {
		return managerText;
	}
	public void setManagerText(String managerText) {
		this.managerText = managerText;
	}
	
	//客户
	private String client;
	
	@Column(name="CLIENT", length = 256)
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	
	//项目金额
	private Long money;
	
	@Column(name="MONEY", length = 16)
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
	
	//描述
	private String describe;
	
	@Lob
	@Column(name="DESCRIBE_")
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	//开始日期
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="START_DATE",length = 7)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	//计划结束日期
	private Date planEndDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PLAN_END_DATE",length = 7)
	public Date getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}
	
	//结束日期
	private Date endDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="END_DATE",length = 7)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}