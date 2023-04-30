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
<#include "../menu.ftl">
<h1>Mon profil : </h1>


Prénom  : ${user.getFirstName()}
Nom : ${user.getLastName()}
<#include "possessions.ftl">

<a href="/profile/${user.id}/trades"> Mes échanges </a>

</body>
</html>