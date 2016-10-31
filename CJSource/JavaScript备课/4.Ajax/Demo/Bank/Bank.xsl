<?xml version="1.0" encoding="GB2312"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
	doctype-system="http://www.w3.org/TR/xhtml/DTD/xhtml1-transitional.dtd"
	version="1.0"
	encoding="GB2312" />
	<xsl:template match="/">
		<html>
			<head>
				<title>XSLT</title>
			</head>
			<body>
			<table border="1">
				<caption>银行储蓄统计单</caption>
				<tr>
					<th>姓名</th>
					<th>年龄</th>
					<th>储金</th>
				</tr>
				<xsl:for-each select="/bank/p">
					<tr>
					<xsl:if test="position() mod 2 = 0">
					<xsl:attribute name="style">background:#EEEEEE;</xsl:attribute>
					</xsl:if>
					<xsl:if test="money &lt; 0">
					<xsl:attribute name="style">background:#FF0000;color:white;</xsl:attribute>
					</xsl:if>
							<td><xsl:value-of select="./name" /></td>
							<td><xsl:value-of select="./age" /></td>
							<td><xsl:value-of select="./money" /></td>
					</tr>
				</xsl:for-each>
			</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
