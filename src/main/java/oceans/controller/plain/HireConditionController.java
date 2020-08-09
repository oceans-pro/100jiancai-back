package oceans.controller.plain;

import oceans.model.AppConfig;
import oceans.model.HireCondition;
import oceans.model.dto.StatusMsgData;
import oceans.service.plain.AppConfigService;
import oceans.service.plain.HireConditionService;
import oceans.utils.JsonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hire")
public class HireConditionController {
    @Resource
    private HireConditionService hireConditionService;
    @Resource
    private AppConfigService appConfigService;

    /**
     * 获取全部的招聘信息
     */
    @GetMapping
    public ResponseEntity<StatusMsgData<List<HireCondition>>> getAll() {
        return ResponseEntity.ok(new StatusMsgData<>(hireConditionService.selectAll()));
    }

    /**
     * 获取有效的招聘信息
     */
    @GetMapping("/active")
    public ResponseEntity<StatusMsgData<List<HireCondition>>> getAllActive() {
        return ResponseEntity.ok(new StatusMsgData<>(hireConditionService.selectSome()));
    }

    /**
     * 获取是否开启了招聘
     */
    @GetMapping("/config")
    public ResponseEntity<StatusMsgData<Integer>> getHireConfig() {
        Map map = appConfigService.getOneByName("hire");
        Boolean shouldHire = (Boolean)map.get("hire");
        if (shouldHire) {
            return ResponseEntity.ok(new StatusMsgData<>(1));
        } else {
            return ResponseEntity.ok(new StatusMsgData<>(0));
        }
    }

    /**
     * 修改是否开启招聘
     */
    @PutMapping("/switch")
    public ResponseEntity<StatusMsgData<String>> switchHire() {
        Map configMap = appConfigService.getOneByName("hire");
        boolean shouldHire = !(boolean)configMap.get("hire"); // 修改招聘
        configMap.put("hire", shouldHire);
        appConfigService.updateOne("hire", configMap);
        if (shouldHire) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "招聘功能开启成功"));
        } else {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "招聘功能关闭成功"));
        }
    }

    /**
     * 删除一条招聘信息
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<StatusMsgData<String>> deleteOne(@PathVariable("id") Integer id) {
        hireConditionService.deleteOne(id);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "删除成功"));
    }

    /**
     * 增加一条招聘信息
     */
    @PostMapping
    public ResponseEntity<StatusMsgData<String>> postOne(HireCondition hireCondition) {
        hireCondition.setId(null);
        hireConditionService.insertOne(hireCondition);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "增加成功"));
    }

    /**
     * 修改一条招聘信息
     */
    @PutMapping
    public ResponseEntity<StatusMsgData<String>> putOne(HireCondition hireCondition) {
        hireConditionService.updateOne(hireCondition);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "修改成功"));
    }

}
