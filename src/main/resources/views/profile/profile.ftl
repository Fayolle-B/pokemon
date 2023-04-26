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
nombre de possession : ${numberOfOwnership!"0"}

<table>
    <caption>Les possessions de ${user.firstName}</caption>
    <tr>
        <th scope="col">id de la possessions</th>
        <th scope="col">numero du PKMN</th>

    </tr>

    <#list ownerships as ownership>
        <tr>
            <th scope="row">${ownership.idPos}</th>
            <td>${ownership.numPok}</td>
        </tr>

    </#list>
    Possession : ${ownership!"null"};

</table>
</body>
</html>