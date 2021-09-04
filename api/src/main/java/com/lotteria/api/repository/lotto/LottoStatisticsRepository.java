package com.lotteria.api.repository.lotto;

import com.lotteria.api.entity.lotto.LottoStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LottoStatisticsRepository extends JpaRepository<LottoStatistics, Integer> {
    Optional<LottoStatistics> findByNum(int num);
    List<LottoStatistics> findAll();
}
