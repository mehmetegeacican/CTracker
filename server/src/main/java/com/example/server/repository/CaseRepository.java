package com.example.server.repository;

import com.example.server.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CaseRepository extends MongoRepository<Report,String> {
}
