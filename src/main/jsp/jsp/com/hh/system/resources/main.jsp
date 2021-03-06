<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.hh.system.util.pk.PrimaryKey"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=SystemUtil.getBaseJs("layout","ztree", "ztree_edit")%>
<%
	String iframeId = PrimaryKey.getUUID();
%>
<style type="text/css">
.ztree li span.button.share_ico_docu{margin-right:2px; background: url(/hhcommon/images/icons/folder/folder_star.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.share_ico_open{margin-right:2px; background: url(/hhcommon/images/icons/folder/folder_star.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.share_ico_close{margin-right:2px; background: url(/hhcommon/images/icons/folder/folder_star.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>
<script type="text/javascript">
	var iframeId = '<%=iframeId%>';
	function treeClick(treeNode) {
		window.frames[iframeId].iframeClick(treeNode);
	}
	function init(){
	}
	function doSetState(state) {
		var node = $('#span_tree').getWidget().getSelectNode();
		if(node){
			var data = {};
			data.ids = node.id;
			data.state = state || 0;
			Request.request('system-ResourcesType-doSetState', {
				data : data
			}, function(result) {
				if (result.success != false) {
					$.hh.tree.refresh('tree');
				}
			});
		}else{
			Dialog.infomsg('请选中一条数据！');
		}
		
		
	}
	
	
	function doGX(){
		doSetState(1);
	}
	function doQXGX(){
		doSetState(0);
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
		<div config="render : 'west',width:220"  style="overflow :hidden; ">
			<div xtype="toolbar" config="type:'head'">
				<span xtype="button"
					config="onClick : $.hh.tree.refresh,text : '刷新' ,params: 'tree'  "></span>
					<span
			xtype="button" config="onClick: doGX ,text:'共享' "></span>
			<span
			xtype="button" config="onClick: doQXGX ,text:'取消共享' "></span>
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
				config=" id:'tree', url:'system-ResourcesType-queryTreeList' ,onClick : treeClick ,nheight:72  "></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="<%=iframeId%>" name="<%=iframeId%>" width=100%
				height=100% frameborder=0 src="jsp-system-resources-ResourcesList"></iframe>
		</div>
	</div>
</body>
</html>