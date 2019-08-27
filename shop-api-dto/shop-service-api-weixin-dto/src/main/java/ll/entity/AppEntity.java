package ll.entity;

import lombok.Data;

/**
 * @description: 微信应用实体类
 * @author: LL
 * @create: 2019-08-27 14:58
 */
@Data
public class AppEntity {

    private String appId;

    private String appName;

    public AppEntity(String appId, String appName) {
        super();
        this.appId = appId;
        this.appName = appName;
    }
}