package com.scosyf.fix.process.hryl;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-25 17:19
 **/
@Data
@Document("clienttype")
public class ClientType extends BaseEntity {

    /**
     * 客户类别编码
     */
    private String code;

    /**
     * 客户类别名称
     */
    private String name;

    private String remark;
}
