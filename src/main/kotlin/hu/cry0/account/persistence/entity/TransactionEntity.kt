package hu.cry0.account.persistence.entity

import jakarta.persistence.*
import org.hibernate.annotations.ResultCheckStyle
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "transaction")
@SQLDelete(sql = "update transaction SET status = 'DELETED' WHERE id = ? AND status <> 'DELETED'", check = ResultCheckStyle.COUNT)
@SQLRestriction("status <> 'DELETED'")
class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID? = null

    @Column(name = "account_number")
    var accountNumber: String? = null

    @ManyToOne
    @JoinColumn(name = "account_number", updatable = false, insertable = false)
    var account: AccountEntity? = null

    @Column(name = "transaction_type")
    var type: String? = null

    @Column(name = "amount", nullable = false)
    var amount: Long? = null

    @Column(name = "time_stamp", updatable = false)
    var timeStamp: Instant = Instant.now()
}