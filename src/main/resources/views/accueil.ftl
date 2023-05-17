<#-- @ftlvariable name="links" type="com.uca.Pages[]" -->
<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/style.css">
    <title>Accueil | PokeMy</title>
    <meta name="description"
          content="PokeMy est un site web où vous pouvez échanger des Pokémons avec d'autres personnes, en gagner un tous les jours et les entraîner.">
</head>
<body>
<header>
    <h1>Bienvenue sur PokeMy</h1>
</header>
<main>
    <section>
        <h2>Présentation</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid debitis doloremque excepturi ipsam quam
            unde!
            Et
            eveniet harum, labore molestias nemo nobis, nostrum officia officiis quibusdam, repudiandae sit temporibus
            tenetur.</p>
        <p>A aperiam consequatur, cum eos, esse est fuga ipsam iste modi, neque praesentium velit vero voluptas.
            Adipisci
            debitis eaque facilis incidunt itaque laborum laudantium nihil, obcaecati praesentium quidem repudiandae
            sequi!</p>
    </section>
    <section>

        <#list links as link>
            <aside>

                <a class="link" href=${link.uri}  id=<#list link.ids as id>${id}</#list>>
                    <b>

                        ${link.text}
                    </b>
                </a>
            </aside>
        </#list>
    </section>
    <#if errorMessage?? >
        <p>${errorMessage}</p>
    </#if>
</main>
</body>
</html>
