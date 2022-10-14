package com.example.demo.controller;


import com.example.demo.models.CompanyDTO;
import com.example.demo.models.PricesDTO;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.PriceRepository;
import com.example.demo.rest.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("price")
public class PriceController {

    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private CompanyRepository companyRepository;


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<String> save(@RequestBody Price price) {
        PricesDTO priceDto = PricesDTO.builder()
                .country(price.getCountry())
                .build();
        priceDto.setOwner(CompanyDTO.builder().id(price.getOwnerId()).build());
        return priceRepository.save(priceDto).map(g -> "Saved: " + g.getId());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Mono<PricesDTO> get(@PathVariable("id") String id) {
        return priceRepository.findById(id)
                .flatMap( pricesDTO ->
                        Mono.just(pricesDTO)
                                .zipWith(companyRepository.findById(pricesDTO.getOwner().getId()),
                                        (u, p) -> {
                                            u.setOwner(p);
                                            return u;
                                        })
                );

    }

    @GetMapping(produces = "application/json")
    public Flux<PricesDTO> get() {
        return priceRepository.findAll();
    }


    @PutMapping(value = "/{id}", produces = "application/json")
    public Mono<PricesDTO> update(@PathVariable("id") long id, @RequestBody Price price) {
        PricesDTO priceDto = PricesDTO.builder()
                .country(price.getCountry())
                .build();
        priceDto.setOwner(CompanyDTO.builder().id(price.getId()).build());
        priceRepository.save(priceDto);
        return Mono.just(priceDto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public Mono<PricesDTO> delete(@PathVariable("id") String id) {
        return priceRepository.findById(id);
    }
}