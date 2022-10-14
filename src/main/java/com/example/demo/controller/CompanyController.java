package com.example.demo.controller;


import com.example.demo.models.CompanyDTO;
import com.example.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("company")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<String> save(@RequestBody CompanyDTO company) {
        return companyRepository.save(company).map(g -> "Saved: " + g.getId());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Mono<CompanyDTO> get(@PathVariable("id") String id) {
        return companyRepository.findById(id);
    }

    @GetMapping(produces = "application/json")
    public Flux<CompanyDTO> get() {
        return companyRepository.findAll();
    }


    @PutMapping(value = "/{id}", produces = "application/json")
    public Mono<CompanyDTO> update(@PathVariable("id") long id, @RequestBody CompanyDTO company) {
        companyRepository.save(company);
        return Mono.just(company);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public Mono<CompanyDTO> delete(@PathVariable("id") String id) {
        return companyRepository.findById(id);
    }
}