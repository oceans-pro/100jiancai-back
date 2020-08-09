package oceans.controller.info;

import oceans.model.CompanyHistory;
import oceans.model.dto.StatusMsgData;
import oceans.service.plain.CompanyHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/info")
public class CompanyHistoryController {
    @Resource
    private CompanyHistoryService companyHistoryService;

    @GetMapping("/company_history")
    public ResponseEntity<StatusMsgData<List<CompanyHistory>>> getAll() {
        List<CompanyHistory> list = companyHistoryService.selectAll();
        return new ResponseEntity<>(
                new StatusMsgData<>(list),
                HttpStatus.OK
        );
    }

    @PostMapping("/company_history")
    public ResponseEntity<StatusMsgData<String>> postOne(CompanyHistory companyHistory) {
        companyHistory.setActive(true);
        companyHistory.setId(null);
        companyHistoryService.insertOne(companyHistory);
        return new ResponseEntity<>(
                new StatusMsgData<>(StatusMsgData.OK_TIP, "增加成功"),
                HttpStatus.OK
        );
    }

    @PutMapping("/company_history/{id}")
    public ResponseEntity<StatusMsgData<String>> putOne(@PathParam("id") Integer id, CompanyHistory companyHistory) {
        companyHistory.setActive(true);
        companyHistory.setId(id);
        companyHistoryService.updateOne(companyHistory);
        return new ResponseEntity<>(
                new StatusMsgData<>(StatusMsgData.OK_TIP, "修改成功"),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/company_history/{id}")
    public ResponseEntity<StatusMsgData<String>> deleteOne(@PathVariable("id") Integer id) {
        companyHistoryService.inactiveOne(id);
        return new ResponseEntity<>(
                new StatusMsgData<>(StatusMsgData.OK_TIP, "删除成功"),
                HttpStatus.OK
        );
    }
}
