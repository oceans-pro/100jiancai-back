package oceans.controller.plain;

import lombok.RequiredArgsConstructor;
import oceans.model.ProductSimple;
import oceans.model.dto.StatusMsgData;
import oceans.service.plain.SimpleProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final SimpleProductService service;

    @GetMapping
    public ResponseEntity<StatusMsgData<List<ProductSimple>>> getAll() {
        List<ProductSimple> list = service.selectAll();
        return ResponseEntity.ok(new StatusMsgData<>(list));
    }

    @GetMapping("/type")
    public ResponseEntity<StatusMsgData<List<ProductSimple>>> getSome(@RequestParam("type") Integer type) {
        List<ProductSimple> list = service.selectSome(type);
        return ResponseEntity.ok(new StatusMsgData<>(list));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusMsgData<String>> postOne(@PathVariable("id") Integer id) {
        service.deleteOne(id);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "删除成功"));
    }

    @PostMapping
    public ResponseEntity<StatusMsgData<String>> postOne(ProductSimple product) {
        product.setId(null);
        service.insertOne(product);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "增加成功"));
    }


    @PutMapping
    public ResponseEntity<StatusMsgData<String>> putOne(ProductSimple product) {
        if (StringUtils.isEmpty(product.getId())) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN, "传入参数未带id"));
        }
        service.insertOne(product);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "修改成功"));
    }
}
