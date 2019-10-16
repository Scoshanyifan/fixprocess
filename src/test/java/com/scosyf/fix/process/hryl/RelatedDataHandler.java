package com.scosyf.fix.process.hryl;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @project: sqlserver
 * @author: kunbu
 * @create: 2019-09-26 10:04
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RelatedDataHandler {

    private static final Logger logger = LoggerFactory.getLogger(RelatedDataHandler.class);

    @Autowired
    private JdbcTemplate sqlServerTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void area() {
        List<Map<String, Object>> areaList =
                sqlServerTemplate.queryForList("select * from HRMS_YLYTN.dbo.HOrgnization");
        if (areaList != null) {
            logger.info(">>> areaList size:{}", areaList.size());
        } else {
            logger.warn(" === areaList null");
            return;
        }

        List<Area> list = new ArrayList<>();
        for (Map<String, Object> map : areaList) {
            Area area = new Area();

            area.setOldId(map.get("departId").toString());
            area.setName((String) map.get("departNm"));

            // 0:大区,1:省,2：城市
            String departNo = (String) map.get("departNo");
            if (departNo.length() == 2) {
//                continue;

                area.setType(0);
                //一级，父id=10   >>> 110 / 120 / 130
                area.setPId("10");
                int num = Integer.parseInt(departNo);
                int aId = 100 + num * 10;
                area.setAId(Integer.toString(aId));
            } else if (departNo.length() == 4) {
//                continue;

                area.setType(1);
                //二级，父id=110 >>> 110 001 / 120 002
                int num = Integer.parseInt(departNo); // 0102
                int delta1 = num / 100; // 1
                int delta2 = num % 100; // 2

                int pId = delta1 * 10 + 100;
                int aId = pId * 1000 + delta2;
                area.setPId(Integer.toString(pId));
                area.setAId(Integer.toString(aId));
            } else if (departNo.length() == 6) {
//                continue;

                area.setType(2);
                // 010102
                //二级，父id=110 >>> 110 001 002
                int num6 = Integer.parseInt(departNo); // 10102
                int num4 = Integer.parseInt(departNo.substring(2)); // 102

                int delta1 = num6 / 100_00; // 1
                int delta2 = num4 / 100; // 1
                int delta3 = num4 % 100; // 2

                int ppId = delta1 * 10 + 100; // 110
                int pId = ppId * 1000 + delta2; // 110 001
                int aId = pId * 1000 + delta3; // 110 001 000 + 2
                area.setPId(Integer.toString(pId));
                area.setAId(Integer.toString(aId));
            }
            area.setCreateTime((Date) map.get("isrtDt"));
            list.add(area);
        }
        mongoTemplate.insert(list, Area.class);
    }


    @Test
    public void client() {
        List<Map<String, Object>> clientList =
                sqlServerTemplate.queryForList("select * from HRMS_YLYTN.dbo.LCust");
        if (clientList != null) {
            logger.info(">>> clientList size:{}", clientList.size());
        } else {
            logger.warn(" === clientList null");
            return;
        }

        List<ClientType> clientTypeList = mongoTemplate.findAll(ClientType.class);
        Map<String, String> oldId2CodeMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(clientTypeList)) {
            oldId2CodeMap = clientTypeList
                    .stream()
                    .collect(Collectors.toMap(x -> x.getOldId(), x -> x.getCode()));
        }

        List<Area> areaList = mongoTemplate.findAll(Area.class);
        Map<String, String> oldId2AreaIdMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(areaList)) {
            oldId2AreaIdMap = areaList
                    .stream()
                    .collect(Collectors.toMap(x -> x.getOldId(), x -> x.getAId()));
        }

        List<Client> list = new ArrayList<>();
        for (Map<String, Object> map : clientList) {
            Client client = new Client();
            client.setOldId(map.get("id").toString());

            client.setCode((String) map.get("custNo"));
            client.setName((String) map.get("custNm"));
            client.setRemark((String) map.get("mark"));
            client.setAddress((String) map.get("address"));
            client.setActualCity((String) map.get("cityNm"));
            client.setActualProvince((String) map.get("provinceNm"));

            //客户类别
            String custSortId = map.get("custSortId").toString();
            client.setTypeCode(oldId2CodeMap.get(custSortId));
            //部门id
            String principalDeptId = map.get("principalDeptId").toString();
            client.setDeptId(oldId2AreaIdMap.get(principalDeptId));

            client.setZipCode((String) map.get("postNo"));
            client.setContractPerson((String) map.get("linkEmp"));
            client.setFax((String) map.get("fax"));
            client.setEmail((String) map.get("email"));

            client.setStatus(1);
            client.setCreateTime((Date) map.get("isrtDt"));
            list.add(client);
        }
        mongoTemplate.insert(list, Client.class);
    }

    @Test
    public void handleClient() {

        long count = mongoTemplate.count(new Query(), Client.class);
        logger.info(">>> count:{}", count);

        int pageSize = 100;
        long pages = (count / pageSize) + 1;
        for (int i = 1; i <= pages; i++) {
            int pageNum = i;
            Query query = new Query()
                    .with(Sort.by(Sort.Direction.DESC, "createTime"))
                    .skip((pageNum - 1) * pageSize)
                    .limit(pageSize);

            List<Client> clientList = mongoTemplate.find(query, Client.class);
            if (CollectionUtils.isNotEmpty(clientList)) {
                logger.info(">>> client list size:{}, loop:{}", clientList.size(), i);
                clientList.parallelStream().forEach(x -> {

                    // province=山东大区（2） / city=北京市区（3） / region=销售部（1）

                    String deptId = x.getDeptId();
                    Area three = mongoTemplate.findOne(new Query(Criteria.where("aId").is(deptId)), Area.class);
                    if (three != null) {
                        String threeName = three.getName();
                        if (three.getType() == 2) {
                            x.setCity(threeName);

                            Area two = mongoTemplate.findOne(new Query(Criteria.where("aId").is(three.getPId())), Area.class);
                            if (two != null) {
                                String twoName = two.getName();
                                if (two.getType() == 1) {
                                    x.setProvince(twoName);

                                    Area one = mongoTemplate.findOne(new Query(Criteria.where("aId").is(two.getPId())), Area.class);
                                    if (one != null) {
                                        x.setRegion(one.getName());
                                    }
                                } else {
                                    x.setRegion(twoName);
                                }
                            }
                            // 二级
                        } else if (three.getType() == 1) {
                            x.setProvince(threeName);

                            Area two = mongoTemplate.findOne(new Query(Criteria.where("aId").is(three.getPId())), Area.class);
                            if (two != null) {
                                String twoName = two.getName();
                                if (two.getType() == 1) {
                                    x.setProvince(twoName);

                                    Area one = mongoTemplate.findOne(new Query(Criteria.where("aId").is(two.getPId())), Area.class);
                                    if (one != null) {
                                        x.setRegion(one.getName());
                                    } else {

                                    }
                                } else {
                                    x.setRegion(twoName);
                                }
                            }
                            // 一级
                        } else {
                            x.setRegion(threeName);
                        }
                    }
                });

                for (Client c : clientList) {
                    mongoTemplate.save(c);
                }

            }
        }

    }


    @Test
    public void outlets() {
        List<Map<String, Object>> outletsList =
                sqlServerTemplate.queryForList("select * from HRMS_YLYTN.dbo.LAssetLeavePlace");
        if (outletsList != null) {
            logger.info(">>> outletsList size:{}", outletsList.size());
        } else {
            logger.warn(" === outletsList null");
            return;
        }

        long count = outletsList.size();

        int pageSize = 100;
        long pages = (count / pageSize) + 1;
        for (int i = 1; i <= pages; i++) {
            int start = (i - 1) * pageSize;
            int end = i * pageSize;
            if (i == pages) {
                end = (int) count;
            }
            logger.info(">>> loop:{}, start:{}, end:{}",  i, start, end);

            List<Outlets> list = new ArrayList<>();
            for (Map<String, Object> map : outletsList.subList(start, end)) {
                Outlets outlets = new Outlets();
                //冗余字段
                outlets.setOldId(map.get("id").toString());
                outlets.setCustId(map.get("custId").toString());

//                outlets.setName((String) map.get("leavePlaceType"));
                outlets.setName((String) map.get("leavePlace"));
                outlets.setContractPerson((String) map.get("linkEmp"));
                outlets.setPhone((String) map.get("linkPhone"));
                outlets.setAddress((String) map.get("address"));

                outlets.setCreateTime((Date) map.get("isrtDt"));
                list.add(outlets);
            }
            mongoTemplate.insert(list, Outlets.class);

        }

    }

    @Test
    public void handleOutlets() {

        long count = mongoTemplate.count(new Query(), Outlets.class);
        logger.info(">>> count:{}", count);

        int pageSize = 100;
        long pages = (count / pageSize) + 1;
        for (int i = 1; i <= pages; i++) {
            int pageNum = i;
            Query query = new Query()
                    .with(Sort.by(Sort.Direction.DESC, "createTime"))
                    .skip((pageNum - 1) * pageSize)
                    .limit(pageSize);

            List<Outlets> outletsList = mongoTemplate.find(query, Outlets.class);
            if (CollectionUtils.isNotEmpty(outletsList)) {
                logger.info(">>> outletsList size:{}, loop:{}", outletsList.size(), i);

                outletsList.stream().forEach(x -> {
                    String custId = x.getCustId();
                    Client client = mongoTemplate.findOne(new Query(Criteria.where("oldId").is(custId)), Client.class);
                    if (client != null) {
                        x.setClientId(client.getId());
                        x.setClientCode(client.getCode());
                        x.setClientName(client.getName());
                    }
                });

                for (Outlets o : outletsList) {
                    mongoTemplate.save(o);
                }
            }
        }

    }

    @Test
    public void assetsMaterial() {
        List<Map<String, Object>> assetsMaterialInformationList =
                sqlServerTemplate.queryForList("select * from HRMS_YLYTN.dbo.LFixedMateriel");
        if (assetsMaterialInformationList != null) {
            logger.info(">>> assetsMaterialInformationList size:{}", assetsMaterialInformationList.size());
        } else {
            logger.warn(" === assetsMaterialInformationList null");
            return;
        }

        List<AssetsMaterialInformation> list = new ArrayList<>();
        for (Map<String, Object> map : assetsMaterialInformationList) {
            AssetsMaterialInformation assetsMaterialInformation = new AssetsMaterialInformation();
            assetsMaterialInformation.setOldId(map.get("id").toString());
            assetsMaterialInformation.setBrandId(map.get("brandId").toString());
            assetsMaterialInformation.setMaterielSortId(map.get("materielSortId").toString());

            assetsMaterialInformation.setAssetNO((String) map.get("materielNo"));
            assetsMaterialInformation.setAssetName((String) map.get("materielNm"));
            assetsMaterialInformation.setModel((String) map.get("spec"));
            assetsMaterialInformation.setVendorIdenfification((String) map.get("HRIdentifying"));
            assetsMaterialInformation.setVendorProductCode((String) map.get("HRCode"));

            assetsMaterialInformation.setRemark((String) map.get("mark"));
            assetsMaterialInformation.setStatus(1);
            assetsMaterialInformation.setCreateTime((Date) map.get("isrtDt"));
            list.add(assetsMaterialInformation);
        }
        mongoTemplate.insert(list, AssetsMaterialInformation.class);
    }

    @Test
    public void handleAssetsMaterial() {

        long count = mongoTemplate.count(new Query(), AssetsMaterialInformation.class);
        logger.info(">>> count:{}", count);

        List<AssetsMaterialInformation> assetsMaterialInformationList = mongoTemplate.findAll(AssetsMaterialInformation.class);
        if (CollectionUtils.isNotEmpty(assetsMaterialInformationList)) {

            List<AssetsClass> assetsClassList = mongoTemplate.findAll(AssetsClass.class);
            Map<String, AssetsClass> oldId2AssetsClassMap = assetsClassList
                    .stream()
                    .collect(Collectors.toMap(x -> x.getOldId(), x -> x));
            logger.info(">>> oldId2AssetsClassMap size:{}", oldId2AssetsClassMap.size());

            List<Brand> brandList = mongoTemplate.findAll(Brand.class);
            Map<String, Brand> oldId2BrandMap = brandList
                    .stream()
                    .collect(Collectors.toMap(x -> x.getOldId(), x -> x));
            logger.info(">>> oldId2BrandMap size:{}", oldId2BrandMap.size());


            assetsMaterialInformationList.stream().forEach(x -> {
                String brandId = x.getBrandId();
//                Brand brand = mongoTemplate.findOne(new Query(Criteria.where("oldId").is(brandId)), Brand.class);
                if (brandId != null) {
                    x.setBrandCode(oldId2BrandMap.get(brandId).getCode());
                }
                String materialSortId = x.getMaterielSortId();
//                AssetsClass assetsClass = mongoTemplate.findOne(new Query(Criteria.where("oldId").is(materialSortId)), AssetsClass.class);
                if (materialSortId != null) {
                    AssetsClass assetsClass = oldId2AssetsClassMap.get(materialSortId);
                    x.setAssetsClassId(assetsClass.getId());
                    x.setAssetsClassName(assetsClass.getName());
                    x.setAssetsClassCode(assetsClass.getCode());
                }
            });

            for (AssetsMaterialInformation a : assetsMaterialInformationList) {
                mongoTemplate.save(a);
            }
        }
    }

    //----- assets >>> device

    @Test
    public void asset() {

        Integer assetTotal =
                sqlServerTemplate.queryForObject("select count(*) from HRMS_YLYTN.dbo.BAsset", Integer.class);
        logger.info(">>> asset total:{}", assetTotal);

        /**
         * sql server 分页：
         *
         * select * from (
         *     select *,row_number() over(order by id)as number from Student
         * ) as s
         * where s.number between 5 and 8
         **/
        String sql_server_page = "select * from ( select *, row_number() over(order by id) as number from HRMS_YLYTN.dbo.BAsset) as asset " +
                "where asset.number between %d and %d";

//        assetTotal = 22;
//        int pageSize = 10;

        int pageSize = 1000;
        long pages = (assetTotal / pageSize) + 1;
        for (int i = 1; i <= pages; i++) {
            int start = (i - 1) * pageSize + 1;
            if (i == 1) {
                start = (i - 1) * pageSize;
            }
            int end = i * pageSize;
            if (i == pages) {
                end = assetTotal;
            }
            String sql = String.format(sql_server_page, start, end);
            logger.info("{}", sql);

            List<Map<String, Object>> assetList = sqlServerTemplate.queryForList(sql);
            if (CollectionUtils.isNotEmpty(assetList)) {
                logger.info(">>> assetList size:{}", assetList.size());

                List<Device> list = new ArrayList<>();
                for (Map<String, Object> map : assetList) {
                    Device device = new Device();
                    device.setOldId(map.get("id").toString());
                    // 客户信息（区域等）
                    device.setCustId(map.get("custId").toString());
                    // 资产物料（型号等）
                    device.setFixedMaterielId(map.get("fixedMaterielId").toString());

                    Object auditTime = map.get("auditDt");
                    if (auditTime != null) {
                        device.setAuditTime((Date) auditTime);
                    }
                    Object auditState = map.get("auditState");
                    if (auditState != null) {
                        device.setAuditStatusStr(auditState.toString());
                    }
                    //资产登记时间
                    device.setAssetRegTime((Date) map.get("assetRegDt"));
                    device.setUpdateTime((Date) map.get("updtDt"));
                    device.setAssetNumber(map.get("assetNo").toString());
                    device.setChipNumber(map.get("assetChipNo").toString());
                    device.setDeviceNumber(map.get("materialBarcode").toString());
                    device.setAssetProperties(map.get("assetProperty").toString());
                    device.setPutOnYear(map.get("putInYear").toString());
                    device.setUseStateOldId(map.get("useStateId").toString());
//                    device.setRegistrationNumber(map.get("registrationNumber").toString());

                    Object dataSource = map.get("dataSource");
                    if (dataSource != null) {
                        device.setDataSource(dataSource.toString());

                    }
                    Object scanType = map.get("scanType");
                    if (scanType != null) {
                        device.setScanType(scanType.toString());
                    }
                    Object deliveryTime = map.get("deliverGoodsDt");
                    if (deliveryTime != null) {
                        device.setDeliveryTime((Date) deliveryTime);
                    }

                    device.setStatus(1);
                    device.setRemark((String) map.get("mark"));
                    device.setCreateTime((Date) map.get("isrtDt"));

                    list.add(device);
                }
                Collection<Device> insert = mongoTemplate.insert(list, Device.class);
                if (insert != null) {
                    logger.info("### insert result size:{}", insert.size());
                }
            }

        }

    }

    @Test
    public void handleAsset() {
        long count = mongoTemplate.count(new Query(), Device.class);
        logger.info(">>> count:{}", count);

        List<AssetsMaterialInformation> assetsClassList = mongoTemplate.findAll(AssetsMaterialInformation.class);
        Map<String, AssetsMaterialInformation> oldId2AssetsMaterialMap = assetsClassList
                .stream()
                .collect(Collectors.toMap(x -> x.getOldId(), x -> x));
        logger.info(">>> 资产物料信息 size:{}", oldId2AssetsMaterialMap.size());

        List<Brand> brandList = mongoTemplate.findAll(Brand.class);
        Map<String, String> oldId2BrandNameMap = brandList
                .stream()
                .collect(Collectors.toMap(x -> x.getOldId(), x -> x.getName()));
        logger.info(">>> 品牌名称 size:{}", oldId2BrandNameMap.size());

        List<DeviceUseState> deviceUseStateList = mongoTemplate.findAll(DeviceUseState.class);
        Map<String, DeviceUseState> oldId2DeviceUseStateMap = deviceUseStateList
                .stream()
                .collect(Collectors.toMap(x -> x.getOldId(), x -> x));
        logger.info(">>> 冷柜使用状态  size:{}", oldId2DeviceUseStateMap.size());

        List<Client> clientList = mongoTemplate.findAll(Client.class);
        Map<String, Client> oldId2ClientMap = clientList
                .stream()
                .collect(Collectors.toMap(x -> x.getOldId(), x -> x));
        logger.info(">>> 客户信息 size:{}", oldId2ClientMap.size());

        List<Outlets> outletsList = mongoTemplate.findAll(Outlets.class);
        Map<String, Outlets> clientId2outletsMap = outletsList
                .stream()
                .collect(Collectors.toMap(x -> x.getClientId(), x -> x));
        logger.info(">>> 网点信息 size:{}", clientId2outletsMap.size());

        int pageSize = 500;
        long pages = (count / pageSize) + 1;
        for (int i = 1; i <= pages; i++) {
            int pageNum = i;
            Query query = new Query()
                    .with(Sort.by(Sort.Direction.DESC, "createTime"))
                    .skip((pageNum - 1) * pageSize)
                    .limit(pageSize);

            List<Device> deviceList = mongoTemplate.find(query, Device.class);
            if (CollectionUtils.isNotEmpty(deviceList)) {
                logger.info(">>> deviceList size:{}, loop:{}", deviceList.size(), i);

                deviceList.stream().forEach(device -> {
                    String custId = device.getCustId();
                    if (custId != null) {
                        Client client = oldId2ClientMap.get(custId);
                        if (client != null) {
                            device.setClientCode(client.getCode());
                            device.setClientName(client.getName());
                            device.setClientId(client.getId());
                            device.setRegion(client.getRegion());
                            device.setProvince(client.getProvince());
                            device.setCity(client.getCity());
                            device.setActualProvince(client.getActualProvince());
                            device.setActualCity(client.getActualCity());
                            device.setActualRegion(client.getActualRegion());

                            Outlets outlets = clientId2outletsMap.get(client.getId());
                            if (outlets != null) {
                                device.setOutletsAddress(outlets.getAddress());
                                device.setOutletsContractPerson(outlets.getContractPerson());
                                device.setOutletsId(outlets.getId());
                                device.setOutletsName(outlets.getName());
                                device.setOutletsPhone(outlets.getPhone());
                            }
                        }
                    }
                    String fixedMaterielId = device.getFixedMaterielId();
                    if (fixedMaterielId != null) {
                        AssetsMaterialInformation am = oldId2AssetsMaterialMap.get(fixedMaterielId);
                        if (am != null) {
                            device.setAssetMaterialInformationId(am.getId());
                            device.setAssetClassName(am.getAssetsClassName());
                            device.setAssetName(am.getAssetName());
                            device.setAssetNO(am.getAssetNO());
                            device.setBrandCode(am.getBrandCode());
                            device.setBrandId(am.getBrandId());
                            device.setBrandName(oldId2BrandNameMap.get(am.getBrandId()));
                            device.setFreezerModel(am.getModel());
                        }
                    }
                    String useStateOldId = device.getUseStateOldId();
                    if (useStateOldId != null) {
                        if (oldId2DeviceUseStateMap.containsKey(useStateOldId)) {
                            device.setUseStateId(oldId2DeviceUseStateMap.get(useStateOldId).getCode().toString());
                        }
                    }
                });

                for (Device d : deviceList) {
                    mongoTemplate.save(d);
                }
            }
        }
    }


}
