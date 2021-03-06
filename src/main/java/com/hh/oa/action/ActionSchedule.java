package com.hh.oa.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.oa.bean.Schedule;
import com.hh.oa.service.impl.ScheduleService;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.service.impl.LoginUserUtilService;

@SuppressWarnings("serial")
public class ActionSchedule extends BaseServiceAction<Schedule> {

	private Date startDate;
	private Date endDate;

	private String currUserId;
	
	private String range;
	

	public BaseService<Schedule> getService() {
		return scheduleService;
	}

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Override
	public Object save() {
		this.object.setUserId(loginUserUtilService.findUserId());
		return super.save();
	}

	public Object queryListByDate() {
		String userId = loginUserUtilService.findUserId();
		if (Check.isNoEmpty(object.getProjectId())) {
			userId="";
		}
		
		if (Check.isNoEmpty(currUserId)) {
			userId = currUserId;
		}

		ParamInf paramInf = ParamFactory.getParamHb().ge("start", startDate).le("end", endDate);
		
		if (Check.isNoEmpty(userId)) {
			paramInf.is("userId", userId);
		}
		
		if (Check.isNoEmpty(object.getProjectId())) {
			paramInf.is("projectId", object.getProjectId());
		}
		
		if (Check.isNoEmpty(object.getProjectId())) {
			paramInf.is("projectId", object.getProjectId());
		}
		
		if ("day".equals(range)) {
			paramInf.is("type", 0);
		}else if ("stepDay".equals(range)) {
			paramInf.is("type", 1);
		}
		
		if (Check.isNoEmpty(object.getLevel()) && !"all".equals(object.getLevel())) {
			paramInf.is("level", object.getLevel());
		}
		
		List<Schedule> schedules = scheduleService
				.queryList(paramInf);
		return schedules;
	}

	public void ok() {
		this.getService().update(this.object.getId(), "isOk", this.object.getIsOk());
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCurrUserId() {
		return currUserId;
	}

	public void setCurrUserId(String currUserId) {
		this.currUserId = currUserId;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}
	
}
