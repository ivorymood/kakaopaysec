package com.kpsec.test.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "statistics",
        indexes = {
                @Index(name = "idx_statistics", unique = true, columnList = "year, account_no")
        }
)
@SequenceGenerator(
        name = "statistics_seq_generator",
        sequenceName = "statistics_seq",
        initialValue = 1, allocationSize = 1
)
public class Statistics extends Base implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_statistics_seq_generator")
    @Column(name = "account_statistics_id")
    private Long id;

    @Column(nullable = false)
    private String year;

    @Column(name = "branch_code", nullable = false, length = 191)
    private String branchCode;

    @ManyToOne
    @JoinColumn(name = "account_no", nullable = false, referencedColumnName = "account_no")
    @JsonBackReference
    private Account account;

    @Digits(integer = 19, fraction = 4)
    @Column(name = "net_amount_sum")
    private BigDecimal netAmountSum;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Statistics)) {
            return false;
        }
        Statistics that = (Statistics) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
