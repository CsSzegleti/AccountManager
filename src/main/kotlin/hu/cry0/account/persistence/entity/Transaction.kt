package hu.cry0.account.persistence.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "transaction")
class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID? = null

    @Column(name = "account_number", updatable = false, insertable = false)
    var accountNumber: Long? = null

    @ManyToOne
    @JoinColumn(name = "account_number")
    var account: Account? = null

    @Column(name = "transaction_type")
    var type: TransactionType? = null

    @Column(name = "amount")
    var amount: Long? = null

    @Column(name = "time_stamp")
    val timeStamp: Instant? = null
}