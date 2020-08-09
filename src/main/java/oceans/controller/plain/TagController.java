package oceans.controller.plain;

import lombok.RequiredArgsConstructor;
import oceans.model.Tag;
import oceans.model.dto.StatusMsgData;
import oceans.service.plain.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @see NewsController
 */
@Controller
@RequestMapping("/news/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService service;

    @GetMapping
    public ResponseEntity<StatusMsgData<List<Tag>>> getAllTags() {
        return ResponseEntity.ok(new StatusMsgData<>(service.selectAllTags()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusMsgData<String>> deleteOneTag(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "删除标签成功"));
    }

    @PostMapping
    public ResponseEntity<StatusMsgData<String>> postOneTag(String value) {
        service.insertOne(value);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "添加标签成功"));
    }
}
