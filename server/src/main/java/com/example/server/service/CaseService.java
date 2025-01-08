package com.example.server.service;

import com.example.server.repository.CaseRepository;
import org.springframework.stereotype.Service;

@Service
public class CaseService {
    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository){
        this.caseRepository = caseRepository;
    }
}
