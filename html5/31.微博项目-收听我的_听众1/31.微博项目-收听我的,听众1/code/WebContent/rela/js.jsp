<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function showList(data){
		var users = data.users,
			relas = data.relas,
			html = "",
			contextPath = "<%=request.getContextPath()%>";
		
		$("#spn_count").html(users.length);
		for(var i=0;i<users.length;i++){
			var u = users[i];
			var rela = relas[u.id];
			rela = rela || 0;
			
			html += "<li>";
			html += "	<div class='avatar'>";
			html += "		<a href='#'>";
			html += "			<img src='" + contextPath + "/upload/" + u.logo + "' />";
			html += "		</a>";
			html += "	</div>";
			html += "	<div class='user'>";
			html += "		<a href='#'>" + u.nickName + "</a><br />";
			html += "		<span>@" + u.userName + "</span>";
			html += "	</div>";
			html += "	<div class='rela'>";
			if(rela < 2){
				html += "	<div id='btn_l_" + u.id + "' onclick='listen(" + u.id + ");' class='btn_listen'>立即收听</div>";
			}else{
				html += "	<div id='btn_l_" + u.id + "' onclick='cancelListen(" + u.id + ");' class='btn_cancal'>取消收听</div>";
			}
			
			if(rela == 2){
				html += "	<span>已互听</span>";
			}
			
			html += "	</div>";
			html += "</li>";
		}
		
		$("#ulFans").html(html);
	}
	
	$("#btn_search").bind("click",function(){
		var q = $("#q").val();
		if(q.trim().length == 0){
			alert("必须输入要搜索的关键词!");
			return;
		}
		
		$.getJSON("search_do",{"q":q},function(data){
			showList(data);
		});
	});
	
	function listen(id){
		$.getJSON("add_listen_do",{"id":id});
		$("#btn_l_" + id).html("取消收听").attr("class","btn_cancal").attr("onclick","cancelListen(" + id + ")");
	}
	function cancelListen(id){
		$.getJSON("cancel_listen_do",{"id":id});
		$("#btn_l_" + id).html("立即收听").attr("class","btn_listen").attr("onclick","listen(" + id + ")");
	}
</script>