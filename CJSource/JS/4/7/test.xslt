<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="utf-8" indent="yes"
	doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
	doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />

	
	<xsl:template match="/bank">
		<html>
			<head>
				<title></title>
			</head>
			<body>
				<table border="1">
					<tr>
						<th>姓名</th>
						<th>年龄</th>
						<th>存款</th>
					</tr>
					<xsl:for-each select="/bank/p">
						<xsl:sort select="name" data-type="text" order="ascending" />
						
							<tr>
								<td>
									<xsl:choose>
										<xsl:when test="name='PHPer'">PHP程序员</xsl:when>
										<xsl:when test="name='DBD'">董书</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="name"></xsl:value-of>
										</xsl:otherwise>
									</xsl:choose>
								</td>
								<td><xsl:value-of select="age" /></td>
								<td>
									<xsl:if test="money &gt; 0">
										<xsl:value-of select="money" />
									</xsl:if>
									<xsl:if test="money &lt;= 0">
										<span style="color:red"><xsl:value-of select="money" /></span>
									</xsl:if>
								</td>
							</tr>
						
					</xsl:for-each>
				</table>
			</body>
		</html>
		
	</xsl:template>


	<xsl:template match="/bank/p">
		<tr>
			<td>
				<xsl:choose>
					<xsl:when test="name='PHPer'">PHP程序员</xsl:when>
					<xsl:when test="name='DBD'">董书</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="name"></xsl:value-of>
					</xsl:otherwise>
				</xsl:choose>
			</td>
			<td><xsl:value-of select="age" /></td>
			<td>
				<xsl:if test="money &gt; 0">
					<xsl:value-of select="money" />
				</xsl:if>
				<xsl:if test="money &lt;= 0">
					<span style="color:red"><xsl:value-of select="money" /></span>
				</xsl:if>
			</td>
		</tr>
	</xsl:template>
</xsl:transform>
