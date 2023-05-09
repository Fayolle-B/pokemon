<#-- @ftlvariable name="users" type="java.util.Collection<com.uca.entity.UserEntity>" -->
<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Liste des utilisateurs</title>
</head>
<body>
<ul>
    <#list users as user>
        <li><a href="/profile/${user.id}">
                ${user.id} - ${user.firstName} ${user.lastName} -login : ${user.login} - mot de passe : ${user.pwd} ${user.email}
                ${user.dateConnexion}
            </a>

        </li
        >
    </#list>
</ul>

</body>

