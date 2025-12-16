<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Список заданий</title>
            </head>
            <body>
                <h1>Все задания</h1>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>User ID</th>
                        <th>Created Date</th>
                        <th>Completed</th>
                        <th>Actions</th>
                    </tr>
                    <xsl:for-each select="ArrayList/item">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="title"/></td>
                            <td>
                                <a href="/api/users/{userId}">
                                    <xsl:value-of select="userId"/>
                                </a>
                            </td>
                            <td><xsl:value-of select="createdDate"/></td>
                            <td><xsl:value-of select="completed"/></td>
                            <td>
                                <a href="/api/tasks/{id}">Посмотреть детали</a>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
                <br/>
                <div>
                    <a href="/api/users">Список всех пользователей</a>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
