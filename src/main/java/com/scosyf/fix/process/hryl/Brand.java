package com.scosyf.fix.process.hryl;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-25 21:50
 **/
@Data
@Document("brand")
public class Brand extends BaseEntity {

    /**
     * 品牌编码
     */
    private String code;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
