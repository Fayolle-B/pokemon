<#-- @ftlvariable name="additionalLinks" type="java.util.Collection<com.uca.ErrorPages>" -->

<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="HTTPErrorCode" type="java.lang.Number" -->
<#-- @ftlvariable name="comment" type="java.lang.String" -->
<#-- @ftlvariable name="title" type="java.lang.String" -->
<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/style.css">

    <title>${title} | PokeMy</title>
</head>

<body>
<header>

    <h1>
        ${HTTPErrorCode}
    </h1>
</header>
<main>
    <p>
        ${message}

    </p>
    <#if additionalLinks??>
        <#list additionalLinks as additionalLink>
            <a class="additionalLink"
               href=${additionalLink.uri}  id=<#list additionalLink.ids as id>${id}</#list>><b>

                    ${additionalLink.text}
                </b>
            </a>
        </#list>
    </#if>
    <a href="/">
        <b>

            Retourner à l'accueil
        </b>
    </a>
</main>
<footer>
    <#include "footer.ftl">
</footer>
</body>
</html>