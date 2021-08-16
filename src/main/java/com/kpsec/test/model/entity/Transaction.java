package com.kpsec.test.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kpsec.test.model.code.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "transaction_no"})}
)
public class Transaction extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "account_no", nullable = false, referencedColumnName = "account_no")
    @JsonBackReference
    private Account account;

    @Column(name = "transaction_no")
    private Long transactionNo;

    @Digits(integer = 19, fraction = 4)
    private BigDecimal amount;

    @Digits(integer = 19, fraction = 4)
    private BigDecimal fee;

    @Enumerated(EnumType.STRING)
    @Column(name = "canceled")
    private TransactionStatus transactionStatus;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Transaction)) {
            return false;
        }
        Transaction that = (Transaction) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
