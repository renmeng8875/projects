<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<root>
			<xsl:for-each select="//province">
				<a>
					<xsl:attribute name="n">
						<xsl:value-of select="name" />
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="@id" />
					</xsl:attribute>
				</a>
			</xsl:for-each>
			<xsl:for-each select="//city">
				<a t="1">
					<xsl:attribute name="n">
						<xsl:value-of select="name" />
					</xsl:attribute>
					<xsl:attribute name="o">
						<xsl:value-of select="../@id" />
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="@id" />
					</xsl:attribute>
				</a>
			</xsl:for-each>
			<xsl:for-each select="//town">
				<a t="2">
					<xsl:attribute name="n">
						<xsl:value-of select="." />
					</xsl:attribute>
					<xsl:attribute name="o">
						<xsl:value-of select="../@id" />
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="@id" />
					</xsl:attribute>
				</a>
			</xsl:for-each>
		</root>
	</xsl:template>
</xsl:stylesheet>
