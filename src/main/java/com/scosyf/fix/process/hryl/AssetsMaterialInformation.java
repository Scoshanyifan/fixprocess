package com.scosyf.fix.process.hryl;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-25 21:38
 **/
@Data
@Document("assetmaterialinformation")
public class AssetsMaterialInformation extends BaseEntity {

    /**
     * 资产代码
     */
    private String assetNO;

    /**
     * 资产名称
     */
    private String assetName;

    /**
     * 规格型号
     */
    private String model;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * 资产类别编号
     */
    private String assetsClassCode;

    /**
     * 资产类别Id
     */
    private String assetsClassId;

    /**
     * 资产类别名称
     */
    private String assetsClassName;

    /**
     * 供应商产品代码
     */
    private String vendorProductCode;

    /**
     * 供应商标识
     */
    private String vendorIdenfification;

    /**
     * 资产标牌号前缀
     */
    private String assetLabelPrefix;

    /**
     * 备注
     */
    private String remark;

    private String brandId;

    private String materielSortId;
}
