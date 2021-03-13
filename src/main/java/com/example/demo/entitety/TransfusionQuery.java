package com.example.demo.entitety;

import com.example.demo.helpers.enums.TransfusionTypes;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class TransfusionQuery {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne
  private RcUserRecipient recipient;
  @OneToOne
  private RcUserMedic rcUserMedic;
  @Enumerated(EnumType.STRING)
  private TransfusionTypes transfusionType;
  private Long requiredUnits;
}
