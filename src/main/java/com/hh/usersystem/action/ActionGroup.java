package com.hh.usersystem.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.ParamFactory;
import com.hh.usersystem.LoginUserServiceInf;
import com.hh.usersystem.bean.usersystem.UsSysGroup;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.bean.usersystem.UsGroup;
import com.hh.usersystem.service.impl.GroupService;
import com.hh.usersystem.service.impl.UserGroupService;
import com.hh.usersystem.service.impl.UserService;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;

@SuppressWarnings("serial")
public class ActionGroup extends BaseServiceAction<UsSysGroup> {

	private String groups;
	private int andUser = 1;
	@Autowired
	private GroupService service;

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private UserService userService;

	@Autowired
	private LoginUserServiceInf loginUserService;

	public BaseService<UsSysGroup> getService() {
		return service;
	}

	public Object queryPagingData() {
		return service.queryPagingData(object, groups, this.getPageRange());
	}

	public Object queryListAndUserGroup() {
		String userId = loginUserService.findUserId();
		List<Object> returnObjects = new ArrayList<Object>();
		if ("root".equals(this.object.getNode())) {
			UsGroup userGroup = new UsGroup();
			userGroup.setText("自定义组");
			userGroup.setExpanded(1);
			userGroup.setIcon("/hhcommon/images/myimage/org/dept.png");
			userGroup.setType("zdy");
			List<UsGroup> userGroups = userGroupService.queryTreeList(
					object.getNode(),
					ParamFactory.getParamHb().or(
							ParamFactory.getParamHb().is("createUser", userId)
									.like("users", userId)));
			if (andUser == 1) {
				for (UsGroup userGroup2 : userGroups) {
					addUserGroup(userGroup2);
				}
			}

			userGroup.setChildren(userGroups);
			returnObjects.add(userGroup);
		}
		List<UsSysGroup> groupList = service.queryTreeList(this.object
				.getNode());
		if (andUser == 1) {
			for (UsSysGroup hhXtGroup : groupList) {
				addXtGroup(hhXtGroup);
			}
		}
		returnObjects.addAll(groupList);
		return returnObjects;
	}

	private void addXtGroup(UsSysGroup userGroup) {
		String userId = loginUserService.findUserId();
		if (userGroup.getChildren() != null) {
			for (UsSysGroup userGroup2 : userGroup.getChildren()) {
				addXtGroup(userGroup2);
			}
		}
		if (Check.isNoEmpty(userGroup.getUsers())) {
			if (userGroup.getUsers().indexOf(userId) > -1) {
				userGroup.setMeGroup(1);
			}
			List<String> userIds = Convert.strToList(userGroup.getUsers());
			List<UsUser> hhXtYhs = userService.queryListByIds(userIds);
			for (UsUser hhXtYh : hhXtYhs) {
				UsSysGroup extTree = new UsSysGroup();
				extTree.setId(hhXtYh.getId());
				extTree.setText(hhXtYh.getText());
				extTree.setType("user");
				if (LoginUser.getLoginUserId().contains(hhXtYh.getId())) {
					extTree.setIcon(hhXtYh.getNxb() == 0 ? StaticProperties.HHXT_USERSYSTEM_NV
							: StaticProperties.HHXT_USERSYSTEM_NAN);
				} else {
					extTree.setIcon(StaticProperties.HHXT_NO_ON_LINE_USER_ICON);
				}
				extTree.setLeaf(1);
				if (userGroup.getChildren() == null) {
					userGroup.setChildren(new ArrayList<UsSysGroup>());
				}
				userGroup.getChildren().add(extTree);
			}
		}
	}

	private void addUserGroup(UsGroup userGroup) {
		String userId = loginUserService.findUserId();
		if (userGroup.getChildren() != null) {
			for (UsGroup userGroup2 : userGroup.getChildren()) {
				addUserGroup(userGroup2);
			}
		}
		if (Check.isNoEmpty(userGroup.getUsers())) {
			userGroup.setChildren(new ArrayList<UsGroup>());
			if (userGroup.getUsers().indexOf(userId) > -1) {
				userGroup.setMeGroup(1);
			}
			List<String> userIds = Convert.strToList(userGroup.getUsers());
			List<UsUser> hhXtYhs = userService.queryListByIds(userIds);
			for (UsUser hhXtYh : hhXtYhs) {
				UsGroup extTree = new UsGroup();
				extTree.setId(hhXtYh.getId());
				extTree.setText(hhXtYh.getText());
				extTree.setType("user");
				if (LoginUser.getLoginUserId().contains(hhXtYh.getId())) {
					extTree.setIcon(hhXtYh.getNxb() == 0 ? StaticProperties.HHXT_USERSYSTEM_NV
							: StaticProperties.HHXT_USERSYSTEM_NAN);
				} else {
					extTree.setIcon(StaticProperties.HHXT_NO_ON_LINE_USER_ICON);
				}
				extTree.setLeaf(1);
				userGroup.getChildren().add(extTree);
			}
		}
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public int getAndUser() {
		return andUser;
	}

	public void setAndUser(int andUser) {
		this.andUser = andUser;
	}

}
