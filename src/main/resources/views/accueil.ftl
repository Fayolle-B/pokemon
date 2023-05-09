<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Accueil  | PokeMy</title>
</head>
<body>
Bonjour :
<a class="button" id="register" href="/register">S'inscrire</a>
<a class="button" id="login" href="/login">Se  connecter</a>
<#if errorMessage?? >
    <p>${errorMessage}</p>
</#if>

</body>
</html>