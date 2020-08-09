package oceans.controller.plain;

import oceans.model.News;
import oceans.model.dto.StatusMsgData;
import oceans.service.plain.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @see TagController
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    private  NewsService service;

    @GetMapping("/{id}")
    public ResponseEntity<StatusMsgData<News>> getOnePage(@PathVariable Integer id) {
        return ResponseEntity.ok(new StatusMsgData<>(service.selectOne(id)));
    }

    /**
     * 获取新闻的分页数据
     * @param num 第几页
     * @param size 页的大小
     */
    @GetMapping
    public ResponseEntity<StatusMsgData<Page<News>>> getSomePages(Integer num, Integer size) {
        Page<News> newsPage = service.getPage(num - 1, size); // jpa从0开始 前端从1开始
        return ResponseEntity.ok(new StatusMsgData<>(newsPage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusMsgData<String>> deleteOne(@PathVariable Integer id) {
        service.deleteOne(id);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "删除成功"));
    }

    @PostMapping
    public ResponseEntity<StatusMsgData<String>> postOne(@RequestBody News news) {
        service.insertOne(news);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "添加成功"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusMsgData<String>> putOne(@PathVariable("id") Integer id, @RequestBody News news) {
        service.updateOne(id, news);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "修改成功"));
    }
}
