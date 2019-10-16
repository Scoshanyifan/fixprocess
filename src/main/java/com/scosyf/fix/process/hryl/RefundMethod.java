package com.scosyf.fix.process.hryl;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-25 16:40
 **/
@Document("refundmethod")
@Data
public class RefundMethod extends BaseEntity {


    /**
     * 返款方式名称
     */
    private String name;

    private String remark;

    @Override
    public String toString() {
        return "RefundMethod{" +
                "name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                "} " + super.toString();
    }
}
