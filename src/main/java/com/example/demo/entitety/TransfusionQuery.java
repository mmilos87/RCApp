package com.example.demo.entitety;

import com.example.demo.helpers.enums.TransfusionTypes;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class TransfusionQuery {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private AppUser recipient;
  @ManyToOne
  private HospitalUnit hospitalUnit;
  @OneToOne
  private RcUserMedic rcUserMedic;
  @Enumerated(EnumType.STRING)
  private TransfusionTypes transfusionType;
  private Long requiredUnits;
  @Column(nullable = false)
  private LocalDateTime createdAt;

}
