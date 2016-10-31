<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>H5微博</title>
<jsp:include page="/include/head.jsp" />
</head>
<body class="m_body">
	<jsp:include page="/include/m_header.jsp">
		<jsp:param value="index" name="type"/>
	</jsp:include>
	
	<div id="home_title">
		<img class="avatar" src="<%=request.getContextPath()%>/upload/${s_user.logo}" />
		<div class="input" onclick="location.href='wb/writer.jsp';">
			<img src="images/w_1.png" height="19px" width="19px" />&nbsp;
			<img src="images/w_2.png" height="19px" width="178px"/>
		</div>
	</div>
	
	
	<div id="timeline">
		<ul>
			<c:forEach items="${wbs}" var="a">
				<li>
					<div class="avatar">
						<img src="<%=request.getContextPath() %>/upload/${users[a.writerId].logo}" />
					</div>
					<div class="content">
						<div class="wb_user">
							<a href="#">${users[a.writerId].nickName}</a>
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
									<a href="#">${users[a.forwardWibo.writerId].nickName}</a>:${a.forwardWibo.content}
								</div>
								<c:if test="${a.forwardWibo.img != null}">
									<div class="fw_img">
										<img src="<%=request.getContextPath() %>/wb_img/${a.forwardWibo.img}" />
									</div>
								</c:if>
							</div>
						</c:if>
						<div class="wb_btn">
							<a href="#">转播</a>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>