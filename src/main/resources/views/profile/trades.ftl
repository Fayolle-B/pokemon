<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Les échanges de ${user.firstName}</title>
</head>
<body>

<table>
    <caption>Les trades</caption>
    <tr>
        <th scope="col"> ID de l'échange</th>
        <th scope="col">Status de l'échange</th>
        <th scope="col">ID de la possession du demandeur</th>
        <th scope="col">ID de la possession du receveur</th>

    </tr>
    <#list  trades as trade>
        <tr id="trade#${trade_index}">
            <td id="id">
                ${trade.getId()}
            </td>
            <td id="status"> ${trade.status}</td>
            <td id="applicantPossession">${trade.applicantPossession.idPos}</td>
            <td id="recipientPossession"> ${trade.recipientPossession.idPos}</td>
            <td>

                <form id="trade-form" action="/profile/${user.id}/trade/accept" method="post">
                    <input type="hidden" name="tradeID" value="${trade.id}">

                    <button type="submit">Make Trade</button>
                </form>
            </td>
        </tr>

    </#list>
</table>


</body>
</html>