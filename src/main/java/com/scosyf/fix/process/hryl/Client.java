package com.scosyf.fix.process.hryl;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-25 21:06
 **/
@Data
@Document("client")
public class Client extends BaseEntity {

    /**
     * 客户编码
     */
    private String code;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 客户类别
     */
    private String typeCode;

    /**
     * 联系人
     */
    private String contractPerson;

    /**
     * 负责部门
     */
    private String deptId;

    // 部门管理相关信息

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String county;

    /**
     * 大区
     */
    private String region;

    // 行政区划 地区信息
    private String actualProvince;

    private String actualCity;

    private String actualRegion;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 传真
     */
    private String fax;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 负责业务员
     */
    private String salesman;

    /**
     * 负责业务员
     */
//    @RefList(cascade = "R")
//    private List<Admin> admins;

    private String remark;
}
