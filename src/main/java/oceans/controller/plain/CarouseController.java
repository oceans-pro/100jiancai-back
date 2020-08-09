package oceans.controller.plain;

import oceans.model.Carousel;
import oceans.model.dto.StatusMsgData;
import oceans.service.special.CarouselService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/info/carousel")
public class CarouseController {
    @Resource
    private CarouselService carouselService;

    /**
     * 获取全部的轮播图
     */
    @GetMapping
    public ResponseEntity<StatusMsgData<List<Carousel>>> getAll() {
        return ResponseEntity.ok(new StatusMsgData<>(carouselService.selectAll()));
    }

    /**
     * 删除轮播图
     */
    @DeleteMapping("/{name}")
    @Transactional
    public ResponseEntity<StatusMsgData<String>> deleteOne(@PathVariable("name") String name) {
        int count = carouselService.deleteOne(name);
        if (count > 0) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "删除轮播成功"));
        } else {
            return ResponseEntity.ok(new StatusMsgData<>("没有什么可以删除的"));
        }
    }

    /**
     * 增加轮播图
     */
    @PostMapping
    public ResponseEntity<StatusMsgData<String>> postOne(Carousel carousel) {
        carouselService.insertOne(carousel);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "增加轮播成功"));
    }
}
