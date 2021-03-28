package com.example.demo.entitety;

import com.example.demo.helpers.enums.TransfusionTypes;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class RcTransfusion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime date;
  @Enumerated(EnumType.STRING)
  private TransfusionTypes type;
  @ManyToOne
  private RcUserDonor donor;
  @ManyToOne
  private HospitalUnit hospitalUnit;
  @ManyToOne
  private RcUserMedic  rcUserMedic;
  @Builder.Default
  private Boolean isDedicated = false;
}
