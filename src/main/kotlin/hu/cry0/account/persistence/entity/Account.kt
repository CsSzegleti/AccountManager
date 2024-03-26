package hu.cry0.account.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.ResultCheckStyle
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "account")
@SQLDelete(sql = "update account SET status = 'DELETED' WHERE id = ? AND status <> 'DELETED'", check = ResultCheckStyle.COUNT)
@SQLRestriction("status <> 'DELETED")
class Account {

    @Id
    @Column(name = "accunt_number", unique = true)
    var accountNumber: Long? = null

    @Column(name = "account_holder_name")
    var accountHolderName: String? = null

    @OneToMany(mappedBy = "account", cascade = [CascadeType.DETACH])
    var transactions: List<Transaction> = listOf()

    var status: String? = null
}