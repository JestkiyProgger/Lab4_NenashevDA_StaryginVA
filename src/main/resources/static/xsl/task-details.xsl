<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Детали задания</title>
            </head>
            <body>
                <h1>Детали задания</h1>
                <xsl:for-each select="task">
                    <p><b>ID:</b> <xsl:value-of select="id"/></p>
                    <p><b>Title:</b> <xsl:value-of select="title"/></p>
                    <p><b>User ID:</b>
                        <a href="/api/users/{userId}">
                            <xsl:value-of select="userId"/>
                        </a>
                    </p>
                    <p><b>Created Date:</b> <xsl:value-of select="createdDate"/></p>
                    <p><b>Completed:</b> <xsl:value-of select="completed"/></p>
                </xsl:for-each>
                <br/>
                <div>
                    <a href="/api/tasks">Обратно в список заданий</a> |
                    <a href="/api/users">Все пользователи</a>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
