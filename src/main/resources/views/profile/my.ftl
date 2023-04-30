<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Mon profil</title>
</head>
<body>
<h1>Mon profil : </h1>

Prénom  : ${user.getFirstName()}
Nom : ${user.getLastName()}
nombre de possession : ${numberOfPossessions!"0"}

<table>
    <caption>Mes possessions</caption>
    <tr>
        <th scope="col">id de la possessions</th>
        <th scope="col">numero du Pokemon</th>
        <th scope="col">Niveau du pokémon</th>
        <th scope="col">Date d'acuisition</th>


    </tr>

    <#list possessions as possession>
        <tr>
            <th scope="row">${possession.idPos}</th>
            <td>${possession.pokemon.name}</td>
            <td>${possession.level}</td>
            <td>${possession.getDateAcquiAsString()}</td>
        </tr>

    </#list>


</table>
</body>
</html>