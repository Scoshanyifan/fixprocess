package com.scosyf.fix.process.hryl;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-25 21:22
 **/
@Data
@Document("outlets")
public class Outlets extends BaseEntity {
    /**
     * 客户编码
     */
    private String clientCode;

    /**
     * 客户名称
     */
    private String clientName;


    private String clientId;


    // 行政区划 地区信息
    private String actualProvince;

    private String actualCity;

    private String actualRegion;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    private String region;
    /**
     * 名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contractPerson;


    private String address;

    private String phone;

    /**
     * 便利店名称
     */
    private String convenienceStore;

    private String custId;
}
