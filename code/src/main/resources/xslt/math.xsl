<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version='2.0'>

<xsl:template match="/">
  <math>
    <xsl:apply-templates/>
  </math>
</xsl:template>

<xsl:template match="line">
  <mrow>
    <xsl:apply-templates/>
  </mrow>
</xsl:template>

<xsl:template match="char">
  <xsl:variable name="s" select="codepoints-to-string(@MTCode)" />
  <xsl:choose>
    <xsl:when test="matches($s,'\p{N}')">
      <mn><xsl:value-of select="$s" /></mn>
    </xsl:when>
    <xsl:otherwise>
      <mi><xsl:value-of select="$s" /></mi>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
