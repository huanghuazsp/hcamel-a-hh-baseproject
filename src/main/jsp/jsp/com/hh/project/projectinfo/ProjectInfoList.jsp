<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=SystemUtil.getBaseJs("workflow")%>

<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'project-ProjectInfo-deleteByIds'
		});
	}
	function doAdd() {
		Dialog.open({
			url : 'jsp-project-projectinfo-ProjectInfoEdit',
			params : {
				callback : function() {
					$("#pagelist").loadData();
				}
			}
		});
	}
	function doEdit() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-project-projectinfo-ProjectInfoEdit',
				urlParams : {
					id : row.id
				},
				params : {
					callback : function() {
						$("#pagelist").loadData();
					}
				}
			});
		});
	}
	function doQuery() {
		$('#pagelist').loadData({
			params : $('#queryForm').getValue()
		});
	}
	
	function renderMoney(value){
		return (value||0) + '万';
	}
	
	function setUserInfo(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-project-projectuserinfo-ProjectUserInfoList',
				urlParams : {
					projectId : row.id,
					oper : 'all'
				},
				params : {
					callback : function() {
					}
				}
			});
		});
	}
	
	function setModular(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-project-projectmodular-ProjectModularList',
				urlParams : {
					projectId : row.id,
					oper : 'all'
				},
				params : {
					callback : function() {
					}
				}
			});
		});
	}
	
	function setFile(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-project-projectfile-ProjectFileList',
				urlParams : {
					projectId : row.id,
					oper : 'all'
				},
				params : {
					callback : function() {
					}
				}
			});
		});
	}
	
	function doView(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-project-projectinfo-ProjectInfoView',
				urlParams : {
					id : row.id
				},
				params : {
					callback : function() {
					}
				}
			});
		});
	}
	
	function projectApproval(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			WF.start('X6XNgqvQbXfNeEtTYaM', row.text+'-立项',{object:row});
		});
	}
	function stageRender(value){
		if(value==1){
			return '项目实施';
		}else if(value==9){
			return '项目结项';
		}else{
			return '项目立项';
		}
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doAdd,text:'添加' , itype :'add' "></span>
		<span xtype="button" config="onClick:doEdit,text:'修改' , itype :'edit' "></span>
		<span xtype="button" config="onClick:doDelete,text:'删除' , itype :'delete' "></span>
		
	
		<span xtype=menu    config=" id:'menu1', data : [ 
		{ text : '编辑参与者' , onClick : setUserInfo } ,
		{ text : '编辑模块' , onClick : setModular },
		{ text : '编辑附件' , onClick : setFile } ,
		{ text : '项目立项' , onClick : projectApproval }]"></span>
		<span xtype="button" config=" text:'项目信息操作',icon : 'ui-icon-triangle-1-s' ,menuId:'menu1' "></span>
		<span
			xtype="button"
			config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'project-ProjectInfo-order'}  ,  icon : 'hh_up' "></span>
		<span xtype="button"
			config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'project-ProjectInfo-order'} , icon : 'hh_down' "></span>
	
		<span xtype="button" config="onClick:doView,text:'查看' , itype :'view' "></span>
	</div>
	<table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">名称：</td>
			<td><span xtype="text" config=" name : 'text'"></span></td>
			<td><span xtype="button"
						config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
		</tr>
	</table> 
	<div id="pagelist" xtype="pagelist"
		config=" url: 'project-ProjectInfo-queryPagingData' ,column : [
			{
				name : 'text' ,
				text : '项目名称'
			},
			{
				name : 'stage' ,
				text : '项目阶段',
				render : stageRender
			},
			{
				name : 'startDate' ,
				text : '开始日期',
				render : 'date',
				width:80
			},
		
			{
				name : 'managerText' ,
				text : '项目经理'
			},
		
			{
				name : 'client' ,
				text : '客户'
			},
		
			{
				name : 'money' ,
				text : '项目金额',
				width:100,
				render : renderMoney
			}
		
	]">
	</div>
</body>
</html>