package com.example.demo.entitety;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DedicatedTransfusions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private RcTransfusion transfusion;
    @ManyToOne
    private AppUser recipient;
}
