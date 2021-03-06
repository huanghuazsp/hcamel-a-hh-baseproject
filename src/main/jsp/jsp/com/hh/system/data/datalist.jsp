<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<%=SystemUtil.getBaseJs("layout", "ztree", "ztree_edit")%>
<script type="text/javascript">
	var typeTreeObject = null;
	var dataTreeObject = null;

	var selectTypeNode = null;

	function init() {
		typeTreeObject = $('#typeTreeSpan').getWidget();
		dataTreeObject = $('#dataTreeSpan').getWidget();
		$("#datacenter").disabled('请选择字典类别！');
	}

	function addType() {
		var selectNode = typeTreeObject.getSelectNode();
		Dialog.open({
			url : 'jsp-system-data-datatypeedit',
			params : {
				selectNode : selectNode,
				callback : function() {
					typeTreeObject.refresh();
				}
			}
		});
	}
	function editType(treeNode) {
		Dialog.open({
			url : 'jsp-system-data-datatypeedit',
			params : {
				treeNode : treeNode,
				callback : function() {
					typeTreeObject.refresh();
				}
			}
		});
	}
	function removeType(treeNode) {
		$.hh.tree.deleteData({
			pageid : 'typeTree',
			action : 'system-SysDataType-deleteTreeByIds',
			id : treeNode.id
		});
	}

	function typeTreeClick( treeNode) {
			selectTypeNode = treeNode;
			$('#msgspan').html(selectTypeNode.text);
			$("#datacenter").undisabled();
			$.hh.tree.loadData('dataTree', {
				params : {
					dataTypeId : treeNode.code
				}
			});
	}

	function addData() {
		var selectNode = dataTreeObject.getSelectNode();
		Dialog.open({
			url : 'jsp-system-data-dataedit',
			params : {
				selectNode : selectNode,
				selectTypeNode : selectTypeNode,
				callback : function() {
					dataTreeObject.refresh();
				}
			}
		});
	}

	function editData(treeNode) {
		Dialog.open({
			url : 'jsp-system-data-dataedit',
			params : {
				treeNode : treeNode,
				selectTypeNode : selectTypeNode,
				callback : function() {
					dataTreeObject.refresh();
				}
			}
		});
	}
	function removeData(treeNode) {
		$.hh.tree.deleteData({
			pageid : 'dataTree',
			action : 'system-SysData-deleteTreeByIds',
			id : treeNode.id
		});
	}
	
	function inExcel(){
		Dialog.open({
			url : 'jsp-system-tools-file',
			width : 450,
			height : 270,
			params : {
				saveUrl : 'system-SysDataType-importData',
				type : 'temp',
				callback : function(data) {
					if(data.returnModel && data.returnModel.msg){
						Dialog.alert(data.returnModel.msg);
					}else{
						typeTreeObject.refresh();
					}
				}
			}
		});
	}
	
	function downloadExcel(){
		Request.submit('system-File-downloadFile',{path:'temp/数据字典.xls'});
	}
	function outExcel(){
		Request.submit('system-SysDataType-download',{});
	}
	function querytree(){
		$('#typeTreeSpan').loadData({
			params : {text:$('#span_treeText').getValue()}
		});
	}
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west' ,width:'50%' ">
			<div xtype="toolbar" config="type:'head'">
				
				<table  style="font-size: 12" width=100%  cellspacing="0" cellpadding="0" ><tr>
				<td align=center >字典类型
				</td>
				<td >
				<span xtype="button" config="onClick:addType,text:'添加'"></span>
				<span xtype="button"
					config="onClick : inExcel ,text : '导入'"></span>
				<span xtype="button"
					config="onClick : outExcel ,text : '导出'"></span>
				<span xtype="button"
					config="onClick : downloadExcel ,text : '下载模板'"></span>
				</td>
				<td >
				<table style="font-size: 12" width=100%  cellspacing="0" cellpadding="0" ><tr>
				<td >
				<span xtype="text" config=" name : 'treeText'  ,enter: querytree"></span>
				</td>
				<td width="40px" style="text-align:right;">
				<span xtype="button" config=" icon :'hh_img_query' , onClick : querytree "></span>
				</td><tr></table>
				
				</td><tr></table>
			</div>
			<span id="typeTreeSpan" xtype="tree"
				config=" dragAction : 'system-SysDataType-drag', dragInnerAction : 'system-SysDataType-dragInner' , id:'typeTree' , url:'system-SysDataType-queryTreeList' , remove : removeType , edit : editType , onClick : typeTreeClick  ,nheight:42 "></span>
		</div>
		<div id="datacenter">
			<div xtype="toolbar" config="type:'head'">
				字典管理
				<span xtype="button" config="onClick:addData,text:'添加'"></span>
				<span xtype="button"
					config="onClick : function(){dataTreeObject.refresh()} ,text: '刷新'"></span>
					&nbsp;<span id="msgspan" style="color:red;" ></span>
			</div>
			<span id="dataTreeSpan" xtype="tree"
				config=" dragAction : 'system-SysData-drag', dragInnerAction : 'system-SysData-dragInner' , id:'dataTree' , url:'system-SysData-queryTreeList' , remove : removeData , edit : editData "></span>
		</div>
	</div>
</body>
</html>