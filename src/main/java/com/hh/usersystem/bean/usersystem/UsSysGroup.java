package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseEntityTree;
import com.hh.hibernate.util.base.BaseEntity;

@Entity
@Table(name = "US_SYS_GROUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order(fields = "order", sorts = "asc")
public class UsSysGroup  extends BaseEntityTree<UsSysGroup> {
	private String remark;
	private String users;
	
	private String type="sysgroup";
	private int meGroup=0;
	@Column(length = 1024)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	@Lob
	@Column(name="USERS")
	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	@Transient
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Transient
	public int getMeGroup() {
		return meGroup;
	}
	public void setMeGroup(int meGroup) {
		this.meGroup = meGroup;
	}
	
}
