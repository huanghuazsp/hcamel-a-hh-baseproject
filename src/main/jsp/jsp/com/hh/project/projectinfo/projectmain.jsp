<%@page import="com.hh.oa.service.impl.OaNoticeService"%>
<%@page import="com.hh.message.service.EmailService"%>
<%@page import="com.hh.system.service.impl.BeanFactoryHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>项目管理</title>
<%=SystemUtil.getBaseJs("layout")%>
<% 

%>
<script type="text/javascript">
	var emailMenu = {
		data : [{
			text : '创建项目',
			img : '/hhcommon/images/icons/project/project-plus.png',
			onClick : function() {
				$('#iframe').attr('src','jsp-oa-notice-NoticeEdit');
			}
		}, {
			text : '我参与的',
			img : '/hhcommon/images/icons/project/project-horizontal.png',
			onClick : function() {
				wfbd();
			}
		}, {
			text : '我的项目',
			img : '/hhcommon/images/icons/project/project.png',
			onClick : function() {
				wfbd();
			}
		}
		]
	};
	
	function wfbd(){
		$('#iframe').attr('src','jsp-oa-notice-NoticeList?type=wfbd');
	}
	
	function updateGG(id){
		$('#iframe').attr('src','jsp-oa-notice-NoticeEdit?id='+id);
	}
	
	
	<%
		String baseurl = "jsp-oa-notice-NoticeList?type=ggtz";
	%>
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west',width:165">
			<span xtype=menu  configVar="emailMenu"></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="iframe" name="iframe" width=100%
				height=100% frameborder=0 src="<%=baseurl%>"></iframe>
		</div>
	</div>
</body>
</html>