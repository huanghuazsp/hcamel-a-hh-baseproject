<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>菜单管理</title>
<%=SystemUtil.getBaseJs("layout", "ztree", "ztree_edit")%>
<script type="text/javascript">
	function doAdd() {
		var selectNode = $.hh.tree.getSelectNode('menuTree');
		Dialog.open({
			url : 'jsp-usersystem-menu-menuedit',
			params : {
				selectNode : selectNode,
				callback : function() {
					$.hh.tree.refresh('menuTree');
				}
			}
		});
	}
	function doEdit(treeNode) {
		Dialog.open({
			url : 'jsp-usersystem-menu-menuedit',
			params : {
				object : treeNode,
				callback : function() {
					$.hh.tree.refresh('menuTree');
				}
			}
		});
	}
	function doDelete(treeNode) {
		$.hh.tree.deleteData({
			pageid : 'menuTree',
			action : 'usersystem-menu-deleteByIds',
			id : treeNode.id,
			callback:function(){
				$.hh.tree.refresh('cztreespan');
			}
		});
	}

	var cztreeconfig = {
		id : 'cztree',
		url : 'usersystem-operate-queryOperateListByPid',
		remove : doDeleteOper,
		edit : doEditOper
	}

	var selectMenuNode = null;

	function doLoadOper() {
		var selectNode = $.hh.tree.getSelectNode('menuTree') || {};
		selectMenuNode = selectNode;
		$('#operspan').html('（'+selectMenuNode.text+'）');
		var menuId = selectNode.id;
		
		$.hh.tree.loadData('cztree', {
			params : {
				menuId : menuId
			}
		});
	}

	function doAddOper() {
		Dialog.open({
			url : 'jsp-usersystem-menu-operedit',
			params : {
				selectMenuNode : selectMenuNode,
				callback : function() {
					$.hh.tree.refresh('cztree');
				}
			}
		});
	}
	function doEditOper(treeNode) {
		Dialog.open({
			url : 'jsp-usersystem-menu-operedit',
			params : {
				object : treeNode,
				selectMenuNode : selectMenuNode,
				callback : function() {
					$.hh.tree.refresh('cztree');
				}
			}
		});
	}
	function doDeleteOper(treeNode) {
		$.hh.tree.deleteData({
			pageid : 'cztree',
			action : 'usersystem-operate-deleteByIds',
			id : treeNode.id
		});
	}
	function querytree(){
		$('#span_menuTree').loadData({
			params : {text:$('#span_treeText').getValue()}
		});
	}

	function init() {
	}
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west' ,width:'50%'">
			<div xtype="toolbar" config="type:'head'">
			
				<table style="font-size: 12" width=100%  cellspacing="0" cellpadding="0" ><tr>
				<td align=center >菜单管理
				</td>
				<td >
				
				<span xtype="button" config="onClick: doAdd ,text:'添加'"></span>
				<span xtype="button"
					config="onClick: $.hh.tree.doUp , params:{treeid:'menuTree',action:'usersystem-menu-order'}  , textHidden : true,text:'上移' ,icon : 'hh_up' "></span>
				<span xtype="button"
					config="onClick: $.hh.tree.doDown , params:{treeid:'menuTree',action:'usersystem-menu-order'} , textHidden : true,text:'下移' ,icon : 'hh_down' "></span>
				</td>
				<td width="300px" >
				<table style="font-size: 12" width=100%  cellspacing="0" cellpadding="0" ><tr>
				<td >
				<span xtype="text" config=" name : 'treeText' ,enter: querytree"></span>
				</td>
				<td width="40px" style="text-align:right;">
				<span xtype="button" config=" icon :'hh_img_query' , onClick : querytree "></span>
				</td><tr></table>
				</td></tr></table>
			</div>
			<span xtype="tree"
				config=" id:'menuTree' , url:'usersystem-menu-queryList' ,remove:doDelete , edit : doEdit,onClick : doLoadOper "></span>
		</div>
		<div id="czTreeDiv">
			<div xtype="toolbar" config="type:'head'">
			操作管理（页面按钮）
				<span xtype="button" config="onClick: doAddOper ,text:'添加' "></span>
				&nbsp;&nbsp;<span id="operspan" style="color:red;" ></span>
			</div>
			<span id="cztreespan" xtype="tree" configVar="cztreeconfig"></span>
		</div>
	</div>
</body>
</html>