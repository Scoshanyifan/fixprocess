package com.scosyf.fix.process.lh;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FixLhOrderTest {

    private static final Logger logger = LoggerFactory.getLogger(FixLhOrderTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testConnection() {

        long count = mongoTemplate.count(new Query(), "drinksorderhistory");
        logger.info(">>> mongo count:{}", count);

        List<Map<String, Object>> drinkOrderList = jdbcTemplate
                .queryForList("select * from drinksorder where orderTime <= '2019-10-15 19:21:29' and orderTime >= '2019-10-15 17:09:00'");
        logger.info(">>> drinkOrderList:{}", drinkOrderList.size());
//        logger.info(">>> drinkOrderList one:{}", drinkOrderList.get(0));

        List<Map<String, Object>> deviceList = jdbcTemplate
                .queryForList("select * from outputdevice where sn='868809040016912'");
        logger.info(">>> deviceList:{}", deviceList);

    }

    @Test
    public void fix() {

        List<Map<String, Object>> drinkOrderList = jdbcTemplate
                .queryForList("select * from drinksorder where orderTime <= '2019-10-15 19:21:29' and orderTime >= '2019-10-15 17:09:00'");
//        List<Map<String, Object>> drinkOrderList = jdbcTemplate
//                .queryForList("select * from drinksorder where id='6a0330b70cef400aaec2c4bcd7a4f901'");
        logger.info(">>> drinkOrderList:{}", drinkOrderList.size());

        if (CollectionUtils.isNotEmpty(drinkOrderList)) {
            int loop = 0;
            for (Map<String, Object> order : drinkOrderList) {
                logger.info(">>> loop:{}", loop++);
                // 已支付（未打杯）
                order.put("orderStatus", 2);
                //拼接insert
                StringBuilder insertSqlBuilder = new StringBuilder("insert into drinksorderhistory ");
                insertSqlBuilder.append("(");
                for (String key : order.keySet()) {
                    insertSqlBuilder.append(key).append(",");
                }
                insertSqlBuilder.deleteCharAt(insertSqlBuilder.length() - 1);
                insertSqlBuilder.append(") values (");
                for (Object value : order.values()) {
                    // 字符串和日期需要单引号
                    if (value instanceof String || value instanceof Date) {
                        insertSqlBuilder.append("'").append(value).append("'").append(",");
                    } else {
                        insertSqlBuilder.append(value).append(",");
                    }
                }
                insertSqlBuilder.deleteCharAt(insertSqlBuilder.length() - 1);
                insertSqlBuilder.append(")");

                String insertSql = insertSqlBuilder.toString();
                logger.info(">>> insertSql:{}", insertSql);
                // 插入历史库
                jdbcTemplate.execute(insertSql);

                // 删除预订单库
                String deleteSql = "delete from drinksorder where id='" + order.get("id") + "'";
                logger.info(">>> deleteSql:{}", deleteSql);
                jdbcTemplate.execute(deleteSql);

                // 插入mongo
                DrinksOrderHistoryMO orderHistoryMO = convertOrder(order);
                logger.info(">>> orderHistoryMO:{}", orderHistoryMO);
                mongoTemplate.insert(orderHistoryMO);
            }
        }


    }

    private DrinksOrderHistoryMO convertOrder(Map<String, Object> drinksOrder) {
        DrinksOrderHistoryMO drinksOrderHistoryMO = new DrinksOrderHistoryMO();
        drinksOrderHistoryMO.setId((String) drinksOrder.get("id"));
        drinksOrderHistoryMO.setDeviceSN((String) drinksOrder.get("deviceSN"));
        drinksOrderHistoryMO.setDrinkCode((String) drinksOrder.get("drinkCode"));
        drinksOrderHistoryMO.setDrinkId((String) drinksOrder.get("drinkId"));
        drinksOrderHistoryMO.setDrinkName((String) drinksOrder.get("drinkName"));
        drinksOrderHistoryMO.setDrinkPrice((Double) drinksOrder.get("drinkPrice"));
        drinksOrderHistoryMO.setOrderStatus((Integer) drinksOrder.get("orderStatus"));
        drinksOrderHistoryMO.setOrderTime(drinksOrder.get("orderTime") == null ? new Date() : (Date) drinksOrder.get("orderTime"));
        drinksOrderHistoryMO.setPayAccount((String) drinksOrder.get("payAccount"));
        drinksOrderHistoryMO.setPayOrderNo((String) drinksOrder.get("payOrderNo"));
        drinksOrderHistoryMO.setPayInfo((String) drinksOrder.get("payInfo"));
        drinksOrderHistoryMO.setPayTime(drinksOrderHistoryMO.getOrderTime());
        drinksOrderHistoryMO.setPayType((Integer) drinksOrder.get("payType"));
        drinksOrderHistoryMO.setPort((Integer) drinksOrder.get("port"));
        drinksOrderHistoryMO.setRemark((String) drinksOrder.get("remark"));
        drinksOrderHistoryMO.setStatus((Integer) drinksOrder.get("status"));
        drinksOrderHistoryMO.setCreateUser((String) drinksOrder.get("createUser"));
        drinksOrderHistoryMO.setModifyUser((String) drinksOrder.get("modifyUser"));
        drinksOrderHistoryMO.setCreateDate((Date) drinksOrder.get("createDate"));
        drinksOrderHistoryMO.setModifyDate(new Date());
        drinksOrderHistoryMO.setStatus(1);
        drinksOrderHistoryMO.setVersion(1);

        List<Map<String, Object>> deviceList = jdbcTemplate
                .queryForList("select * from outputdevice where sn='" + drinksOrder.get("deviceSN") + "'");
        if (CollectionUtils.isNotEmpty(deviceList)) {
            Map<String, Object> device = deviceList.get(0);
            drinksOrderHistoryMO.setAgencyId((String) device.get("agencyId"));
            drinksOrderHistoryMO.setTerminalId((String) device.get("terminal"));
            drinksOrderHistoryMO.setThirdGradeId((String) device.get("thirdGrade"));
            drinksOrderHistoryMO.setImei((String) device.get("imei"));
        }

        return drinksOrderHistoryMO;
    }

}
