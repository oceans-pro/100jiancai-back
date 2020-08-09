package oceans.service.api;

public interface OssService {
    /**
     * 根据BucketName返回Token
     *
     * @param bucket BucketName
     * @return 很长的Token
     */
    String getTokenByBucketName(String bucket);

    void deleteFileByKey(String bucket, String key);
}
