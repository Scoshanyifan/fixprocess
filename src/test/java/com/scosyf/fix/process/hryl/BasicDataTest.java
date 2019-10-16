package com.scosyf.fix.process.hryl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicDataTest {

    private static final Logger logger = LoggerFactory.getLogger(BasicDataTest.class);

    @Autowired
    private JdbcTemplate sqlServerTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testConnection() {

        Integer assetsCount = sqlServerTemplate.queryForObject("select count(*) from HRMS_YLYTN.dbo.BAsset", Integer.class);
        logger.info(">>> sqlServer assetsCount:{}", assetsCount);

        List<RefundMethod> refundMethodList = mongoTemplate.findAll(RefundMethod.class);
        logger.info(">>> refundMethodList:{}", refundMethodList);
    }

    @Test
    public void refundMethod() {
        List<Map<String, Object>> refundMethodList =
                sqlServerTemplate.queryForList("select * from HRMS_YLYTN.dbo.LShouldBeBackType");
        if (refundMethodList != null) {
            logger.info(">>> refundMethodList size:{}", refundMethodList.size());
        } else {
            logger.warn(" === refundMethodList null");
            return;
        }

        List<RefundMethod> list = new ArrayList<>();
        for (Map<String, Object> map : refundMethodList) {
            RefundMethod refundMethod = new RefundMethod();
            refundMethod.setOldId(map.get("id").toString());

            refundMethod.setName((String) map.get("shouldBeBackNm"));
            refundMethod.setRemark((String) map.get("mark"));
            refundMethod.setStatus(1);
            refundMethod.setCreateTime((Date) map.get("isrtDt"));

            list.add(refundMethod);
        }
        mongoTemplate.insert(list, RefundMethod.class);
    }

    @Test
    public void clientType() {
        List<Map<String, Object>> clientTypeList =
                sqlServerTemplate.queryForList("select * from HRMS_YLYTN.dbo.LCustSort");
        if (clientTypeList != null) {
            logger.info(">>> clientTypeList size:{}", clientTypeList.size());
        } else {
            logger.warn(" === clientTypeList null");
            return;
        }

        List<ClientType> list = new ArrayList<>();
        for (Map<String, Object> map : clientTypeList) {
            ClientType clientType = new ClientType();
            clientType.setOldId(map.get("id").toString());

            clientType.setCode((String) map.get("custSortNo"));
            clientType.setName((String) map.get("custSortNm"));
            clientType.setRemark((String) map.get("mark"));
            clientType.setStatus(1);
            clientType.setCreateTime((Date) map.get("isrtDt"));

            list.add(clientType);
        }
        mongoTemplate.insert(list, ClientType.class);
    }

    @Test
    public void brand() {
        List<Map<String, Object>> brandList =
                sqlServerTemplate.queryForList("select * from HRMS_YLYTN.dbo.LBrand");
        if (brandList != null) {
            logger.info(">>> brandList size:{}", brandList.size());
        } else {
            logger.warn(" === brandList null");
            return;
        }

        List<Brand> list = new ArrayList<>();
        for (Map<String, Object> map : brandList) {
            Brand brand = new Brand();
            brand.setOldId(map.get("id").toString());

            brand.setCode((String) map.get("brandNo"));
            brand.setName((String) map.get("brandNm"));

            brand.setRemark((String) map.get("mark"));
            brand.setStatus(1);
            brand.setCreateTime((Date) map.get("isrtDt"));
            list.add(brand);
        }
        mongoTemplate.insert(list, Brand.class);
    }

    @Test
    public void assetsClass() {
        List<Map<String, Object>> assetsclassList =
                sqlServerTemplate.queryForList("select * from HRMS_YLYTN.dbo.LFixedMaterielSort");
        if (assetsclassList != null) {
            logger.info(">>> assetsclassList size:{}", assetsclassList.size());
        } else {
            logger.warn(" === assetsclassList null");
            return;
        }

        List<AssetsClass> list = new ArrayList<>();
        for (Map<String, Object> map : assetsclassList) {
            AssetsClass assetsClass = new AssetsClass();
            assetsClass.setOldId(map.get("id").toString());

            assetsClass.setCode((String) map.get("sortNo"));
            assetsClass.setName((String) map.get("sortNm"));
            String parentSortId = map.get("parentSortId").toString();
            if (!"0".equals(parentSortId)) {
                assetsClass.setPId(parentSortId);
                assetsClass.setLevel(2);
            } else {
                assetsClass.setPId("");
                assetsClass.setLevel(1);
            }
            assetsClass.setRemark((String) map.get("mark"));

            assetsClass.setStatus(1);
            assetsClass.setCreateTime((Date) map.get("isrtDt"));
            list.add(assetsClass);
        }
        mongoTemplate.insert(list, AssetsClass.class);
    }

    @Test
    public void handleAssetsClass() {

        List<AssetsClass> list = mongoTemplate.findAll(AssetsClass.class);

        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, String> oldId2ObjectIdMap = list
                    .stream()
                    .filter(x -> StringUtils.isBlank(x.getPId()))
                    .collect(Collectors.toMap(x -> x.getOldId(), x -> x.getId()));
            logger.info(">>> oldId2ObjectIdMap size:{}", oldId2ObjectIdMap.size());
            for (AssetsClass ac : list) {
                String oldPId = ac.getPId();
                if (StringUtils.isNotBlank(oldPId)) {
                    ac.setPId(oldId2ObjectIdMap.get(oldPId));
                }
                mongoTemplate.save(ac);
            }
        }
    }

    @Test
    public void deviceUseState() {
        List<Map<String, Object>> deviceUseStateList =
                sqlServerTemplate.queryForList("select * from HRMS_YLYTN.dbo.LFixedMaterielUseState");
        if (deviceUseStateList != null) {
            logger.info(">>> deviceUseStateList size:{}", deviceUseStateList.size());
        } else {
            logger.warn(" === deviceUseStateList null");
            return;
        }

        List<DeviceUseState> list = new ArrayList<>();
        for (Map<String, Object> map : deviceUseStateList) {
            DeviceUseState deviceUseState = new DeviceUseState();
            deviceUseState.setOldId(map.get("id").toString());

            deviceUseState.setCode(1);
            deviceUseState.setDefaultValue(false);
            deviceUseState.setName((String) map.get("stateNm"));

            deviceUseState.setRemark((String) map.get("mark"));
//            deviceUseState.setStatus(1);
            deviceUseState.setCreateTime((Date) map.get("isrtDt"));
            list.add(deviceUseState);
        }
        mongoTemplate.insert(list, DeviceUseState.class);
    }



}
