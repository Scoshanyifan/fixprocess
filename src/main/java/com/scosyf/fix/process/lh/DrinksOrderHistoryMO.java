package com.scosyf.fix.process.lh;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author 刘宸玮
 * @date 2018/8/6
 */
@Document(collection = "drinksorderhistory")
public class DrinksOrderHistoryMO extends BaseMongoEntity {

    @Id
    private String  id;
    /**
     * 设备sn
     */
    private String  deviceSN;

    /**
     * 下单时间
     */
    private Date    orderTime;

    /**
     * 支付时间
     */
    private Date    payTime;

    /**
     * 一级用户
     */
    private String  agencyId;

    /**
     * 二级用户
     */
    private String  terminalId;

    /**
     * 三级用户
     */
    private String  thirdGradeId;

    /**
     * 饮料名称
     */
    private String  drinkName;

    /**
     * 饮料编号
     */
    private String  drinkCode;

    /**
     * 饮料id
     */
    private String  drinkId;

    /**
     * 饮料价格
     */
    private Double  drinkPrice;

    /**
     * 饮料信息
     */
    private String  drinkInfo;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 支付类型
     */
    private Integer payType;

    /**
     * 支付账号
     */
    private String  payAccount;

    /**
     * 支付信息
     */
    private String  payInfo;

    /**
     * 支付订单号
     */
    private String  payOrderNo;

    /**
     * 饮料口
     */
    private int     port;

    /**
     * imei
     */
    private String  imei;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getThirdGradeId() {
        return thirdGradeId;
    }

    public void setThirdGradeId(String thirdGradeId) {
        this.thirdGradeId = thirdGradeId;
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN == null ? null : deviceSN.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName == null ? null : drinkName.trim();
    }

    public String getDrinkCode() {
        return drinkCode;
    }

    public void setDrinkCode(String drinkCode) {
        this.drinkCode = drinkCode == null ? null : drinkCode.trim();
    }

    public String getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId == null ? null : drinkId.trim();
    }

    public Double getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(Double drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public String getDrinkInfo() {
        return drinkInfo;
    }

    public void setDrinkInfo(String drinkInfo) {
        this.drinkInfo = drinkInfo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount == null ? null : payAccount.trim();
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo == null ? null : payInfo.trim();
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public String toString() {
        return "DrinksOrderHistoryMO{" +
                "id='" + id + '\'' +
                ", deviceSN='" + deviceSN + '\'' +
                ", orderTime=" + orderTime +
                ", payTime=" + payTime +
                ", agencyId='" + agencyId + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", thirdGradeId='" + thirdGradeId + '\'' +
                ", drinkName='" + drinkName + '\'' +
                ", drinkCode='" + drinkCode + '\'' +
                ", drinkId='" + drinkId + '\'' +
                ", drinkPrice=" + drinkPrice +
                ", drinkInfo='" + drinkInfo + '\'' +
                ", orderStatus=" + orderStatus +
                ", payType=" + payType +
                ", payAccount='" + payAccount + '\'' +
                ", payInfo='" + payInfo + '\'' +
                ", payOrderNo='" + payOrderNo + '\'' +
                ", port=" + port +
                ", imei='" + imei + '\'' +
                "} " + super.toString();
    }
}
