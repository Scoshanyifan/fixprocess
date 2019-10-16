package com.scosyf.fix.process.hryl;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-25 22:12
 **/
@Data
@Document("deviceusestatus")
public class DeviceUseState extends BaseEntity {

    private Integer code;

    private String name;

    private Boolean defaultValue;

    private String remark;
}
