<#-- @ftlvariable name="connectedUser" type="com.uca.entity.UserEntity" -->
<#-- @ftlvariable name="oldPossessions" type="java.util.Collection<com.uca.entity.PossessionEntity>" -->
<#-- @ftlvariable name="activePossessions" type="java.util.Collection<com.uca.entity.PossessionEntity>" -->
<#-- @ftlvariable name="user" type="com.uca.entity.UserEntity" -->

<table>
    <caption>Possessions actuelle </caption>
    <tr>
        <th scope="col">id de la possessions</th>
        <th scope="col">Nom du Pokémon</th>
        <th scope="col">Niveau du pokémon</th>
        <th scope="col">Date d'acuisition</th>
        <th scope="col">Date de perte</th>

    </tr>

    <#list activePossessions as possession>
        <tr>
            <th scope="row">${possession.idPos}</th>
            <td>${possession.pokemon.name}</td>
            <td>${possession.level}</td>
            <td>${possession.getDateAcquiAsString()}</td>
            <td>${possession.datePerte!"En votre possession"}</td>
            <td>

                <form action="/pex" method="post">
                    <button value="${possession.idPos}" name="possessionID" <#if (connectedUser.points==0)>disabled style="color: red;background-color: darkgray; cursor: not-allowed" </#if>>Pex</button>
                </form>
            </td>
        </tr>

    </#list>
</table>
<table>
    <caption>Les possessions archivés de ${user.firstName}</caption>
    <tr>
        <th scope="col">id de la possessions</th>
        <th scope="col">Nom du PKMN</th>
        <th scope="col">Niveau du pokémon</th>
        <th scope="col">Date d'acuisition</th>
        <th scope="col">Date de perte</th>

    </tr>

    <#list oldPossessions as possession>
        <tr>
            <th scope="row">${possession.idPos}</th>
            <td>${possession.pokemon.name}</td>
            <td>${possession.level}</td>
            <td>${possession.getDateAqui()}</td>
            <td>${possession.datePerte!"En votre possession"}</td>

        </tr>

    </#list>
</table>
