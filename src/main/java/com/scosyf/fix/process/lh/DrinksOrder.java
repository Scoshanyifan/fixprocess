package com.scosyf.fix.process.lh;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


public class DrinksOrder extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5336455706578625463L;

	private String deviceSN;

    private String imei;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.S")
    private Date orderTime;

    private Date payTime;

    private String drinkName;

    private String drinkCode;

    private String drinkId;

    private Double drinkPrice;
    
    private String drinkInfo;

    private Integer orderStatus;

    private Integer payType;

    private String payAccount;

    private String payInfo;

    private String payOrderNo;
    
    private String uId;

    private int port;

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

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
	public String toString() {
		return "{deviceSN:" + deviceSN + ", orderTime:" + orderTime
				+ ", payTime:" + payTime + ", drinkName:" + drinkName
				+ ", drinkCode:" + drinkCode + ", drinkId:" + drinkId
				+ ", drinkPrice:" + drinkPrice + ", drinkInfo:" + drinkInfo
				+ ", orderStatus:" + orderStatus + ", payType:" + payType
				+ ", payAccount:" + payAccount + ", payInfo:" + payInfo
				+ ", payOrderNo:" + payOrderNo + ", uId:" + uId + "}";
	}
	
}