package com.scosyf.fix.process.hryl;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-25 21:31
 **/
@Data
@Document("assetsclass")
public class AssetsClass extends BaseEntity {

    /**
     * 资产类别编码
     */
    private String code;

    /**
     * 资产类别名称
     */
    private String name;

    /**
     * 父级ID
     */
    private String pId;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 备注
     */
    private String remark;
}
