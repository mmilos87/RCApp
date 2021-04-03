package com.example.demo.helpers.classes;

import com.example.demo.entitety.TransfusionQuery;
import com.example.demo.helpers.enums.BloodTypes;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.demo.helpers.enums.BloodTypes.*;

public class RcTransfusionCompatibilityHelper {
  private final Map<BloodTypes, List<BloodTypes>> bloodTransfusionCompatibility =
      Map.of(
          A_NEGATIVE,
          Arrays.asList(O_NEGATIVE, A_NEGATIVE),
          A_POSITIVE,
          Arrays.asList(O_NEGATIVE, O_POSITIVE, A_POSITIVE, A_NEGATIVE),
          B_NEGATIVE,
          Arrays.asList(O_NEGATIVE, B_NEGATIVE),
          B_POSITIVE,
          Arrays.asList(O_NEGATIVE, O_POSITIVE, B_POSITIVE, B_NEGATIVE),
          AB_NEGATIVE,
          Arrays.asList(O_NEGATIVE, B_NEGATIVE, A_NEGATIVE, AB_NEGATIVE),
          AB_POSITIVE,
          Arrays.asList(BloodTypes.values()),
          O_NEGATIVE,
          Collections.singletonList(O_NEGATIVE),
          O_POSITIVE,
          Arrays.asList(O_NEGATIVE, O_POSITIVE));

  private final Map<BloodTypes, List<BloodTypes>> plasmaTransfusionCompatibility =
      Map.of(
          A_NEGATIVE,
          Arrays.asList(A_POSITIVE, A_NEGATIVE, AB_NEGATIVE, AB_POSITIVE),
          A_POSITIVE,
          Arrays.asList(A_POSITIVE, A_NEGATIVE, AB_NEGATIVE, AB_POSITIVE),
          B_NEGATIVE,
          Arrays.asList(B_NEGATIVE, B_POSITIVE, AB_NEGATIVE, AB_POSITIVE),
          B_POSITIVE,
          Arrays.asList(B_NEGATIVE, B_POSITIVE, AB_NEGATIVE, AB_POSITIVE),
          AB_NEGATIVE,
          Arrays.asList(AB_POSITIVE, AB_NEGATIVE),
          AB_POSITIVE,
          Arrays.asList(AB_POSITIVE, AB_NEGATIVE),
          O_NEGATIVE,
          Arrays.asList(values()),
          O_POSITIVE,
          Arrays.asList(values()));
  private final TransfusionQuery transfusionQuery;

  public RcTransfusionCompatibilityHelper(TransfusionQuery transfusionQuery) {
    this.transfusionQuery = transfusionQuery;
  }

  public List<BloodTypes> getCompatibleBloodTypes() {
    BloodTypes recipientBloodType = transfusionQuery.getRecipient().getBloodType();
    List<BloodTypes> compatibleTypes = Collections.EMPTY_LIST;
    switch (transfusionQuery.getTransfusionType()) {
      case BLOOD:
        compatibleTypes =
            transfusionQuery.getOnlyRecipientBloodType()
                ? Collections.singletonList(recipientBloodType)
                : bloodTransfusionCompatibility.get(recipientBloodType);
        break;
      case BLOOD_PLASMA:
        compatibleTypes =
            transfusionQuery.getOnlyRecipientBloodType()
                ? Collections.singletonList(recipientBloodType)
                : plasmaTransfusionCompatibility.get(recipientBloodType);
        break;
      case PLATELETS:
        compatibleTypes = Collections.singletonList(recipientBloodType);
        break;
    }
    return compatibleTypes;
  }

  public int getNumberOfDonorsToNotify() {
    return transfusionQuery.getRequiredUnits().intValue() + 3;
  }
}
