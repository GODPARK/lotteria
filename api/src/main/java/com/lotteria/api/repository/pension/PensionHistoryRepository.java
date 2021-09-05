package com.lotteria.api.repository.pension;

import com.lotteria.api.entity.pension.PensionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PensionHistoryRepository extends JpaRepository<PensionHistory, Long> {
    Optional<PensionHistory> findByRoundNum(long roundNum);
    Optional<PensionHistory> findFirstByOrderByRoundNumDesc();
    boolean existsByRoundNum(long roundNum);
}
