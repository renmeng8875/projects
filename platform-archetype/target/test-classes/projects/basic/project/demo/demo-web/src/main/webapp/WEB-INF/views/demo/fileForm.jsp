<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>html login demo</title>
</head>

<body>
<form action="/demo/demo/file/upload" method="post" enctype="multipart/form-data">
    <p>file:<input type="file" name="file"/></p>

    <p>other msg:<input type="input" name="msg"/></p>

    <p><input type="submit" value="upload"/></p>
</form>
</body>
</html>
