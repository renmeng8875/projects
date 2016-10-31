<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="no" omit-xml-declaration="no"/>
	<xsl:template match="/">
<!--
元素a表示地区,属性n(name)表示名称,id就是表示ID,own表示其所属地区,
t(type)属性是表示类别,0是province,1是city,2是town
-->
	<root>
		<xsl:for-each select="//province|//city|//town">
				<xsl:sort data-type="text" select="name(.)" />
				<a>
					<xsl:attribute name="t">
					<xsl:choose>
						<xsl:when test="name(.)='province'">0</xsl:when>
						<xsl:when test="name(.)='city'">1</xsl:when>
						<xsl:when test="name(.)='town'">2</xsl:when>
					</xsl:choose>
					</xsl:attribute>
					<xsl:choose>
						<xsl:when test="name">
							<xsl:attribute name="n"><xsl:value-of select="name"/></xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="n"><xsl:value-of select="text()"/></xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:attribute name="id">A<xsl:value-of select="@id"/></xsl:attribute>
					<xsl:if test="@own">
						<xsl:attribute name="own">A<xsl:value-of select="@own"/></xsl:attribute>
					</xsl:if>
				</a>
		</xsl:for-each>
	</root>
	</xsl:template>
</xsl:stylesheet>
