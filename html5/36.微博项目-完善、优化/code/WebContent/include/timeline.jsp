<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="timeline">
	<ul>
		<c:forEach items="${wbs}" var="a">
			<li>
				<div class="avatar">
					<img src="<%=request.getContextPath() %>/upload/${users[a.writerId].logo}" />
				</div>
				<div class="content">
					<div class="wb_user">
						<a href="<%=request.getContextPath()%>/rela/client_profile?id=${a.writerId}">${users[a.writerId].nickName}</a>
						<time>${a.timeStr}</time>
					</div>
					<div class="wb_content">
						${a.content}
					</div>
					<c:if test="${a.img != null}">
						<div class="wb_img">
							<img src="<%=request.getContextPath() %>/wb_img/${a.img}" />
						</div>
					</c:if>
					<c:if test="${a.forwardWibo != null}">
						<div class="wb_forward">
							<div class="fw_msg">
								<a href="<%=request.getContextPath()%>/rela/client_profile?id=${a.forwardWibo.writerId}">${users[a.forwardWibo.writerId].nickName}</a>:${a.forwardWibo.content}
							</div>
							<c:if test="${a.forwardWibo.img != null}">
								<div class="fw_img">
									<img src="<%=request.getContextPath() %>/wb_img/${a.forwardWibo.img}" />
								</div>
							</c:if>
						</div>
					</c:if>
					<div class="wb_btn">
						<a href="<%=request.getContextPath()%>/wb/fw?fid=${a.id}">转播</a>
					</div>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>

<c:if test="${nextPage == true}">
	<div id="more" class="more">
		<button onclick="more()">更多</button>
	</div>
</c:if>

<jsp:include page="/include/m_footer.jsp" />
<script>
	var pageNo = 2,contextPath = "<%=request.getContextPath()%>",more_url="${param.url}";
	function more(){
		$.getJSON(more_url + "&pageNo=" + pageNo,function(data){
			// 拼装HTML代码
			var html = "",users = data.users;
			for(var index in data.wbs){
				var a = data.wbs[index];
				html += "<li>";
				html += "	<div class=\"avatar\">";
				html += "		<img src='" + contextPath + "/upload/" + users[a.writerId].logo +"' />";
				html += "   </div>";
				html += "	<div class=\"content\">";
				html += "		<div class=\"wb_user\">";
				html += "    		<a href='" + contextPath + "/rela/client_profile?id=" + a.writerId + "'>" + users[a.writerId].nickName + "</a>";
				html += "    		<time>"  + a.timeStr +"</time>";
				html += "    	</div>";
				html += "		<div class=\"wb_content\">" + a.content + "</div>";
				
				if(a.img){
					html += "<div class='wb_img'>";
					html += "	<img src=\"" + contextPath + "/wb_img/" + a.img + "\" />";
					html += "</div>";
				}
				if(a.forwardWibo){
						html += "<div class='wb_forward'>";
						html += "	<div class='fw_msg'>";
						html += "		<a href='" + contextPath + "/rela/client_profile?id=" + a.forwardWibo.writerId + "'>" + data.users[a.forwardWibo.writerId].nickName + "</a>:" + a.forwardWibo.content;
						html += "	</div>";
						if(a.forwardWibo.img){
							html += "<div class='fw_img'>";
							html += "	<img src=\"" + contextPath + "/wb_img/" + a.forwardWibo.img + "\" />";
							html += "</div>";
						}
						html += "</div>";
				}
				html += "	<div class='wb_btn'>";
				html += " 		<a href=\"" + contextPath + "/wb/fw?fid=" + a.id + "\">转播</a>";
				html += "	</div>";
				html += "</li>";
			}
			
			$("#timeline ul").append(html);
			pageNo++;
			if(data.nextPage){
				$("#more").show();
			}else{
				$("#more").hide();
			}
		});
	}
</script>