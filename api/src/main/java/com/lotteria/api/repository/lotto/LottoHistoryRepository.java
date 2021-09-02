package com.lotteria.api.repository.lotto;

import com.lotteria.api.entity.lotto.LottoHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LottoHistoryRepository extends JpaRepository<LottoHistory, Long> {
    Optional<LottoHistory> findByRoundNum(long roundNum);
    Optional<LottoHistory> findFirstByOrderByRoundNumDesc();
}
