<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 表单的命令对象名为dataBinderTest -->
<form:form commandName="dataBinderTest">
    <form:errors path="*" cssStyle="color:red"></form:errors><br/><br/>
    bool:<form:input path="bool"/><br/>
    phoneNumber:<form:input path="phoneNumber"/><br/>
    date:<form:input path="date"/><br/>
    <input type="submit" value="提交"/>
</form:form>
