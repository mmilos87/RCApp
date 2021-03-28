package com.example.demo.controlers;

import com.example.demo.entitety.*;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.models.*;
import com.example.demo.services.AppUserService;
import com.example.demo.services.RcDonorService;
import com.example.demo.services.RcUserMedicService;
import com.example.demo.services.TransfusionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "api/transfusion")
@AllArgsConstructor
public class TransfusionController extends MainController {

    private final RcUserMedicService rcUserMedicService;
    private final TransfusionService transfusionService;
    private final RcDonorService donorService;
    private final AppUserService appUserService;

  // create new query
  @PostMapping(value = "/query")
  public TransfusionQuery createNewQuery(
      @RequestBody RequestTransfusionQuery transfusionQuery, HttpServletRequest httpServletRequest)
      throws JmbgIsNotValidException {
    RcUserMedic medic = rcUserMedicService.extractAppUserFromRequest(httpServletRequest);
    AppUser recipient = appUserService.getOrCreateAppUser(transfusionQuery.getRecipient());
    TransfusionQuery query =
        transfusionService.createOrUpdateTransfusionQuery(
            recipient,
            transfusionQuery.getTransfusionType(),
            transfusionQuery.getRequiredUnits(),
            medic);
    return query;
  }

  // non dedicated transfusion
  @PostMapping(value = "/nondedicated")
  public RcTransfusion nonDedicatedTransfusion(
      @RequestBody RequestTransfusion requestTransfusion, HttpServletRequest httpServletRequest)
      throws JmbgIsNotValidException {
    RcUserMedic medic = rcUserMedicService.extractAppUserFromRequest(httpServletRequest);
    RcUserDonor donor = donorService.getOrCreateDonor(requestTransfusion.getDonor());
    RcTransfusion rcTransfusion =
        transfusionService.nonDedicatedTransfusionCompleted(
            medic, donor, requestTransfusion.getTransfusionType());
    return rcTransfusion;
  }

  // non dedicated transfusion
  @PostMapping(value = "/dedicated")
  public DedicatedTransfusions dedicatedTransfusion(
          @RequestBody DedicatedTransfusionRequest dedicatedTransfusionRequest,
          HttpServletRequest httpServletRequest)
      throws JmbgIsNotValidException {
    RcUserMedic medic = rcUserMedicService.extractAppUserFromRequest(httpServletRequest);
    RcUserDonor donor = donorService.getOrCreateDonor(dedicatedTransfusionRequest.getDonor());
    DedicatedTransfusions dedicatedTransfusions =
        transfusionService.dedicatedTransfusionCompleted(
            dedicatedTransfusionRequest.getTransfusionQueryId(), medic, donor);
    return dedicatedTransfusions;
  }

  //rejected transfusion
  @PostMapping(value = "/rejected")
  public RejectedTransfusions rejectedTransfusion(
          @RequestBody RejectedTransfusionRequest rejectedTransfusionRequest,
          HttpServletRequest httpServletRequest)
          throws JmbgIsNotValidException {
    RcUserMedic medic = rcUserMedicService.extractAppUserFromRequest(httpServletRequest);
    RcUserDonor donor = donorService.getOrCreateDonor(rejectedTransfusionRequest.getDonor());
    RejectedTransfusions rejectedTransfusions =
        transfusionService.rejectedTransfusionsSave(
            rejectedTransfusionRequest.getType(),
            medic,
            donor,
            rejectedTransfusionRequest.getNote());
    return rejectedTransfusions;
  }


}
