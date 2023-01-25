class Client(
    val id: Int, val taxNumber: String, val name: String
) {
    override fun toString(): String {
        return "ID: $id, RUT: $taxNumber, Name: $name"
    }
}
open class Bank(
    val id: Int,
    val name: String,
) {
    override fun toString(): String {
        return "ID: $id, Name: $name"
    }
}
class Account(
    val clientId: Int,
    val bankId: Int,
    val balance: Int,
) {
    override fun toString(): String {
        return "ClientID: $clientId, BankId: $bankId, balance: $balance"
    }
}
class Challenge {
    private val banks = listOf(
        Bank(1, "SANTANDER"),
        Bank(2, "CHILE"),
        Bank(3, "ESTADO"),
    )
    private val clients = listOf(
        Client(1, "86620855", "HECTOR ACUÑA BOLAÑOS"),
        Client(2, "7317855K", "JESUS RODRIGUEZ ALVAREZ"),
        Client(3, "73826497", "ANDRES NADAL MOLINA"),
        Client(4, "88587715", "SALVADOR ARNEDO MANRIQUEZ"),
        Client(5, "94020190", "VICTOR MANUEL ROJAS LUCAS"),
        Client(6, "99804238", "MOHAMED FERRE SAMPER"),
    )
    private var accounts = listOf(
        Account(6, 1, 15000),
        Account(1, 3, 18000),
        Account(5, 3, 135000),
        Account(2, 2, 5600),
        Account(3, 1, 23000),
        Account(5, 2, 15000),
        Account(3, 3, 45900),
        Account(2, 3, 19000),
        Account(4, 3, 51000),
        Account(5, 1, 89000),
        Account(1, 2, 1600),
        Account(5, 3, 37500),
        Account(6, 1, 19200),
        Account(2, 3, 10000),
        Account(3, 2, 5400),
        Account(3, 1, 9000),
        Account(4, 3, 13500),
        Account(2, 1, 38200),
        Account(5, 2, 17000),
        Account(1, 3, 1000),
        Account(5, 2, 600),
        Account(6, 1, 16200),
        Account(2, 2, 10000),
    )
  fun listClientIds() {
    val clientIds = clients.map { it.id }
    println(clientIds)
}

fun listClientsIdsSortByTaxNumber() {
    val clientIds = clients.sortedBy { it.taxNumber }.map { it.id }
    println(clientIds)
}

fun sortClientsTotalBalances() {
    val clientBalances = clients.associateWith { client ->
        accounts.filter { it.clientId == client.id }
            .sumBy { it.balance }
    }
    val sortedClients = clientBalances.toList().sortedByDescending { (_, balance) -> balance }
        .map { (client, _) -> client.name }
    println(sortedClients)
}

fun banksClientsTaxNumbers() {
    val bankClients = banks.associateWith { bank ->
        accounts.filter { it.bankId == bank.id }
            .map { client -> clients.find { it.id == client.clientId } }
            .filterNotNull()
            .sortedBy { it.name }
            .map { it.taxNumber }
    }
    println(bankClients)
}

fun richClientsBalances() {
    val richClients = accounts.filter { it.bankId == 1 && it.balance > 25000 }
        .map { clients.find { it.id == it.clientId } }
        .filterNotNull()
        .sortedByDescending { it.balance }
        .map { it.balance }
    println(richClients)
}

fun banksRankingByTotalBalance() {
    val bankBalances = banks.associateWith { bank ->
        accounts.filter { it.bankId == bank.id }
            .sumBy { it.balance }
    }
    val sortedBanks = bankBalances.toList().sortedBy { (_, balance) -> balance }
        .map { (bank, _) -> bank.id }
    println(sortedBanks)
}

fun banksFidelity() {
    val bankClients = accounts.groupBy { it.bankId }
        .mapValues { (_, accounts) ->
            accounts.map { it.clientId }
                .toSet()
                .size
        }
    val bankFidelity = banks.associateWith { bank ->
        bankClients[bank.id]
    }
    println(bankFidelity)
}

    // 7 Objeto en que las claves sean los nombres de los bancos y los valores el id de su cliente con menos dinero.
    fun banksPoorClients() {
    val bankClients = accounts.groupBy { it.bankId }
        .mapValues { (_, accounts) ->
            accounts.minBy { it.balance }?.clientId
        }
    val bankPoorClients = banks.associateWith { bank ->
        val clientId = bankClients[bank.id]
        if (clientId != null) {
            val poorClient = clients.find { it.id == clientId }
            poorClient?.id
        } else null
    }
    println(bankPoorClients)
}

    /*
        8 Agregar nuevo cliente con datos ficticios a "clientes" y agregar una cuenta en el BANCO ESTADO con un saldo de 9000 para este nuevo empleado.
        Luego devolver el lugar que ocupa este cliente en el ranking de la pregunta 2.
        No modificar arreglos originales para no alterar las respuestas anteriores al correr la solución
    */
   fun newClientRanking() {
    val newClient = Client(7, "12345678", "NEW CLIENT")
    val newAccount = Account(newClient.id, 3, 9000)
    val newClients = clients + newClient
    val newAccounts = accounts + newAccount
    val clientBalances = newClients.associateWith { client ->
        newAccounts.filter { it.clientId == client.id }
            .sumBy { it.balance }
    }
    val sortedClients = clientBalances.toList().sortedByDescending { (_, balance) -> balance }
    val newClientRanking = sortedClients.indexOfFirst { it.first.id == newClient.id } + 1
    println("The new client's ranking is: $newClientRanking")
  }
}
fun main() {
    val challenge = Challenge()
    println("Pregunta 1")
    val p0 = challenge.listClientIds()
    println(p0)
    println("Pregunta 2")
    val p1 = challenge.listClientsIdsSortByTaxNumber()
    println(p1)
    println("Pregunta 3")
    val p2 = challenge.sortClientsTotalBalances()
    println(p2)
    println("Pregunta 4")
    val p3 = challenge.banksClientsTaxNumbers()
    println(p3)
    println("Pregunta 5")
    val p4 = challenge.richClientsBalances()
    println(p4)
    println("Pregunta 6")
    val p5 = challenge.banksRankingByTotalBalance()
    println(p5)
    println("Pregunta 7")
    val p6 = challenge.banksFidelity()
    println(p6)
    println("Pregunta 8")
    val p7 = challenge.banksPoorClients()
    println(p7)
    println("Pregunta 9")
    val p8 = challenge.newClientRanking()
    println(p8)
}