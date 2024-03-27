package hu.cry0.account.persistence.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "transaction")
class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID? = null

    @Column(name = "account_number", updatable = false, insertable = false)
    var accountNumber: Long? = null

    @ManyToOne
    @JoinColumn(name = "account_number")
    var account: AccountEntity? = null

    @Column(name = "transaction_type")
    var type: String? = null

    @Column(name = "amount", nullable = false)
    var amount: Long? = null

    @Column(name = "time_stamp", updatable = false)
    val timeStamp: Instant? = null
}