package com.kpsec.test.repository.statistics;

import com.kpsec.test.domain.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
}
