<#-- @ftlvariable name="receivedPendingTrades" type="java.util.Collection<com.uca.entity.TradeEntity>" -->

<#-- @ftlvariable name="proposedPendingTrades" type="java.util.Collection<com.uca.entity.TradeEntity>" -->
<#-- @ftlvariable name="user" type="com.uca.entity.UserEntity" -->
<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/style.css">
    <title>Les échanges de ${user.firstName}</title>
</head>
<body>

<table>
    <caption>Les échanges que vous avez proposé</caption>
    <tr>
        <th scope="col"> ID de l'échange</th>
        <th scope="col">Status de l'échange</th>
        <th scope="col">ID de la possession du demandeur</th>
        <th scope="col">ID de la possession du receveur</th>

    </tr>
    <#list  proposedPendingTrades as trade>
        <tr id="trade#${trade_index}">
            <td id="id">
                ${trade.getId()}
            </td>
            <td id="status"> ${trade.status}</td>
            <td id="applicantPossession">${trade.applicantPossession.idPos}</td>
            <td id="recipientPossession"> ${trade.recipientPossession.idPos}</td>
            <td>
            </td>
        </tr>

    </#list>
</table>

<table>
    <caption>Les échanges que vous avez reçu</caption>
    <tr>
        <th scope="col"> ID de l'échange</th>
        <th scope="col">Status de l'échange</th>
        <th scope="col"> Vous recevrez</th>
        <th scope="col"> Vous envoyez</th>

    </tr>
    <#list  receivedPendingTrades as trade>
        <tr id="trade#${trade_index}">
            <td id="id">
                ${trade.getId()}
            </td>
            <td id="status"> ${trade.status}</td>
            <td id="applicantPossession">${trade.applicantPossession}</td>
            <td id="recipientPossession"> ${trade.recipientPossession}</td>
            <td>

                <form id="trade-form" action="/profile/trades/accept" method="post">
                    <input type="hidden" name="tradeID" value="${trade.id}">

                    <button type="submit">Accepter l'échange </button>
                </form>
            </td>
        </tr>

    </#list>
</table>


</body>
</html>