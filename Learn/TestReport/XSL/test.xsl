<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
<html>
	<body>
		<h2 align="center">testresult</h2>
		<table border="1">
		<tr bgcolor="#9acd32">
		<th>ID</th>
		<th>result</th>
		<th>casename</th>
		<th>classname</th>
		<th>time</th>
		</tr>
		<xsl:for-each select="testsuites/testsuite/testcase">
		<tr>
		<td><xsl:value-of select="./@ID" /></td>
		<td><xsl:value-of select="result" /></td>
		<td><xsl:value-of select="./@casename" /></td>
		<td><xsl:value-of select="./@classname" /></td>
		<td><xsl:value-of select="./@time" /></td>
		</tr>
		</xsl:for-each>
		</table>
	</body>
</html>
</xsl:template>

</xsl:stylesheet>