package oceans.controller.info;

import oceans.model.CompanyIntroduction;
import oceans.model.dto.StatusMsgData;
import oceans.service.plain.CompanyIntroductionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/info")
public class CompanyIntroductionController {
    @Resource
    private CompanyIntroductionService companyIntroductionService;

    @GetMapping("/introduction")
    public ResponseEntity<StatusMsgData<CompanyIntroduction>> getCompanyIntroduction() {
        CompanyIntroduction companyIntroduction = companyIntroductionService.selectTheCompanyIntroduction();
        return new ResponseEntity<>(
                new StatusMsgData<>(companyIntroduction),
                HttpStatus.OK
        );
    }

    @PutMapping("/introduction")
    public ResponseEntity<StatusMsgData<String>> putCompanyIntroduction(CompanyIntroduction companyIntroduction) {
        companyIntroductionService.updateTheCompanyIntroduction(companyIntroduction);
        return new ResponseEntity<>(
                new StatusMsgData<>(StatusMsgData.OK_TIP, "修改成功！请前往官网确认！"),
                HttpStatus.OK
        );
    }
}
