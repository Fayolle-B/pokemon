
<table>
    <caption>Les possessions de ${user.firstName}</caption>
    <tr>
        <th scope="col">id de la possessions</th>
        <th scope="col">numero du PKMN</th>
        <th scope="col">Niveau du pokémon</th>
        <th scope="col">Date d'acuisition</th>
        <th scope="col">Date de perte</th>

    </tr>

    <#list possessions as possession>
    <tr>
        <th scope="row">${possession.idPos}</th>
        <td>${possession.pokemon.name}</td>
        <td>${possession.level}</td>
        <td>${possession.getDateAcquiAsString()}</td>
        <td>${possession.datePerte!"En votre possession"}</td>
    </tr>

</#list>