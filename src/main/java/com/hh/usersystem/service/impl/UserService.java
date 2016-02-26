package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.date.DateFormat;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.model.ExtTree;
import com.hh.usersystem.bean.usersystem.UsSysGroup;
import com.hh.usersystem.bean.usersystem.UsGroup;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.bean.usersystem.UsRole;
//import com.hh.usersystem.bean.usersystem.HHXtZmsx;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.bean.usersystem.UsUserCyLxr;
import com.hh.usersystem.bean.usersystem.UsUserMenuZmtb;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;
import com.opensymphony.xwork2.ActionContext;

@Service
public class UserService extends BaseService<UsUser> {
	@Autowired
	private IHibernateDAO<UsUser> xtyhdao;

	@Autowired
	private IHibernateDAO<UsUserMenuZmtb> xtyhcdzmtb;

	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Autowired
	private IHibernateDAO<UsUserCyLxr> cylxrdao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserGroupService usGoupService;


	public Map<? extends String, ? extends Object> queryOnLinePagingData(UsUser hhXtYh, PageRange pageRange) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("items", LoginUser.getLoginUserList());
		resultMap.put("total", LoginUser.getLoginUserCount());
		return resultMap;
	}

	public void deleteOnLineByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		for (String string : idList) {
			LoginUser.loginUserSession.get(string).invalidate();
			LoginUser.remove(string);
		}
	}

	public PagingData<UsUser> queryPagingData(UsUser hhXtYh, PageRange pageRange, String ids, String orgs, String roles,
			String groups, String usgroups) {
		ParamInf hqlParamList = ParamFactory.getParamHb();
		if (!Check.isEmpty(hhXtYh.getText())) {
			hqlParamList.or(ParamFactory.getParamHb().like("text", hhXtYh.getText()),
					ParamFactory.getParamHb().like("textpinyin", hhXtYh.getText()));
		}

		if (hhXtYh.getNxb() != 2) {
			hqlParamList.add(Restrictions.eq("nxb", hhXtYh.getNxb()));
		}

		List<String> idList = Convert.strToList(ids);

		boolean param = false;

		if (Check.isNoEmpty(orgs)) {
			ParamInf hqlParamList2 = ParamFactory.getParamHb();
			hqlParamList2.is("orgId", orgs);
			ParamInf hqlParamList3 = ParamFactory.getParamHb();
			hqlParamList3.is("jobId", orgs);
			ParamInf hqlParamList4 = ParamFactory.getParamHb();
			hqlParamList4.is("deptId", orgs);
			ParamInf hqlParamList5 = ParamFactory.getParamHb();
			hqlParamList.or(hqlParamList5.or(hqlParamList2, hqlParamList3), hqlParamList4);
		}

		if (Check.isNoEmpty(roles)) {
			hqlParamList.like("roleIds", roles);
			// param = true;
			// idList.addAll(queryUserIdListByRole(roles));
		}

		if (Check.isNoEmpty(groups)) {
			param = true;
			idList.addAll(queryUserIdListByGroup(groups));
		}
		if (Check.isNoEmpty(usgroups)) {
			param = true;
			idList.addAll(queryUserIdListByUsGroup(usgroups));
		}

		List<String> idParamList = new ArrayList<String>();
		for (String id : idList) {
			if (!idParamList.contains(id)) {
				idParamList.add(id);
			}
		}
		if (idParamList.size() == 0 && param == true) {
			return new PagingData<UsUser>();
		}
		if (Check.isNoEmpty(idParamList)) {
			hqlParamList.add(Restrictions.in("id", idParamList));
		}
		// tiaojian(orgs, roles, users, groups, hqlParamList);

		// if (roleCriterion != null && orgCriterion != null) {
		// hqlParamList.add(Restrictions.or(roleCriterion, orgCriterion));
		// } else if (roleCriterion != null) {
		// hqlParamList.add(roleCriterion);
		// } else if (orgCriterion != null) {
		// hqlParamList.add(orgCriterion);
		// }
		return xtyhdao.queryPagingData(UsUser.class, hqlParamList, pageRange);
	}

	private void tiaojian(String orgs, String roles, String users, String groups, ParamInf hqlParamList) {
		List<Criterion> orCriterion = new ArrayList<Criterion>();

		if (Check.isNoEmpty(users)) {
			Criterion userCriterion = Restrictions.in("id", Convert.strToList(users));
			orCriterion.add(userCriterion);
		}

		if (!Check.isEmpty(roles)) {
			// List<String> roleIdList = Convert.strToList(roles);
			// DetachedCriteria ownerCriteria = DetachedCriteria
			// .forEntityName(UsUserRole.class.getName());
			// ownerCriteria.setProjection(Property.forName("yhId"));
			// ownerCriteria.add(Restrictions.in("jsId", roleIdList));
			// Criterion roleCriterion =
			// Property.forName("id").in(ownerCriteria);
			// orCriterion.add(roleCriterion);
		}

		if (orCriterion.size() > 1) {
			Criterion criterion = null;
			for (int i = 1; i < orCriterion.size(); i++) {
				if (criterion == null) {
					criterion = Restrictions.or(orCriterion.get(i - 1), orCriterion.get(i));
				} else {
					criterion = Restrictions.or(criterion, orCriterion.get(i));
				}
			}
			if (criterion != null) {
				hqlParamList.add(criterion);
			}
		} else if (orCriterion.size() == 1) {
			hqlParamList.add(orCriterion.get(0));
		}
	}

	public UsUser save(UsUser hhXtYh) throws MessageException {
		if (checkVdlzhOnly(hhXtYh)) {
			throw new MessageException("登陆账号已存在，请更换！" + hhXtYh.getVdlzh());
		}
		if (Check.isEmpty(hhXtYh.getId())) {
			// HHXtZmsx hhXtZmsx = new HHXtZmsx();
			// hhXtYh.setHhXtZmsx(hhXtZmsx);
			hhXtYh.setVmm("123456");
			hhXtYh.setId(UUID.randomUUID().toString());
			hhXtYh.setDesktopType("jquerydesktop");
			hhXtYh.setTheme("base");
			xtyhdao.createEntity(hhXtYh);
		} else {
			xtyhdao.updateEntity(hhXtYh);
		}
		return hhXtYh;
	}

	public boolean checkVdlzhOnly(UsUser hhXtYh) {
		return xtyhdao.findWhetherData("select count(o) from " + hhXtYh.getClass().getName() + " o "
				+ "where o.vdlzh=:vdlzh and (o.id!=:id or :id is null)", hhXtYh);
	}

	public UsUser findObjectById(String id) {
		UsUser hhXtYh = xtyhdao.findEntityByPK(UsUser.class, id);
		return hhXtYh;
	}

	public UsUser findObjectById_user(String id) {
		UsUser hhXtYh = xtyhdao.findEntityByPK(UsUser.class, id);
		return hhXtYh;
	}

	public void deleteByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		if (!Check.isEmpty(idList)) {
			xtyhdao.deleteEntity(UsUser.class, "id", idList);
			xtyhcdzmtb.deleteEntity(UsUserMenuZmtb.class, "yhId", idList);
			// zmsxdao.deleteEntity(HHXtZmsx.class, "id", idList);
			cylxrdao.deleteEntity(UsUserCyLxr.class, "yhId", idList);
		}

	}

	public void updatePassWord(UsUser hhXtYh, String oldPass) throws MessageException {
		hhXtYh.setId(loginUserUtilService.findUserId());
		boolean as = xtyhdao.isExist("select count(o) from HhXtYh o where o.id=? and o.vmm=?",
				new Object[] { hhXtYh.getId(), oldPass });
		if (!as) {
			throw new MessageException("旧密码错误！");
		} else {
			xtyhdao.updateEntity("update HhXtYh o set o.vmm=:vmm where o.id=:id", hhXtYh);
		}
	}

	public List<UsUser> queryCylxrs() {
		UsUser hhXtYh = loginUserUtilService.findLoginUser();
		List<UsUserCyLxr> hhXtYhCyLxrs = cylxrdao.queryList(UsUserCyLxr.class,
				ParamFactory.getParamHb().is("yhId", hhXtYh.getId()));

		List<String> cylxrIdList = new ArrayList<String>();
		for (UsUserCyLxr hhXtYhCyLxr : hhXtYhCyLxrs) {
			cylxrIdList.add(hhXtYhCyLxr.getCylxrId());
		}

		if (!Check.isEmpty(cylxrIdList)) {
			return xtyhdao.queryList(UsUser.class, ParamFactory.getParamHb().in("id", cylxrIdList));
		} else {
			return new ArrayList<UsUser>();
		}

	}

	public List<ExtTree> queryCylxrTree() {
		List<UsUser> hhXtYhs = this.queryCylxrs();
		List<ExtTree> extTrees = new ArrayList<ExtTree>();
		for (UsUser hhXtYh : hhXtYhs) {
			ExtTree extTree = new ExtTree();
			extTree.setId(hhXtYh.getId());
			extTree.setText(hhXtYh.getText());
			// extTree.setIcon(hhXtYh.getNxb() == 0 ?
			// StaticProperties.HHXT_USERSYSTEM_NV
			// : StaticProperties.HHXT_USERSYSTEM_NAN);
			if (LoginUser.getLoginUserId().contains(hhXtYh.getId())) {
				extTree.setIcon(hhXtYh.getNxb() == 0 ? StaticProperties.HHXT_USERSYSTEM_NV
						: StaticProperties.HHXT_USERSYSTEM_NAN);
			} else {
				extTree.setIcon(StaticProperties.HHXT_NO_ON_LINE_USER_ICON);
			}
			extTree.setLeaf(1);
			extTrees.add(extTree);
		}
		return extTrees;
	}

	public void addCylxr(String string) throws MessageException {
		boolean as = cylxrdao.isExist("select count(o) from HhXtYhCyLxr o where o.cylxrId = ?",
				new Object[] { string });
		if (as) {
			throw new MessageException("此人已经是您的常用联系人了！");
		} else {
			UsUser hhXtYh = loginUserUtilService.findLoginUser();
			UsUserCyLxr hhXtYhCyLxr = new UsUserCyLxr();
			hhXtYhCyLxr.setCylxrId(string);
			hhXtYhCyLxr.setYhId(hhXtYh.getId());
			cylxrdao.createEntity(hhXtYhCyLxr);
		}
	}

	public void deleteCylxr(String string) throws MessageException {
		UsUser hhXtYh = loginUserUtilService.findLoginUser();
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("yhId", hhXtYh.getId());
		paramsMap.put("cylxrId", string);
		cylxrdao.deleteEntity("delete HhXtYhCyLxr o where o.yhId=:yhId and o.cylxrId=:cylxrId", paramsMap);
	}

	public List<UsUser> queryListByIds(String[] users) {
		return xtyhdao.queryList(UsUser.class, ParamFactory.getParamHb().in("id", users));
	}

	public List<UsUser> queryItemsByIdsStr(String ids) {
		if (Check.isEmpty(ids)) {
			return null;
		}
		return xtyhdao.queryList(UsUser.class, ParamFactory.getParamHb().in("id", Convert.strToList(ids)));
	}

	public List<UsUser> queryUserByOrgId(String orgs) {
		ParamInf hqlParamList2 = ParamFactory.getParamHb();
		hqlParamList2.is("orgId", orgs);
		ParamInf hqlParamList3 = ParamFactory.getParamHb();
		hqlParamList3.is("jobId", orgs);
		ParamInf hqlParamList4 = ParamFactory.getParamHb();
		hqlParamList4.is("deptId", orgs);
		ParamInf hqlParamList5 = ParamFactory.getParamHb();

		List<UsUser> hhXtYhList = queryList(
				ParamFactory.getParamHb().or(hqlParamList5.or(hqlParamList2, hqlParamList3), hqlParamList4));
		return hhXtYhList;
	}

	public List<UsUser> queryUserByGroup(String groupId) {
		List<String> yhIdList = queryUserIdListByGroup(groupId);
		if (Check.isEmpty(yhIdList)) {
			return new ArrayList<UsUser>();
		}
		List<UsUser> hhXtYhList = xtyhdao.queryList(UsUser.class, Restrictions.in("id", yhIdList));
		return hhXtYhList;
	}
	
	public List<UsUser> queryUserByUsGroup(String groupId) {
		List<String> yhIdList = queryUserIdListByUsGroup(groupId);
		if (Check.isEmpty(yhIdList)) {
			return new ArrayList<UsUser>();
		}
		List<UsUser> hhXtYhList = xtyhdao.queryList(UsUser.class, Restrictions.in("id", yhIdList));
		return hhXtYhList;
	}
	
	private List<String> queryUserIdListByUsGroup(String groupId) {
		List<String> yhIdList = new ArrayList<String>();
		UsGroup hhXtGroup = usGoupService.findObjectById(groupId);
		if (hhXtGroup != null && Check.isNoEmpty(hhXtGroup.getUsers())) {
			yhIdList = Convert.strToList(hhXtGroup.getUsers());
		}
		return yhIdList;
	}

	private List<String> queryUserIdListByGroup(String groupId) {
		List<String> yhIdList = new ArrayList<String>();
		UsSysGroup hhXtGroup = groupService.findObjectById(groupId);
		if (hhXtGroup != null && Check.isNoEmpty(hhXtGroup.getUsers())) {
			yhIdList = Convert.strToList(hhXtGroup.getUsers());
		}
		return yhIdList;
	}

	public void updateZmbj(UsUser hhXtZmsx) {
		hhXtZmsx.setId(loginUserUtilService.findLoginUser().getId());
		dao.updateEntity("update " + UsUser.class.getName() + " o set o.vzmbj=:vzmbj where o.id=:id", hhXtZmsx);
	}

	public void updateDefaultOrg(String userId, String orgid) {
		dao.updateEntity("update " + UsUser.class.getName() + " o set o.defaultOrgId=? where o.id=?",
				new Object[] { orgid, userId });
	}

	public void updateTheme(UsUser hhXtZmsx) {
		UsUser hhXtYh = loginUserUtilService.findLoginUser();
		hhXtZmsx.setId(hhXtYh.getId());
		dao.updateEntity("update " + UsUser.class.getName() + " o set o.theme=:theme where o.id=:id", hhXtZmsx);
		hhXtYh.setTheme(hhXtZmsx.getTheme());
		ActionContext.getContext().getSession().put("loginuser", hhXtYh);
	}

	public void save(List<Map<String, Object>> mapList) throws MessageException {
		Map<String, String> orgMapNameId = new HashMap<String, String>();
		Map<String, String> roleMapNameId = new HashMap<String, String>();
		for (Map<String, Object> map : mapList) {

			String id = Convert.toString(map.get("标识"));
			String name = Convert.toString(map.get("名称"));
			String zh = Convert.toString(map.get("账号"));
			String lxdh = Convert.toString(map.get("联系电话"));
			String dzyj = Convert.toString(map.get("电子邮件"));

			String xbName = Convert.toString(map.get("性别"));
			int xb = 0;
			if ("男".equals(xbName)) {
				xb = 1;
			}
			int zt = 0;
			if ("禁用".equals(Convert.toString(map.get("状态")))) {
				zt = 1;
			}
			String srName = Convert.toString(map.get("生日"));
			Date sr = null;
			if (Check.isNoEmpty(srName)) {
				sr = DateFormat.strToDate(srName, "yyyy-MM-dd");
			}

			String jsName = Convert.toString(map.get("角色"));
			String jgName = Convert.toString(map.get("机构"));
			String bmName = Convert.toString(map.get("部门"));
			String gwName = Convert.toString(map.get("岗位"));

			String js = "";
			String jg = "";
			String bm = "";
			String gw = "";
			if (Check.isNoEmpty(jsName)) {
				if (roleMapNameId.keySet().contains(jsName)) {
					js = roleMapNameId.get(jsName);
				} else {
					String[] jsArr = jsName.split(",");
					List<UsRole> usRoles = roleService.queryListByProperty("text", Convert.arrayToList(jsArr));
					js = Convert.objectListToString(usRoles, "id");
					roleMapNameId.put(jsName, js);
				}
			}
			if (Check.isNoEmpty(jgName)) {
				if (orgMapNameId.keySet().contains(jgName)) {
					jg = orgMapNameId.get(jgName);
				} else {
					String[] jsArr = jgName.split(",");
					List<UsOrganization> usOrganizations = organizationService.queryListByProperty("text",
							Convert.arrayToList(jsArr));
					jg = Convert.objectListToString(usOrganizations, "id");
					orgMapNameId.put(jgName, jg);
				}
			}
			if (Check.isNoEmpty(bmName)) {
				String bmkey = jg + bmName;
				if (orgMapNameId.keySet().contains(bmkey)) {
					bm = orgMapNameId.get(bmkey);
				} else {
					String[] jsArr = bmName.split(",");
					List<UsOrganization> usOrganizations = organizationService
							.queryList(ParamFactory.getParamHb().in("text", Convert.arrayToList(jsArr)).is("node", jg));
					bm = Convert.objectListToString(usOrganizations, "id");
					orgMapNameId.put(bmkey, bm);
				}
			}
			if (Check.isNoEmpty(gwName)) {
				String gwkey = bm + gwName;
				if (orgMapNameId.keySet().contains(gwkey)) {
					gw = orgMapNameId.get(gwkey);
				} else {
					String[] jsArr = gwName.split(",");
					List<UsOrganization> usOrganizations = organizationService
							.queryList(ParamFactory.getParamHb().in("text", Convert.arrayToList(jsArr)).is("node", bm));
					gw = Convert.objectListToString(usOrganizations, "id");
					orgMapNameId.put(gwkey, gw);
				}
			}

			UsUser user = null;
			if (Check.isNoEmpty(id)) {
				user = findObjectById(id);
			} else {
				List<UsUser> users = queryList(ParamFactory.getParamHb().is("text", name));
				for (UsUser usUser : users) {
					user = usUser;
				}
			}
			if (user == null) {
				user = new UsUser();
			}
			user.setText(name);
			user.setVdlzh(zh);
			user.setVdh(lxdh);
			user.setVdzyj(dzyj);
			user.setNxb(xb);
			user.setState(zt);
			user.setDsr(sr);
			user.setRoleIds(js);
			user.setOrgId(jg);
			user.setDeptId(bm);
			user.setJobId(gw);
			save(user);
		}
	}

}
