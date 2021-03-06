package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Convert;
import com.hh.system.util.StaticVar;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.pk.PrimaryKey;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.bean.usersystem.UsUserMenuZmtb;
import com.opensymphony.xwork2.ActionContext;

@Service
public class ZmtbService extends BaseService<UsUserMenuZmtb> {
	@Autowired
	private LoginUserUtilService loginUserService;
	@Autowired
	private MenuService menuService;

	public void updateOrder(String id1, String order1, String id2, String order2) {
		UsUserMenuZmtb entity1 = dao.findEntity(UsUserMenuZmtb.class, Restrictions.eq("cdId", id1));
		long order1_ = entity1.getOrder();
		UsUserMenuZmtb entity2 = dao.findEntity(UsUserMenuZmtb.class, Restrictions.eq("cdId", id2));
		long order2_ = entity2.getOrder();
		dao.updateEntity("update " + UsUserMenuZmtb.class.getName() + " o set o.order=? where o.cdId=?",
				new Object[] { order1_, id2 });
		dao.updateEntity("update " + UsUserMenuZmtb.class.getName() + " o set o.order=? where o.cdId=?",
				new Object[] { order2_, id1 });
	}

	public List<SysMenu> queryZmtbByUserId(String userId,List<String> menuList) {
		List<UsUserMenuZmtb> desktopQuickList = dao.queryList(UsUserMenuZmtb.class,
				ParamFactory.getParamHb().is("yhId", userId));
		return menuService.queryListByZmtb(desktopQuickList,menuList);
	}
	
	

	@Transactional
	public void orderIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		String userid = loginUserService.findUserId();
		deleteByProperty("yhId", userid);
		for (int i = idList.size() - 1; i > -1; i--) {
			UsUserMenuZmtb hhXtYhCdZmtb = new UsUserMenuZmtb();
//			SysMenu hhXtCd = menuService.findObjectById2(idList.get(i));
			hhXtYhCdZmtb.setCdId(idList.get(i));
			hhXtYhCdZmtb.setOrder(PrimaryKey.getTime());
			hhXtYhCdZmtb.setYhId(userid);
			dao.createEntity(hhXtYhCdZmtb);
		}
		
		menuService.updateSessionZmtb();
	}

}