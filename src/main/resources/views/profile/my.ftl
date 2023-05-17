<#-- @ftlvariable name="user" type="com.uca.entity.UserEntity" -->
<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/style.css">

    <title>Mon profil | PokeMy</title>
</head>
<body>
<header>

    <#include "../menu.ftl">
    <h1>Mon profil : </h1>
</header>

<main>
    <section>

<aside>
    <h3>
    Prénom
    </h3>
    <p>${user.getFirstName()}</p>

    <h3>
    Nom
    </h3>
    <p>
    ${user.getLastName()}
    </p>
</aside>
<aside>
<h3>Nombre de points restants</h3>
    <p>

    ${user.points}
    </p>
</aside>
    </section>
    <section>

    <#include "possessions.ftl">
    </section>


    <a href="/profile/${user.id}/trades"><b>

        Mes échanges
        </b>
    </a>

</main>
</body>
</html>
