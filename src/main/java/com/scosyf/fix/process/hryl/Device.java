package com.scosyf.fix.process.hryl;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-27 16:03
 **/
@Document("device")
@Data
public class Device extends BaseEntity {

    /**
     * 快速巡检
     */
    private String major;

    private String minor;

    /**
     * 资产编号
     */
    private String assetNumber;

    /**
     * 条形码
     */
    private String deviceNumber;

    /**
     * RFID
     */
    private String chipNumber;

    /**
     * RFID2
     */
    private String rfid2;

    /**
     * IMSI卡号
     */
    private String imsi;

    /**
     * GPRS冷柜终端号
     */
    private String terminalSN;

    /**
     * 厂家代码
     */
    private String companyCode;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 设备类型
     */
    private String freezerType;

    /**
     * 设备型号
     */
    private String freezerModel;

    /**
     * 品牌编码
     */
    private String brandCode;

    private String brandName;

    private String brandId;

    /**
     * 资产属性
     */
    private String assetProperties;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 生产日期
     */
    private String productTime;

    /**
     * 生产厂商
     */
    private String manufacturer;


    /**
     * 大区
     */
    private String region;

    /**
     * 区域
     */
    private String address;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String county;

    // 行政区划 地区信息
    private String actualProvince;

    private String actualCity;

    private String actualRegion;

    /**
     * 设备运行状态 1正常 2故障
     */
    private int deviceStatus;

    /**
     * 租赁状态 1活跃 2 过期
     */
    private int leaseState;

    /**
     * 冷柜状态 0库存 1在库 2出库 3在用
     */
    private int deviceState;

    /**
     * 上一次冷柜状态 0库存 1在库 2出库 3在用
     */
    private int lastDeviceState;

    /**
     * 投放门店地址
     */
    private String shopAddress;

    /**
     * 投放客户名称
     */
    private String ontoCustomerName;

    /**
     * 责任业代
     */
    private String responsible;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 入库时间
     */
    private Date inTime;

    /**
     * 出库时间
     */
    private Date outTime;

    /**
     * 入库地址
     */
    private String inAddress;

    /**
     * 出库地址
     */
    private String outAddress;


    private String datumAddress;// 基准地址

    private int deviation;// 偏移距离

    private float temp1;//当前温度1
    private float temp2;//当前温度2
    private float temp3;//当前温度3

    private boolean online;//是否在线

    /**
     * 巡检类型
     */
    private Integer inspectionType;

    /**
     * 设备使用状态
     */
    private Integer useStatus;

    private Date locationTime;

    private String locationAddress;// 上报的地址

    private int voltage;//电压

    private int flowRate;   //人流统计

    /**
     * 电源 "1"市电   "0"断电
     */
    private String power;//电源（市电或备用）

    private boolean quake;//异动标识

    private Date lastOnline;    //设备最后一次上线时间


    private Date lastStopTime;//上次停止时间


    /**
     * 标示在库与在用
     * true 在库 false 再用
     */
    private boolean isStock;

    /**
     * 资产物料Id
     */
    private String assetMaterialInformationId;

    /**
     * 资产代码
     */
    private String assetNO;

    private String assetName;

    private String assetClassName;

    /**
     * 投放年份
     */
    private String putOnYear;


    /**
     * 一级渠道ID
     */
    private String firstChannelInformationId;
    private String firstChannelInformationName;

    /**
     * 二级渠道ID
     */
    private String secondChannelInformationId;
    private String secondChannelInformationName;

    /**
     * 三级渠道ID
     */
    private String thirdChannelInformationId;
    private String thirdChannelInformationName;

    /**
     * 资产台账ID
     */
    private String assetLedgerId;


    /**
     * 客户代码
     */
    private String clientCode;


    private String clientId;

    /**
     * 网点Id
     */
    private String outletsId;

    /**
     * 网点名
     */
    private String outletsName;

    /**
     * 网点电话
     */
    private String outletsPhone;

    /**
     * 网点联系人
     */
    private String outletsContractPerson;

    /**
     * 网点地址
     */
    private String outletsAddress;

    /**
     * 便利店名称
     */
    private String convenienceStore;

    /**
     * 是否大客户导入
     */
    private boolean customerImport;

    /**
     * 供应商产品代码
     */
    private String vendorProductCode;

    /**
     * 发货日期
     */
    private Date deliveryTime;

    /**
     * 发货地址
     */
    private String deliveryAddress;

    private String assetPropertiesId;


    /**
     * 备注
     */
    private String remark;

    /**
     * 上次巡检时间
     */
    private Date lastInspectionTime;

    /**
     * 上次巡检人
     */
    private String lastInspectionPerson;



    /**
     * 设备激活时间
     */
    private Date activeTime;

    /**
     * 是否巡检过
     */
    private Boolean inspected;

    @Ignore
    private String refundPolicyName;

    private String deptId;

    /**
     * 负责业务员
     */
    @Ignore
    private String salesman;

    /**
     * 登记单号
     */
    private String registrationNumber;

    /**
     * 审核状态
     */
    private Boolean auditStatus;

    /**
     * 是否有手工录入权限
     */
    private Boolean manualInputPermission;

    private Integer leftDoorMagneticAlarm;        //左门磁告警，null表示没有左门磁，0表示正常，1表示超时告警，2表              示门磁传感器故障，在平台显示"M0"
    private Integer leftDoorMagneticAlarmTime; //左门磁告警时长，小时
    private Integer rightDoorMagneticAlarm;     //右门磁告警，null表示没有左门磁，0表示正常，1表示超时告警，2表示门磁传感器故障，在平台显示"M0"
    private Integer rightDoorMagneticAlarmTime;     //右门磁告警时长，小时

    private Integer leftDoorOpenTiems;            //左门开合次数总和
    private Integer rightDoorOpenTiems;            //右门开合次数总和

    private Boolean deviationAlarm;    //偏移告警
    private Integer tempAlarm;        //温度告警标识， 0:正常  1:低温告警  2:高温报警
    private Boolean tempSensorFault;    //温度传感器是否故障，false表示正常，true表示故障，温度值显示"E0"
    private Boolean defrostSensorFault;        //除霜传感器是否故障，null表示没有除霜传感器，false表示正常，true表示故障，显示"E1"

    /**
     * 信号强度
     */
    private Integer signal;

    private Boolean needPositionAlarm;	//是否需要偏移告警
    private Date lastPowerOnTime;		//最后一次上电时间

    /**
     * 是否异常
     */
    private Boolean abnormal;

    private String custId;
    private String fixedMaterielId;
    private String useStateOldId;
    private String useStateId;

    private Date assetRegTime;

    private Date auditTime;

    private String auditStatusStr;

    /** 登记来源 */
    private String dataSource;

    private String scanType;

}
