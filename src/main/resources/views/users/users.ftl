<#-- @ftlvariable name="users" type="java.util.Collection<com.uca.entity.UserEntity>" -->
<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/style.css">

    <title>Liste des utilisateurs | PokeMy</title>
</head>
<body>
<header>
    <#include "../menu.ftl">
    <h1>Liste des utilisateurs</h1>
    <p> (Pour pouvoir tester toutes les fonctions du site, ne serait pas affichée comme ça sur un site publique</p>

</header>
<main>
    <table>
        <caption>Utilisateurs</caption>
        <thead>
        <tr>
            <th>Id</th>
            <th>Prénom</th>
            <th>Nom</th>
            <th>Login</th>
            <th>Email</th>
            <th>Date de connexion</th>
        </tr>
        </thead>
        <tbody>

        <#list users as user>
        <tr class="row-link" onclick="window.location='/profile/${user.id}'">
            <td> ${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.dateConnexion}</td>
        </tr>
    </#list>

        </tbody>
    </table>

</main>
</body>

