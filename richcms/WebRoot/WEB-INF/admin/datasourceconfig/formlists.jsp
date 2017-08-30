<%@include file="../public/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html public "-/w3c/dtd html 4.01 transitional/en" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<meta name="keywords" content=" " />
<meta name="description" content="" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<link href="${ctx}/static/admin/css/all.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/admin/css/reset.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/admin/css/ligerui-all.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/admin/css/gray/css/all.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/admin/css/ligerui-icons.css" rel="stylesheet"	type="text/css" />

<script src="${ctx}/static/admin/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/static/admin/js/json2.js" type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/base.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerGrid.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerToolBar.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerResizable.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerDrag.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerCheckBox.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerDialog.js"	type="text/javascript"></script>
<style>
.treeopra{
    color: #219B00;
    display: inline-block;
    height: 20px;
    line-height: 20px;
    margin: 0 10px;
    text-decoration: none;
}
</style>
<script type="text/javascript">
	var gridManager = null;
	$(function() {
		gridManager = $("#maingrid").ligerGrid({
			columns : [ {
				display : 'ID',
				name : 'formid',
				width : 60,
				align : 'center'
			}, {
				display : '数据源名称',
				name : 'formdesc',
				width: 200,
				align : 'center'
			}, {
				display : '数据源类型',
				width : 150,
				align : 'center',
				render:function(row){
				     var html = "";
				     if(row.formtype=="base"){
				        html = "栏目表";
				     }else if(row.formtype=="extends"){
				        html = "数据源表";
				     }
				     return html;
				}
			}, {
				display : '数据源标识',
				name : 'datatype',
				width : 150,
				align : 'center'
			}, {
				display : '数据源模板',
				name : 'template',
				width : 250,
				align : 'center'
			}, {
				display : '管理',
				align : 'center',
				render:function(row){
					var html = '<a class="treeopra" href="${ctx}/dataconfig/editform.do?formid='+row.formid+'">修改</a>'+
                               '<a class="treeopra" href="${ctx}/dataconfig/edittemplate.do?type='+row.datatype+'&formid='+row.formid+'">配置模板</a>'+
                               '<a class="treeopra" style="cursor: pointer;" onclick="javascript:del('+row.formid+');">删除</a>';
                    return html;
				}
			}],
			pageSize: 10,
			sortName: 'sid',
            width: '100%', height: '98%',
            rownumbers:true,
            checkbox : true,
            //应用灰色表头
            cssClass: 'l-grid-gray', 
            heightDiff: -6,
            fixedCellHeight:false,
			data : { Rows : ${formlist} }
// 			toolbar : {
// 				items : [  {
// 				    id   : '1',
// 					text : '增加数据源',
// 					click : itemclick,
// 					icon : 'add'
// 				}, {
// 					line : true
// 				} ]
// 			}
	 
		});
		$("#pageloading").hide(); 
		
		gridManager = $("#maingrid").ligerGetGridManager();
	});
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "1":
				window.location.href="${ctx}/dataconfig/editform.do";
				break;
			}
		}
		
	}
	
	function del(formid){
	    if(confirm('确认删除该数据源')){
	      $.ajax({
				   type: "post",
				   dataType:"json",
				   url: "${ctx}/dataconfig/deleteForm.do?csrfToken=${csrfToken}",
				   data: "formid="+formid,
				   success: function(rs){
				    	if(rs.status=='0'){
				    		gridManager.deleteSelectedRow();
				    	}
				    	alert(rs.msg);
				   }
				})
		}
	}
</script>

</head>
<body style="">
<div class="main_border"><div id="my_main">
	<h2 class="adm-title clearfix">
		<a class="fl title">数据源配置管理</a> 
	</h2>
	<div class="l-clear"></div>
    <div class="l-loading" style="display:block" id="pageloading"></div> 

	<div id="maingrid" style="padding:1px 4px;"></div>

	<div style="display: none;"></div>

	<a class="l-button"
		style="width: 120px; float: left; margin-left: 10px; display: none;"></a>
 </div></div>
</body>
</html>