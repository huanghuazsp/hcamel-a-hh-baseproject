<%@page import="com.hh.system.util.Convert"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>会议申请</title>
<%=SystemUtil.getBaseJs("checkform", "date")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 650;
	var height = 430

	var object = params.object;
	var objectid = object ? object.id : '';

	function save() {
		$.hh.validation.check('form', function(formData) {
			if (params.object) {
				formData.meetingId = params.object.meetingId;
				formData.meetingIdText = params.object.meetingIdText;
			}
			
			if(!formData.attendUser && !formData.attendOrg){
				Dialog.alert('请选择参与部门或参与人员');
				return;
			}
			Request.request('oa-MeetingApply-save', {
				data : formData,
				callback : function(result) {
					if (result.success != false) {
						params.callback(result);
						Dialog.close();
					}
				}
			});
		});
	}

	function doDelete() {
		if (objectid) {
			Request.request('oa-MeetingApply-deleteByIds', {
				data : {
					ids : objectid
				}
			}, function(result) {
				if (result.success != false) {
					params.callback('delete');
					Dialog.close();
				}
			});
		}
	}

	function findData() {
		if (object) {
			if (object.id) {
				$('#deletespan').show();
				Request.request('oa-MeetingApply-findObjectById', {
					data : {
						id : object.id
					},
					callback : function(result) {
						$('#form').setValue(result);
					}
				});
			}else{
				if (params.object) {
					object.meetingId = params.object.meetingId;
					object.meetingIdText = params.object.meetingIdText;
				}
				$('#form').setValue(object);
			}
		}
	}

	function init() {
		findData();
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<span xtype="text" config=" hidden:true,name : 'meetingId'"></span>
			
			<table xtype="form">
				<tr>
					<td xtype="label">会议室：</td>
					<td colspan="3"><span xtype="text"
						config=" name : 'meetingIdText' ,readonly :true"></span></td>
				</tr>
				<tr>
					<td xtype="label">会议主题：</td>
					<td colspan="3"><span xtype="text"
						config=" name : 'text' ,required :true "></span></td>
				</tr>
				<tr>
					<td xtype="label">参与人员：</td>
					<td colspan="3"><span xtype="selectUser"
						config=" name : 'attendUser' ,many:true "></span></td>
				</tr>
				<tr>
					<td xtype="label">参与部门：</td>
					<td colspan="3"><span  xtype="selectOrg" config="name: 'attendOrg',many:true  "></span></td>
				</tr>
				<tr>
					<td xtype="label">开始：</td>
					<td><span xtype="date"
						config="name: 'start'  ,type:'datetime' ,required :true"></span></td>
					<td xtype="label">结束：</td>
					<td><span xtype="date"
						config="name: 'end'  ,type:'datetime' ,required :true "></span></td>
				</tr>
				<tr>
					<td xtype="label">描述：</td>
					<td colspan="3"><span  xtype="textarea" config="name: 'describe'   "></span></td>
				</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span id="deletespan" xtype="button"
			config="text:'删除' , onClick : doDelete ,hidden:true"></span> <span
			id="savespan" xtype="button" config="text:'保存' , onClick : save "></span>
		<span xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>