package com.kpsec.test.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "account",
        indexes = {@Index(name = "idx_account", unique = true, columnList = "account_no")}
)
@SequenceGenerator(
        name = "account_seq_generator",
        sequenceName = "account_seq",
        initialValue = 1, allocationSize = 1
)
public class Account extends Base implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_generator")
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_no", unique = true, nullable = false, length = 191)
    private String accountNo;

    @Column(name = "account_name", length = 191)
    private String accountName;

    @Column(name = "branch_code", length = 191)
    private String branchCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, referencedColumnName = "branch_id")
    @JsonBackReference
    private Branch branch;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        Account that = (Account) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
