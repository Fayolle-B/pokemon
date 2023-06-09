<#-- @ftlvariable name="recipientPossession" type="com.uca.entity.PossessionEntity" -->
<#-- @ftlvariable name="possessions" type="java.util.Collection<com.uca.entity.PossessionEntity>" -->
<#-- @ftlvariable name="user" type="com.uca.entity.UserEntity" -->
<#-- @ftlvariable name="recipientPossessionID" type="java.lang.Number" -->
<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/style.css">

    <title>Confirmer échange | PokeMy</title>
</head>
<body>
<h1>Choisir l'une de vos  possessions à envoyer</h1>

<form method="post" action="/trade">
    <input type="hidden" name="recipientPossessionID" value="${recipientPossession.idPos}">
    <table>
        <caption>Les possessions de ${user.firstName}</caption>
        <tr>
            <th scope="col">id de la possessions</th>
            <th scope="col">Nom du PKMN</th>
            <th scope="col">Niveau du pokémon</th>
            <th scope="col">Date d'acuisition</th>

        </tr>

        <#list possessions as possession>
            <tr>
                <th scope="row">${possession.idPos}</th>
                <td>${possession.pokemon.name}</td>
                <td>${possession.level}</td>
                <td>${possession.getDateAcquiAsString()}</td>
                <td>
                    <label for="${possession.idPos}"></label>
                    <input type="radio" name="applicantPossessionID" id="${possession.idPos}" value="${possession.idPos}">

            </tr>

        </#list>
    </table>
    <button type="submit">Valider</button>
</form>
<a href="/myProfile" class="button">Retourner à mon profil</a>

</body>
</html>
