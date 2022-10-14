package com.example.demo.repository;

import com.example.demo.models.CompanyDTO;
import com.example.demo.models.PricesDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends ReactiveMongoRepository<CompanyDTO, String> {
}