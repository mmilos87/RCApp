package com.example.demo.controlers;

import com.example.demo.services.RcUserMedicService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/update")
@AllArgsConstructor
public class UpdateController extends MainController {
    private RcUserMedicService medicService;





//jwt test
    @PostMapping(value = "/test")
    public String upDate(){return "work";}
}
