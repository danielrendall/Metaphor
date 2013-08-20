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
  <xsl:variable name="char" select="codepoints-to-string(@MTCode)" />
  <xsl:choose>
    <xsl:when test="matches($char,'^\p{N}+$')">
      <mn><xsl:value-of select="$char" /></mn>
    </xsl:when>
    <xsl:when test="matches($char,'^\p{L}+$')">
      <mi><xsl:value-of select="$char" /></mi>
    </xsl:when>
    <xsl:otherwise>
      <mo><xsl:value-of select="$char" /></mo>
    </xsl:otherwise>
  </xsl:choose>
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

<xsl:template match="tmpl[matches(@templateType,'^[0-9]$')]">
  <xsl:variable name="open" select="codepoints-to-string(*[2]/@MTCode)" />
  <xsl:variable name="close" select="codepoints-to-string(*[3]/@MTCode)" />
  <mfenced open="{$open}" close="{$close}">
    <xsl:apply-templates select="*[1]"/>
  </mfenced>
</xsl:template>

<xsl:template match="tmpl[@templateType = '10']">
  <xsl:variable name="vari" select="@variation" />
  <xsl:choose>
    <xsl:when test="$vari = '1'">
      <mroot>
        <xsl:apply-templates />
      </mroot>
    </xsl:when>
    <xsl:otherwise>
      <msqrt>
        <xsl:apply-templates />
      </msqrt>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="tmpl[@templateType = '11']">
  <xsl:variable name="vari" select="@variation" />
  <xsl:choose>
    <xsl:when test="$vari = '6'">
      <mrow>
        <xsl:apply-templates select="*[1]" />
        <mo>/</mo>
        <xsl:apply-templates select="*[2]" />
      </mrow>
    </xsl:when>
    <xsl:when test="$vari = '2'">
      <mfrac bevelled='true'>
        <xsl:apply-templates />
      </mfrac>
    </xsl:when>
    <xsl:otherwise>
      <mfrac>
        <xsl:apply-templates />
      </mfrac>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="tmpl[@templateType = '12']">
  <munder>
    <xsl:variable name="vari" select="@variation" />
    <xsl:choose>
      <xsl:when test="$vari = '1'">
        <munder>
          <xsl:apply-templates />
          <mo stretchy='true'>&#x00AF;</mo>
        </munder>
        <mo stretchy='true'>&#x00AF;</mo>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates />
        <mo stretchy='true'>&#x00AF;</mo>
      </xsl:otherwise>
    </xsl:choose>
  </munder>
</xsl:template>

<xsl:template match="tmpl[@templateType = '13']">
  <mover>
    <xsl:variable name="vari" select="@variation" />
    <xsl:choose>
      <xsl:when test="$vari = '1'">
        <mover>
          <xsl:apply-templates />
          <mo stretchy='true'>&#x00AF;</mo>
        </mover>
        <mo stretchy='true'>&#x00AF;</mo>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates />
        <mo stretchy='true'>&#x00AF;</mo>
      </xsl:otherwise>
    </xsl:choose>
  </mover>
</xsl:template>

<xsl:template match="tmpl[matches(@templateType, '^(1[5-9]|2[0-2])$')]">
  <xsl:apply-templates select="line[1]"/>
  <munderover>
    <xsl:apply-templates select="line[4]"/>
    <xsl:apply-templates select="line[2]"/>
    <xsl:apply-templates select="line[3]"/>
  </munderover>
</xsl:template>

<xsl:template match="tmpl[@templateType = '23']">
  <munderover>
    <xsl:apply-templates />
  </munderover>
</xsl:template>

<xsl:template match="tmpl[@templateType = '24']">
  <xsl:variable name="vari" select="@variation" />
  <xsl:choose>
    <xsl:when test="$vari = '1'">
      <mover>
        <mover>
          <xsl:apply-templates select="line[1]"/>
          <mo stretchy='true'>&#65079;</mo>
        </mover>
        <xsl:apply-templates select="line[2]"/>
      </mover>
    </xsl:when>
    <xsl:otherwise>
      <munder>
        <munder>
          <xsl:apply-templates select="line[1]"/>
          <mo stretchy='true'>&#65080;</mo>
        </munder>
        <xsl:apply-templates select="line[2]"/>
      </munder>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="tmpl[@templateType = '25']">
  <xsl:variable name="vari" select="@variation" />
  <xsl:choose>
    <xsl:when test="$vari = '1'">
      <mover>
        <mover>
          <xsl:apply-templates select="line[1]"/>
          <mo stretchy='true'>&#9140;</mo>
        </mover>
        <xsl:apply-templates select="line[2]"/>
      </mover>
    </xsl:when>
    <xsl:otherwise>
      <munder>
        <munder>
          <xsl:apply-templates select="line[1]"/>
          <mo stretchy='true'>&#9141;</mo>
        </munder>
        <xsl:apply-templates select="line[2]"/>
      </munder>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="tmpl[@templateType = '26']">
  <xsl:variable name="vari" select="@variation" />
  <xsl:choose>
    <xsl:when test="$vari = '1'">
      <mtable>
        <mtr>
          <mtd columnalign='right'>
            <xsl:apply-templates select="*[2]" />
          </mtd>
        </mtr>
        <mtr>
          <mtd columnalign='right'>
            <menclose notation='longdiv'>
              <xsl:apply-templates select="*[1]" />
            </menclose>
          </mtd>
        </mtr>
      </mtable>
    </xsl:when>
    <xsl:otherwise>
      <menclose notation='longdiv'>
        <xsl:apply-templates />
      </menclose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="tmpl[matches(@templateType,'^(2[7-9])$')]">
  <msubsup>
  	<mrow />
    <xsl:apply-templates />
  </msubsup>
</xsl:template>

<xsl:template match="tmpl[@templateType = '31']">
  <xsl:variable name="vari" select="@variation" />
  <xsl:choose>
    <xsl:when test="$vari = '7'">
      <munder>
        <xsl:apply-templates select="line[1]" />
        <mo stretchy='true'>
          <xsl:apply-templates select="char[1]" />
        </mo>
      </munder>
    </xsl:when>
    <xsl:otherwise>
      <mover>
        <xsl:apply-templates select="line[1]" />
        <mo stretchy='true'>
          <xsl:apply-templates select="char[1]" />
        </mo>
  	  </mover>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>