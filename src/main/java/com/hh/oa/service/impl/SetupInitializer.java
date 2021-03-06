package com.hh.oa.service.impl;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.message.service.EmailService;
import com.hh.message.service.SysMessageService;
import com.hh.system.service.impl.SysParamService;
import com.hh.system.service.impl.SystemResourcesService;
import com.hh.system.util.StaticVar;
import com.hh.system.util.pk.PrimaryKey;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.bean.usersystem.SysOper;
import com.hh.usersystem.service.impl.UserService;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class SetupInitializer {

	@Autowired
	private EmailService sysShouEmailService;
	@Autowired
	private SysMessageService sysMessageService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysParamService sysParamService;
	@Autowired
	private SystemResourcesService systemResourcesService;
	
	@Autowired
	private OaNoticeService oaNoticeService;

	@PostConstruct
	public void initialize() {
		SysMenu rootHhXtCd = new SysMenu("YKfDaj9fJWf16D4ovWH", "协同办公",
				"com.hh.global.NavigAtionWindow",
				"/hhcommon/images/extjsico/17460359.png", 1, 0);
		rootHhXtCd.setChildren(new ArrayList<SysMenu>());
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("63467b5b-ce83-4c56-a3e4-70e9e01739d5", "日程安排",
		// "jsp/calendar.jsp",
		// "/hhcommon/images/extjsico/17460310.png", 0, 1));

		// HhXtCd rootMailHhXtCd = new HhXtCd(
		// "a4ebc255-2045-4bb9-ace0-dabbb3e72b64", "邮箱",
		// "com.hh.global.NavigAtionWindow",
		// "/hhcommon/images/icons/email/email.png", 1, 0);
		// rootMailHhXtCd.setChildren(new ArrayList<HhXtCd>());
		// rootMailHhXtCd.getChildren().add(
		// new HhXtCd("93bb64fe-e50a-40b2-ab59-b1ae543cd107", "收件箱",
		// "jsp-message-email-shouemaillist",
		// "/hhcommon/images/icons/email/email.png", 0, 1));
		// rootMailHhXtCd.getChildren().add(
		// new HhXtCd("bb84e514-2f94-4b02-96fe-9af208166f11", "发件箱",
		// "jsp-message-email-sendemaillist",
		// "/hhcommon/images/icons/email/email_edit.png", 0, 1));

		SysMenu rootMailHhXtCd = new SysMenu("Mj38uTS5EDGou2cOCY4", "个人邮件",
				"jsp-message-email-emailmain",
				"/hhcommon/images/icons/email/email.png", 0, 1);
		
		rootMailHhXtCd.setVdtp("/hhcommon/images/big/apple/email.png");

		rootHhXtCd.getChildren().add(rootMailHhXtCd);
		
		
		

		rootHhXtCd.getChildren().add(
				new SysMenu("7fmZpjkk034UGEL6K7W", "计划总结",
						"jsp-oa-schedule-fullcalendar","/hhcommon/images/big/apple/3.png",
						"/hhcommon/images/extjsico/17460310.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new SysMenu("5P4L5383QPwpsjwvjN0", "文件中心",
						"jsp-system-resources-resourcesmain","/hhcommon/images/big/apple/28.png",
						"/hhcommon/images/icons/folder/folder.png", 0, 1));

		SysMenu notice = new SysMenu("QoGVp009MF5vDM5qCQs", "公告通知",
				"jsp-oa-notice-noticemain","/hhcommon/images/big/apple/2.png",
				"/hhcommon/images/icons/newspaper/newspaper.png", 0, 1);
		rootHhXtCd.getChildren().add(notice);
		
		StaticProperties.sysOperList.add(new SysOper("ZV29BtmO0mtRQf3rM9V",
				"发布公告", notice));
		StaticProperties.sysOperList.add(new SysOper("i5XcfE1mIpAvTxRgSkB",
				"我发布的", notice));
		StaticProperties.sysOperList.add(new SysOper("eQBBKL23YIku7EmYequ",
				"所有公告", notice));
		
		
		SysMenu meeting = new SysMenu("id04ZkRCOJ78aUzjwW", "会议管理",
				"jsp-oa-meeting-meetingmain","/hhcommon/images/big/apple/29.png",
				"/hhcommon/images/icons/telephone/telephone.png", 0, 1);
		
		StaticProperties.sysOperList.add(new SysOper("lzl2ky7M7RRiyO9D3uJ",
				"会议室管理", meeting));
		rootHhXtCd.getChildren().add(meeting);
		
		
		
		SysMenu project = new SysMenu("dL8F5prALiQFtgnnH4K", "项目管理",
				"jsp-project-projectinfo-projectmain","/hhcommon/images/big/apple/0.png",
				"/hhcommon/images/icons/project/project.png", 0, 1);
		
		rootHhXtCd.getChildren().add(project);
		

		StaticProperties.sysMenuList.add(rootHhXtCd);
		
		
		
		
		
		
		
		StaticProperties.addData("公告", "", "类别", "", "", "");
		StaticProperties.addData("公告类型", "公告", "类别", "", "", "");
		
		StaticProperties.addData("决定", "", "字典", "公告类型", "1", "");
		StaticProperties.addData("通知", "", "字典", "公告类型", "2", "");
		StaticProperties.addData("通报", "", "字典", "公告类型", "3", "");
		StaticProperties.addData("人事", "", "字典", "公告类型", "4", "");
		StaticProperties.addData("行政", "", "字典", "公告类型", "5", "");
		StaticProperties.addData("其他", "", "字典", "公告类型", "255", "");
		
		
		
		StaticProperties.addData("项目管理", "", "类别", "", "", "");
		StaticProperties.addData("项目管理参与者角色", "项目管理", "类别", "", "", "");
		
		StaticProperties.addData("项目负责人", "", "字典", "项目管理参与者角色", "1", "");
		StaticProperties.addData("研发负责人", "", "字典", "项目管理参与者角色", "2", "");
		StaticProperties.addData("研发工程师", "", "字典", "项目管理参与者角色", "3", "");
		
		StaticProperties.addData("技术负责人", "", "字典", "项目管理参与者角色", "4", "");
		StaticProperties.addData("技术工程师", "", "字典", "项目管理参与者角色", "5", "");
		
		StaticProperties.addData("测试负责人", "", "字典", "项目管理参与者角色", "6", "");
		StaticProperties.addData("测试工程师", "", "字典", "项目管理参与者角色", "7", "");
		
		
		StaticProperties.addData("附件类型", "项目管理", "类别", "", "", "");
		
		StaticProperties.addData("需求文档", "", "字典", "附件类型", "1", "");
		StaticProperties.addData("设计文档", "", "字典", "附件类型", "2", "");
		StaticProperties.addData("帮助文档", "", "字典", "附件类型", "3", "");
		
		
		
		
		
		
		
		
		StaticVar.loadDataTimeMap.put("jsp-message-email-emailmain",
				sysShouEmailService);
		
		StaticVar.loadDataTimeMap.put("jsp-oa-notice-noticemain",
				oaNoticeService);
		
		StaticVar.loadDataTimeMap.put("message", sysMessageService);

		StaticVar.fileOperMap.put("email", sysShouEmailService);
		StaticVar.fileOperMap.put("headpic", userService);
		StaticVar.fileOperMap.put("param", sysParamService);
		StaticVar.fileOperMap.put("sysresources", systemResourcesService);

	}

	public static void main(String[] args) {
		System.out.println(PrimaryKey.getUUID());
	}
}