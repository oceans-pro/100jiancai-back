package oceans.controller.special;

import oceans.constant.ApiConst;
import oceans.model.dto.StatusMsgData;
import oceans.service.api.OssService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Resource
    private OssService ossService;

    @GetMapping("/upload/{bucket}")
    @PreAuthorize("hasAnyAuthority('token:get','product:mod')")
    public ResponseEntity<StatusMsgData<String>> getUploadToken(@PathVariable("bucket") String bucket) {
        for (String item: ApiConst.QINIU_BUCKET_LIST) {
            if (bucket.equals(item)) {
                String token = ossService.getTokenByBucketName(item);
                return ResponseEntity.ok(new StatusMsgData<>(token));
            }
        }
        return ResponseEntity.ok(new StatusMsgData<>("不存在的bucket"));
    }

    @GetMapping("/baidu_map")
    @PreAuthorize("hasAnyAuthority('token:get','visitor:get')")
    public ResponseEntity<StatusMsgData<String>> getBaiduToken() {
        return ResponseEntity.ok(new StatusMsgData<>(ApiConst.BAIDU_AK));
    }
}
