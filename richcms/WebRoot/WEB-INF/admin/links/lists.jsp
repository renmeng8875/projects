<%@include file="../public/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html public "-/w3c/dtd html 4.01 transitional/en" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<meta name="keywords" content=" " />
<meta name="description" content="" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>友情链接管理</title>
<link href="${ctx}/static/admin/css/all.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/admin/css/reset.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/admin/css/ligerui-all.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/admin/css/gray/css/all.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}/static/admin/css/ligerui-icons.css" rel="stylesheet"	type="text/css" />

<script src="${ctx}/static/admin/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/base.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerGrid.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerToolBar.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerResizable.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerDrag.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerCheckBox.js"	type="text/javascript"></script>
<script src="${ctx}/static/admin/js/ligerui/plugins/ligerDialog.js"	type="text/javascript"></script>

<script type="text/javascript">
	var gridManager = null;
	$(function() {
		gridManager = $("#maingrid").ligerGrid({
			columns : [ {
				display : 'ID',
				name : 'linkid',
				width : 100,
				align : 'center'
			},{
				display : '排序',
				name : 'priority',
				width : 120,
				align : 'center'
			}, {
				display : '网站名称',
				name : 'site',
				width : 120,
				align : 'center'
			}, {
				display : '网站地址',
				name : 'linkurl',
				width : 200,
				align : 'center'
			},{
				display : '图片LOGO',
				name : 'logo',
				width : 200,
				align : 'center',
				render:function(item){
					try{
						JSON.stringify(item.logo);
						return JSON.stringify(item.logo);
					}catch(e){
						return item.logo;
					}
				}
			}, {
				display : '网站联系人',
				name : 'contact',
				width : 200,
				align : 'center'
			}, {
				display : '网站介绍',
				name : 'mem',
				width : 120,
				align : 'center'
			}],
			width : '100%',
			pageSize: 5,
			pageSizeOptions : [ 5, 10, 15, 20 ],
			sortName: 'sid',
            width: '100%', height: '100%', pageSize: 10,rownumbers:true,
            checkbox : true,
            //应用灰色表头
            cssClass: 'l-grid-gray', 
            heightDiff: -6,
            width : '100%',
            fixedCellHeight:false,
            
			data : { Rows : ${linkslist} },
            
			toolbar : {
				items : [ { 
				    id :'1',
					text : '增加',
					click : itemclick,
					icon : 'add'
				},{
					line:true
				},
				{ 
				    id :'2',
					text : '修改',
					click : itemclick,
					icon : 'modify'
				}, {
					line : true
				}, { 
				     id :'3',
					text : '删除',
					click : itemclick,
					icon : 'delete'
				} ]
			}
	 
		});
		 gridManager = $("#maingrid").ligerGetGridManager();
	});
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "1":
				window.location.href="${ctx}/Links/add.do";
				break;
			case "2":
				var row = gridManager.getSelectedRow();
				if(!row){
					alert("请先选择一条要修改的数据！");
					return false;
				}
				window.location.href = "${ctx}/Links/edit.do?linkid="+row.linkid;
				return;
			case "3":
				var data = gridManager.getCheckedRows();
				if (data.length == 0)
					alert("请至少选中一条链接数据！");
				else {
					var checkedIds = [];
					$(data).each(function() {
						checkedIds.push(this.linkid);
					});
					$.ligerDialog.confirm('确定删除' + checkedIds.join(',') + '?',function(btn){
					 if(btn){
						$.ajax({
						   type: "post",
						   url: "${ctx}/Links/del.do?csrfToken=${csrfToken}",
						   data: "idstr="+checkedIds,
						   success: function(msg){
						     if(msg.status=='0') {
						    	 alert("删除成功！"); 
						    	 gridManager.deleteSelectedRow();
						     }
						   }
						})
					  }		
					});
				}
				return;
			}
		}
		
	}
</script>

</head>
<body style="overflow-x: hidden; padding: 4px;">
	<h2 class="adm-title clearfix"><a class="fl title">友情链接</a><a class="fr btn" href="${ctx}/Links/add.do">+添加友情链接</a></h2>
	<div class="l-clear"></div>

	<div id="maingrid"></div>

	<div style="display: none;"></div>

	<a class="l-button"
		style="width: 120px; float: left; margin-left: 10px; display: none;"></a>
</body>
</html>