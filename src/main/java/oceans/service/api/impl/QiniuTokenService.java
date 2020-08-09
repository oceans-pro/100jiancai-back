package oceans.service.api.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import oceans.constant.ApiConst;
import oceans.service.api.OssService;
import org.springframework.stereotype.Service;

/**
 * https://developer.qiniu.com/kodo/sdk/1283/javascript
 */
@Service
@Slf4j
public class QiniuTokenService implements OssService {
    static private final String accessKey = ApiConst.QINIU_ACCESSKEY;
    static private final String secretKey = ApiConst.QINIU_SECRETKEY;
    static private final Configuration cfg = new Configuration(Region.huabei());
    static private final UploadManager uploadManager = new UploadManager(cfg);
    static private final long expireSeconds = 3600L;
    static private final Auth auth = Auth.create(accessKey, secretKey);
    BucketManager bucketManager = new BucketManager(auth, cfg);
    static private final StringMap putPolicy = new StringMap();
    // -- 可选
    static private final String adminBucket = ApiConst.QINIU_BUCKET_ADMIN;
    static private final String publicBucket = ApiConst.QINIU_BUCKET_PUBLIC;

    static {
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"size\":$(size)}");
    }

    @Override
    public String getTokenByBucketName(String bucket) {
        if (adminBucket.equals(bucket) || publicBucket.equals(bucket)) {
            return auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        }
        throw new RuntimeException("bucket不存在");
    }

    @Override
    public void deleteFileByKey(String bucket, String key) {
        try {
            if (adminBucket.equals(bucket) || publicBucket.equals(bucket)) {
                Response response = bucketManager.delete(bucket, key);
                log.info(response.bodyString());
                return;
            }
            throw new RuntimeException("bucket不存在");
        } catch (QiniuException e) {
            log.error("删除失败", e);
        }
    }
}
