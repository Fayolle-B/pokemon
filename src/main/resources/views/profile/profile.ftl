<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Profile ${user.id} </title>
</head>
<body>
<h1>${user.id}</h1>

Prénom  : ${user.getFirstName()}
Nom : ${user.getLastName()}
nombre de possession : ${numberOfPossessions!"0"}

<table>
    <caption>Les possessions de ${user.firstName}</caption>
    <tr>
        <th scope="col">id de la possessions</th>
        <th scope="col">numero du PKMN</th>
        <th scope="col">Niveau du pokémon</th>
        <th scope="col">Date d'acuisition</th>
        <th scope="col">Date de perte</th>

    </tr>
<form action="/trade" method="post">

    <#list possessions as possession>
        <tr>
            <th scope="row">${possession.idPos}</th>
            <td>${possession.pokemon.name}</td>
            <td>${possession.level}</td>
            <td>${possession.getDateAcquiAsString()}</td>
            <td>${possession.datePerte!"JAMAIS"}</td>
            <td>
                <button value="${possession.idPos}" name="recipientPossessionID">Trade</button>
            </td>
        </tr>

    </#list>

</form>
</body>
</html>