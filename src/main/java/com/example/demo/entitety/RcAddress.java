package com.example.demo.entitety;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class RcAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserCity userCity;
    private String township;
    private Long postalCodeZip;
    private String street;
    private String number;

}
