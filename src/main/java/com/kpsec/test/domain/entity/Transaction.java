package com.kpsec.test.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kpsec.test.domain.code.CancelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "transaction",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "transaction_no"})}
)
@SequenceGenerator(
        name = "transaction_seq_generator",
        sequenceName = "transaction_seq",
        initialValue = 1, allocationSize = 1
)
public class Transaction extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq_generator")
    @Column(name = "transaction_id")
    private Long id;

    @Column(nullable = false)
    private String date;

    @Column(name = "account_no", nullable = false, length = 191)
    private String accountNo;

    @Column(name = "transaction_no", nullable = false, length = 191)
    private String transactionNo;

    @Digits(integer = 19, fraction = 4)
    private BigDecimal amount;

    @Digits(integer = 19, fraction = 4)
    private BigDecimal fee;

    @Enumerated(EnumType.STRING)
    @Column(name = "canceled")
    private CancelStatus cancelStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, referencedColumnName = "account_id")
    @JsonBackReference
    private Account account;

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
