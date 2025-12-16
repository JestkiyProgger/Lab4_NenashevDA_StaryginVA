<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Список пользователей</title>
            </head>
            <body>
                <h1>Все пользователи</h1>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Age</th>
                        <th>Actions</th>
                    </tr>
                    <xsl:for-each select="ArrayList/item">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="email"/></td>
                            <td><xsl:value-of select="age"/></td>
                            <td>
                                <a href="/api/users/{id}">Посмотреть детали</a> |
                                <a href="/api/users/{id}/tasks">Посмотреть задачи</a>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
                <br/>
                <div>
                    <a href="/api/tasks">Посмотреть все задачи</a>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
