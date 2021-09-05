package com.lotteria.api.service.pension;

import com.lotteria.api.repository.pension.PensionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PensionHistoryService {

    @Autowired
    private PensionHistoryRepository pensionHistoryRepository;


}
