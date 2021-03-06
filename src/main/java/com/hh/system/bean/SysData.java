package com.hh.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseEntityTree;

@Entity(name = "SYS_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order(fields = "order", sorts = "asc")
public class SysData extends BaseEntityTree {
	private String dataTypeId;

	@Column(name = "DATA_TYPE_ID", length = 36)
	public String getDataTypeId() {
		return dataTypeId;
	}

	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
	}
	
	private String code;

	@Column(name = "CODE_", length = 1024)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
