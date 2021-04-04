package com.example.demo.services;

import com.example.demo.entitety.NotifiedRcDonors;
import com.example.demo.entitety.RcUserDonor;
import com.example.demo.entitety.TransfusionQuery;
import com.example.demo.helpers.classes.RcTransfusionCompatibilityHelper;
import com.example.demo.models.EmailMessage;
import com.example.demo.repos.NotifiedRCDonorsRepository;
import com.example.demo.repos.RcUserDonorsRepository;
import com.example.demo.repos.TransfusionQueryRepository;
import com.example.demo.repos.UserCItyRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static com.example.demo.helpers.enums.TransfusionTypes.*;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
  private final EmailService emailService;
  private final TransfusionQueryRepository queryRepository;
  private final RcUserDonorsRepository donorsRepository;
  private final NotifiedRCDonorsRepository notifiedRCDonorsRepository;
  private final UserCItyRepository cItyRepository;

  public String initialNotifications(TransfusionQuery transfusionQuery) {
    RcTransfusionCompatibilityHelper compatibilityHelper =
        new RcTransfusionCompatibilityHelper(transfusionQuery);
    Pageable page = PageRequest.of(0, compatibilityHelper.getNumberOfDonorsToNotify());
    donorsRepository
        .findByBloodType(
            compatibilityHelper.getCompatibleBloodTypes(),
            transfusionQuery.getHospitalUnit().getAddress().getUserCity(),
            LocalDateTime.now().minusDays(BLOOD.getDaysToNextGiving()),
            LocalDateTime.now().minusDays(PLATELETS.getDaysToNextGiving()),
            LocalDateTime.now().minusDays(BLOOD_PLASMA.getDaysToNextGiving()),
            transfusionQuery.getRecipient(), page)
        .get()
        .stream()
        .map(
            donor ->
                NotifiedRcDonors.builder()
                    .donor(donor)
                    .transfusionQuery(transfusionQuery)
                    .notifiedDate(LocalDateTime.now())
                    .build())
        .forEach(this::sendMsgAndUpdateRepos);

    return null;
  }

  @Transactional
  private void sendMsgAndUpdateRepos(NotifiedRcDonors notifiedRCDonor) {
    TransfusionQuery transfusionQuery = notifiedRCDonor.getTransfusionQuery();
    transfusionQuery.setNumberOfNotifiedDonors(transfusionQuery.getNumberOfNotifiedDonors() + 1);
    RcUserDonor donor = notifiedRCDonor.getDonor();
    emailService.send(
        EmailMessage.builder()
            .subject("obavestenje") // todo napraviti poruku i ost
            .message("poruka sa linkom")
            .to(donor.getAppUser().getEmail())
            .toHtml(true)
            .build());
    donor.setSentNotification(true);
    donorsRepository.save(donor);
    queryRepository.save(transfusionQuery);
    notifiedRCDonorsRepository.save(notifiedRCDonor);
  }

}
