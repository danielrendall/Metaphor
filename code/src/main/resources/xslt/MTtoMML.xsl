<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/1998/Math/MathML"
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
    <xsl:when test="matches($s,'^\p{N}+$')">
      <mn><xsl:value-of select="$s" /></mn>
    </xsl:when>
    <xsl:when test="matches($s,'^\p{L}+$')">
      <mi><xsl:value-of select="$s" /></mi>
    </xsl:when>
    <xsl:otherwise>
      <mo><xsl:value-of select="$s" /></mo>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="tmpl[matches(@templateType,'^(2[7-9])$')]">
  <msubsup>
    <mrow/>
    <xsl:apply-templates />
  </msubsup>
</xsl:template>

<xsl:template match="tmpl[@templateType = '11']">
  <mfrac>
    <xsl:apply-templates />
  </mfrac>
</xsl:template>

<xsl:template match="tmpl[@templateType = '23']">
  <munderover>
    <xsl:apply-templates />
  </munderover>
</xsl:template>

<xsl:template match="tmpl[matches(@templateType,'^[1-3]$')]">
  <xsl:variable name="open" select="codepoints-to-string(*[2]/@MTCode)" />
  <xsl:variable name="close" select="codepoints-to-string(*[3]/@MTCode)" />
  <mfenced open="{$open}" close="{$close}">
    <xsl:apply-templates select="*[1]"/>
  </mfenced>
</xsl:template>

<xsl:template match="matrix">
  <mtable>
    <xsl:apply-templates />
  </mtable>
</xsl:template>

<xsl:template match="matrix/row">
  <mtr>
    <xsl:apply-templates />
  </mtr>
</xsl:template>

<xsl:template match="matrix/row/*">
  <mtd>
    <xsl:apply-templates />
  </mtd>
</xsl:template>

</xsl:stylesheet>