package com.scosyf.fix.process.hryl;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-25 21:56
 **/
@Data
@Document("area")
public class Area extends BaseEntity {

    /** 自身id */
    private String aId;

    /** 父id */
    private String pId;

    /** 名称 */
    private String name;

    /** 0:大区,1:省,2：城市,3:县/区 */
    private int type;

    /** 是否有效 */
    private boolean deleted;

    /** 是否已有用户选择该区域 */
    private boolean select;

    /** 是否为大客户自己添加 */
    private boolean userAdd;

}
