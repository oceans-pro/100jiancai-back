package oceans.controller.info;

import oceans.model.Advantage;
import oceans.model.dto.StatusMsgData;
import oceans.service.plain.AdvantageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/info")
public class AdvantageController {
    @Resource
    private  AdvantageService service;

    /**
     * 查询全部
     */
    @GetMapping("/advantage")
    // @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<StatusMsgData<List<Advantage>>> getAll() {
        return ResponseEntity.ok(new StatusMsgData<>(service.findAllAdvantages()));
    }

    /**
     * 修改全部
     */
    @PutMapping("/advantage")
    public ResponseEntity<StatusMsgData<String>> putAll(@RequestBody List<Advantage> advantages) {
        for (Advantage advantage: advantages) {
            Integer id = advantage.getId();
            if (id == null) {
                throw new RuntimeException("需要advantage.id");
            }
        }
        service.updateAll(advantages);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "修改成功"));
    }

    /**
     * 修改一个
     */
    @PutMapping("/advantage/{id}")
    public ResponseEntity<StatusMsgData<String>> putOne(@PathParam("id") Integer id, Advantage advantage) {
        advantage.setId(id);
        service.updateOne(id, advantage);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "修改成功"));
    }
}
