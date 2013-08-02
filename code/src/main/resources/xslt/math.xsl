<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exsl="http://exslt.org/common"
                xmlns:ce="http://www.elsevier.com/xml/common/dtd"
                xmlns:sb="http://www.elsevier.com/xml/common/struct-bib/dtd"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                xmlns:mml="http://www.w3.org/1998/Math/MathML"
		xmlns:uc="http://www.elsevier.com/xml/apps/qc/uc"
		xmlns:tb="http://www.elsevier.com/xml/common/table/dtd"
		xmlns="http://www.w3.org/1999/xhtml"
		exclude-result-prefixes="xsl exsl xlink tb ce sb mml uc"
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
