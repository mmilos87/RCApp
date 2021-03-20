package com.example.demo.entitety;

import com.example.demo.helpers.enums.TransfusionTypes;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AllCompletedTransfusions {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime date;
  @Enumerated(EnumType.STRING)
  private TransfusionTypes type;
  @ManyToMany
  private List<RcUserRecipient> recipient;
  @ManyToMany
  private List<RcUserDonor> donor;
  @ManyToOne
  private HospitalUnit hospitalUnit;
  @OneToOne
  private RcUserMedic  rcUserMedic;

}
