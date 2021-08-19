package com.kpsec.test.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "branch",
        indexes = {@Index(name = "idx_branch", unique = true, columnList = "branch_code")}
)
@NaturalIdCache
@SequenceGenerator(
        name = "branch_seq_generator",
        sequenceName = "branch_seq",
        initialValue = 1, allocationSize = 1
)
public class Branch extends Base implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_seq_generator")
    @Column(name = "branch_id")
    private Long id;

    @NaturalId
    @Column(name = "branch_code", unique = true, nullable = false, length = 191)
    private String branchCode;

    @Column(name = "branch_name", length = 191)
    private String branchName;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Branch)) {
            return false;
        }
        Branch that = (Branch) obj;
        return Objects.equals(branchCode, that.branchCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchCode);
    }
}
