package oceans.service.api;

public interface MapService {
    /**
     * @param ip ipv4地址
     * @return 城市信息
     */
    String getCityInfo(String ip);
}
