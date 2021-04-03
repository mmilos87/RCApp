package com.example.demo.entitety;

import lombok.*;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class NotifiedRcDonors {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne
  private RcUserDonor donor;
  @ManyToOne
  private TransfusionQuery transfusionQuery;
  @Column(nullable = false)
  private LocalDateTime notifiedDate;
  private LocalDateTime confirmDate;
}
