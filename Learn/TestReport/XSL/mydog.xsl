<?xml version="1.0" encoding="iso-8859-1"?>  
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">  
  
<xsl:template match="/">  
<html>  
<head>  
    <title>Review of My Dogs</title>  
</head>  
<body>  
    <h4>list of My Dogs</h4>  
      
    <table width="100%" border="1">  
        <thead>  
            <tr>  
            <th>ID</th>  
            <th>classname</th>  
            <th>casename</th>  
            <th>time</th>  
            <th>message</th> 
            <th>result</th>  
            </tr>  
        </thead>  
        <tbody>  
            <xsl:apply-templates/>  
        </tbody>  
    </table>  
</body>  
</html>  
</xsl:template>  
  
<xsl:template match="testsuite">  
    <tr>  
        <td>  
            <strong><xsl:value-of select="testcase" /></strong>  
        </td>  
        <td><xsl:value-of select="@ID" /></td>  
        <td><xsl:value-of select="@classname" /></td>  
        <td><xsl:value-of select="@casename" /> </td>  
        <td><xsl:value-of select="casename" /></td> 
        <td><xsl:value-of select="result" /></td>  
        
    </tr>  
  
</xsl:template>  
  
<xsl:template match="age">  
    <xsl:value-of select="years" />years  
    <xsl:value-of select="months" />months  
</xsl:template>  
  
</xsl:stylesheet>  