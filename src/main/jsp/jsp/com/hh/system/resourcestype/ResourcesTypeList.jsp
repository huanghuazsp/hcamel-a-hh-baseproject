<%@page import="com.hh.system.util.SystemUtil"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.pk.PrimaryKey"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=SystemUtil.getBaseJs("layout","ztree", "ztree_edit")+SystemUtil.getUser()%>
<%
	String iframeId = PrimaryKey.getUUID();
%>

<script type="text/javascript">
	var iframeId = '<%=iframeId%>';
	function doAdd() {
		$('#centerdiv').undisabled();
		var selectNode = $.hh.tree.getSelectNode('tree');
		var iframe = window.frames[iframeId];
		iframe.callback = function() {
			$.hh.tree.refresh('tree');
			$('#centerdiv').disabled('请选择要编辑的树节点或添加新的数据！');
		}
		if (selectNode) {
			iframe.newData({
				node : selectNode.id
			});
		} else {
			iframe.newData({});
		}
		return;
		Dialog.open({
			url : 'jsp-system-resourcestype-ResourcesTypeEdit',
			params : {
				selectNode : selectNode,
				callback : function() {
					$.hh.tree.refresh('tree');
				}
			}
		});
	}
	function doEdit(treeNode) {
		Dialog.open({
			url : 'jsp-system-resourcestype-ResourcesTypeEdit',
			params : {
				object : treeNode,
				callback : function() {
					$.hh.tree.refresh('tree');
				}
			}
		});
	}
	function doDelete(treeNode) {
		$.hh.tree.deleteData({
			pageid : 'tree',
			action : 'system-ResourcesType-deleteTreeByIds',
			id : treeNode.id,
			callback : function(result) {
				if (result.success!=false) {
					$('#centerdiv').disabled('请选择要编辑的树节点或添加新的数据！');
				}
			}
		});
	}
	function treeClick(treeNode) {
		$('#centerdiv').undisabled();
		var iframe = window.frames[iframeId];
		iframe.callback = function(object) {
			treeNode.name = object.text;
			$.hh.tree.updateNode('tree', treeNode);
			$.hh.tree.getTree('tree').refresh();
		}
		iframe.findData(treeNode.id);
		/* if(treeNode.createUser!=loginUser.id){
			$('#upBtn').hide();
			$('#downBtn').hide();
		}else{
			$('#upBtn').show();
			$('#downBtn').show();
		} */
	}
	function init(){
		$('#centerdiv').disabled('请选择要编辑的树节点或添加新的数据！');
	}
	function showBtn(treeid,node){
		return node.createUser==loginUser.id;
	}
	function querytree(){
		$('#span_tree').loadData({
			params : {text:$('#span_treeText').getValue()}
		});
	}
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west'"  style="overflow :hidden; ">
			<div xtype="toolbar" config="type:'head'">
				<span xtype="button" config="onClick: doAdd ,text:'添加'"></span> <span
					id="upBtn" xtype="button"
					config="onClick: $.hh.tree.doUp , params:{treeid:'tree',action:'system-ResourcesType-order'}  , textHidden : true,text:'上移' ,icon : 'hh_up' "></span>
				<span	id="downBtn"  xtype="button"
					config="onClick: $.hh.tree.doDown , params:{treeid:'tree',action:'system-ResourcesType-order'} , textHidden : true,text:'下移' ,icon : 'hh_down' "></span>
				<span xtype="button"
					config="onClick : $.hh.tree.refresh,text : '刷新' ,params: 'tree'  "></span>
			</div>
			<div style="padding:2px;">
			
			
			<table style="font-size: 12" width=100%  cellspacing="0" cellpadding="0" ><tr>
			<td >
			<span xtype="text" config=" name : 'treeText' ,enter: querytree"></span>
			</td>
			<td width="40px" style="text-align:right;">
			<span xtype="button" config=" icon :'hh_img_query' , onClick : querytree "></span>
			</td><tr></table>
			</div>
			<span xtype="tree"
				config=" id:'tree', showRemoveBtn : showBtn, url:'system-ResourcesType-queryTreeList' ,params:{state:2} ,remove: doDelete , onClick : treeClick ,nheight:72 "></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="<%=iframeId%>" name="<%=iframeId%>" width=100%
				height=100% frameborder=0 src="jsp-system-resourcestype-ResourcesTypeEdit"></iframe>
		</div>
	</div>
</body>
</html>