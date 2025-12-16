<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Детали пользователя</title>
            </head>
            <body>
                <h1>Детали пользователя</h1>
                <xsl:for-each select="user">
                    <p><b>ID:</b> <xsl:value-of select="id"/></p>
                    <p><b>Name:</b> <xsl:value-of select="name"/></p>
                    <p><b>Email:</b> <xsl:value-of select="email"/></p>
                    <p><b>Age:</b> <xsl:value-of select="age"/></p>
                </xsl:for-each>
                <br/>
                <div>
                    <a href="/api/users">Вернуться к списку пользователей</a> |
                    <a href="/api/tasks">Посмотреть все задачи</a>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
