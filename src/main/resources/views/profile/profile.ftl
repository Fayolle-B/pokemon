<#-- @ftlvariable name="activePossessions" type="java.util.Collection<com.uca.entity.PossessionEntity>" -->
<#-- @ftlvariable name="possessions" type="java.util.Collection<com.uca.entity.PossessionEntity>" -->
<#-- @ftlvariable name="numberOfPossessions" type="int" -->
<#-- @ftlvariable name="user" type="com.uca.entity.UserEntity" -->
<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/style.css">

    <title>Profile ${user.id} | PokeMy </title>
</head>
<body>
<header>

<#include "../menu.ftl">
<h1>${user.id}</h1>
    <p> ${user.login}</p>
</header>
<main>

<table>
    <caption>Les possessions de ${user.login}</caption>
    <tr>
        <th scope="col">id de la possessions</th>
        <th scope="col">Nom du pokemon</th>
        <th scope="col">Niveau du pokémon</th>
        <th scope="col">Date d'acuisition</th>
        <th scope="col">Date de perte</th>

    </tr>

    <#list activePossessions as possession>
        <tr>
            <th scope="row">${possession.idPos}</th>
            <td>${possession.pokemon.name}</td>
            <td>${possession.level}</td>
            <td>${possession.getDateAcquiAsString()}</td>
            <td>${possession.datePerte!"JAMAIS"}</td>
            <td>
                <form action="/trade" method="post">
                    <button value="${possession.idPos}" name="recipientPossessionID">Trade</button>
                </form>
            </td>
            <td>
                <form action="/pex" method="post">
                    <button value="${possession.idPos}" name="possessionID" <#if (connectedUser.points==0)>disabled style="color: red;background-color: darkgray; cursor: not-allowed" </#if>>Pex</button>
                </form>
            </td>
        </tr>

    </#list>

</table>
</main>
</body>
</html>
