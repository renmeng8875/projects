<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="no"/>
	<xsl:template match="/">
		{
			p:[
				<xsl:for-each select="//a[@t=0]">
					[&quot;<xsl:value-of select="@n" />&quot;,&quot;<xsl:value-of select="@id" />&quot;]<xsl:if test="position() != last()">,</xsl:if>
				</xsl:for-each>
			],
			a:{
				<xsl:call-template name="show_areas">
				</xsl:call-template>
			}
		}
	</xsl:template>
	
	<xsl:template name="show_areas">
		<xsl:for-each select="//a[@t=0]|//a[@t=1]">
			&quot;<xsl:value-of select="@id" />&quot;:
			[<xsl:call-template name="show_cities">
				<xsl:with-param name="id" select="@id"/>
			</xsl:call-template>]<xsl:if test="position() !=last()">,</xsl:if>
		</xsl:for-each>
	</xsl:template>
	
	
	<xsl:template name="show_cities">
		<xsl:param name="id"/>
		<xsl:for-each select="//a[@own=$id]">
			[&quot;<xsl:value-of select="@n"/>&quot;,&quot;<xsl:value-of select="@id"/>&quot;]<xsl:if test="position() != last()">,</xsl:if>
		</xsl:for-each>
		<xsl:if test="count(//a[@own=$id])=0">[]</xsl:if>
	</xsl:template>
</xsl:stylesheet>
