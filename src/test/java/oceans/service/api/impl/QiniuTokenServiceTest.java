package oceans.service.api.impl;

import oceans.constant.ApiConst;
import org.junit.jupiter.api.Test;


public class QiniuTokenServiceTest {
    static QiniuTokenService qiniuTokenService = new QiniuTokenService();

    @Test
   public void testDelete() {
        qiniuTokenService.deleteFileByKey(ApiConst.QINIU_BUCKET_ADMIN, "boy.jpg");
    }
}
