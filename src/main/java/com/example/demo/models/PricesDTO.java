package com.example.demo.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricesDTO implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String country;

    @Indexed
    @DBRef
    private CompanyDTO owner;
}
