package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.AdminService;
import com.wande.ssy.dubbo.provider.service.IndexService;
import com.wande.ssy.dubbo.provider.service.RegionService;
import com.wande.ssy.dubbo.provider.service.SupplierService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Region;
import com.wande.ssy.entity.Supplier;
import com.wande.ssy.enums.ItemStatus;
import com.wande.ssy.utils.DateTimeUtil;
import com.wande.ssy.utils.StringUtil;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.model.SuperBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;


    @Reference(interfaceClass=IndexService.class)
    private IndexService indexService;

    @Reference(interfaceClass=RegionService.class)
    private RegionService regionService;

    @Reference(interfaceClass=SupplierService.class)
    private SupplierService  supplierService;





    /**
     * 获取首页场地和器材数量的接口
     */
    @RequestMapping("/getAreaItemCount")
    public  Object getAreaItemCount(@RequestParam(value = "provinceId",defaultValue = "0") Integer provinceId, // 省份ID
                                    @RequestParam(value = "cityId",defaultValue = "0") Integer cityId, // 城市ID
                                    @RequestParam(value = "regionId",defaultValue = "0") Integer regionId, // 地区ID
                                    @RequestParam(value = "eqpsortId",defaultValue = "0") Integer eqpsortId){ // 分类ID
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(8L);
        admin.setAccount("admin");

        int parentId = regionId != 0 ? regionId : cityId != 0 ? cityId : provinceId != 0 ? provinceId : 0;

        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("parentId", parentId);
        params.put("eqpsortId", eqpsortId);

        RespWrapper<SuperBean> resp = indexService.getAreaItemCount(admin, params);
        //返回结果
        return resp;
    }


    /**
     * 设施安全警示接口
     * @param provinceId     // 省份ID
     * @param cityId        // 城市ID
     * @param regionId     // 地区ID
     * @param eqpsortId    // 分类ID
     * @return
     */
    @RequestMapping("/getEqpSafeChart")
    public Object getEqpSafeChart(@RequestParam(value = "provinceId",defaultValue = "0") Integer provinceId,
                                  @RequestParam(value = "cityId",defaultValue = "0") Integer cityId,
                                  @RequestParam(value = "regionId",defaultValue = "0") Integer regionId,
                                  @RequestParam(value = "eqpsortId",defaultValue = "0") Integer eqpsortId){
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(8L);
        admin.setAccount("admin");

        Integer parentId = regionId != 0 ? regionId : cityId != 0 ? cityId : provinceId != 0 ? provinceId : 0;

        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("parentId", parentId);
        params.put("eqpsortId", eqpsortId);

        RespWrapper<List<SuperBean>> resp = indexService.getEqpSafeChart(admin, params);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        //扩展
        Map<String, Long> resultMap = new HashMap<String, Long>();
        resultMap.put("7", 0L);
        resultMap.put("6", 0L);
        resultMap.put("5", 0L);
        resultMap.put("4", 0L);
        resultMap.put("3", 0L);
        resultMap.put("2", 0L);
        resultMap.put("1", 0L);

        for(SuperBean ret : resp.getObj()){
            String rtime = ret.getString("time");
            long num = ret.getLong("num");
            if (rtime != null && StringUtil.isNotEmpty(rtime)) {
                Date now = new Date();
                Date time = DateTimeUtil.formatString(rtime, "yyyy-MM");
                @SuppressWarnings("deprecation")
                int yearMinus = time.getYear() - now.getYear();
                // 将过期年限相差1-7年的计算出来, 放在对应的Map里面
                if (yearMinus >= 1 && yearMinus <= 7) {
                    resultMap.put((8 - yearMinus) + "", resultMap.get((8 - yearMinus) + "") + num);
                }
            }
        }

        //包装数据
        List<SuperBean> result = new ArrayList<SuperBean>();
        for(String key : resultMap.keySet()){
            SuperBean r = new SuperBean();
            r.put("value", key);		//value是年份
            r.put("name", resultMap.get(key));		//name是数量
            result.add(r);
        }
        //返回结果
        return RespWrapper.makeResp(0, "", result);
    }



    /**
     * 设施状态分布
     * @param provinceId     // 省份ID
     * @param cityId        // 城市ID
     * @param regionId     // 地区ID
     * @param eqpsortId    // 分类ID
     * @return
     */
    @RequestMapping("/getEqpStatusChart")
    public Object getEqpStatusChart(@RequestParam(value = "provinceId",defaultValue = "0") Integer provinceId,
                                    @RequestParam(value = "cityId",defaultValue = "0") Integer cityId,
                                    @RequestParam(value = "regionId",defaultValue = "0") Integer regionId,
                                    @RequestParam(value = "eqpsortId",defaultValue = "0") Integer eqpsortId){
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(8L);
        admin.setAccount("admin");
        int parentId = regionId != 0 ? regionId : cityId != 0 ? cityId : provinceId != 0 ? provinceId : 0;

        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("parentId", parentId);
        params.put("eqpsortId", eqpsortId);

        RespWrapper<List<SuperBean>> resp = indexService.getEqpStatusChart(admin, params);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        //扩展
        for (SuperBean ret : resp.getObj()) {
            int status = ret.getInt("status");
            if(status != 0){
                SuperBean itemStyle = new SuperBean();
                SuperBean normal = new SuperBean();
                normal.put("color", status == 1 ? "#28C187" : status == 2 ? "#E4A325" : "#DA4426");
                itemStyle.put("normal", normal);
                ret.put("itemStyle", itemStyle);
                ret.put("name", ItemStatus.getName(status));
            }
        }
        //返回结果
        return resp;
    }
    
    
    
    /**
     * 设备安装年份
     * @param provinceId     // 省份ID
     * @param cityId        // 城市ID
     * @param regionId     // 地区ID
     * @param eqpsortId    // 分类ID
    */
    @RequestMapping("/getEqpTimeChart")
    public Object getEqpTimeChart(@RequestParam(value = "provinceId",defaultValue = "0") Integer provinceId,
                                  @RequestParam(value = "cityId",defaultValue = "0") Integer cityId,
                                  @RequestParam(value = "regionId",defaultValue = "0") Integer regionId,
                                  @RequestParam(value = "eqpsortId",defaultValue = "0") Integer eqpsortId){
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(8L);
        admin.setAccount("admin");

        int parentId = regionId != 0 ? regionId : cityId != 0 ? cityId : provinceId != 0 ? provinceId : 0;
        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("parentId", parentId);
        params.put("eqpsortId", eqpsortId);

        RespWrapper<List<SuperBean>> resp = indexService.getEqpTimeChart(admin, params);
        //返回结果
        return resp;
    }


    /**
     *  安装地点分布
     * @param provinceId     // 省份ID
     * @param cityId        // 城市ID
     * @param regionId     // 地区ID
     * @param eqpsortId    // 分类ID
     * @param areaId      // 场地ID
    */
    @RequestMapping("/getInstallAreaChart")
    public Object getInstallAreaChart(@RequestParam(value = "provinceId",defaultValue = "0") Integer provinceId,
                                      @RequestParam(value = "cityId",defaultValue = "0") Integer cityId,
                                      @RequestParam(value = "regionId",defaultValue = "0") Integer regionId,
                                      @RequestParam(value = "eqpsortId",defaultValue = "0") Integer eqpsortId,
                                      @RequestParam(value = "areaId",defaultValue = "0") Integer areaId) { //场地ID
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(8L);
        admin.setAccount("admin");

        int parentId = regionId != 0 ? regionId : cityId != 0 ? cityId : provinceId != 0 ? provinceId : 0;

        //设置参数,调用远程Service
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("parentId", parentId);
        params.put("eqpsortId", eqpsortId);
        params.put("areaIds", areaId);               //场地ID

        RespWrapper<SuperBean> resp = indexService.getInstallAreaChart(admin, params);
        //返回结果
        return resp;
    }



    /**
     * 获取首页报表下拉选初始化值
    */
    @RequestMapping("/getSelectValue")
    public Object getSelectValue(){

        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(10L);
        admin.setAccount("admin");

        // -1 获取该登录用户的拥有的所有地区
        RespWrapper<List<Region>> resp = regionService.getRegionListByParentId(admin, -1);
        if (resp.getErrCode() != 0) {
            throw new RespException(1001, resp.getErrMsg());
        }
        // 将获取的数据, 分别整理为对应的省市区列表
        List<Region> rets = resp.getObj();
        List<Region> proList = new ArrayList<Region>();
        List<Region> cityList = new ArrayList<Region>();
        List<Region> regionList = new ArrayList<Region>();
        for (Region r : rets) {
            if (r.getLevel() == 1) {
                proList.add(r);
            } else if (r.getLevel() == 2) {
                cityList.add(r);
            } else if (r.getLevel() == 3) {
                regionList.add(r);
            }
        }
        if (proList.size() == 0) {
            throw new RespException(1001, "当前用户没有对应的管辖区域");
        }

        // 定义返回的数据
        int provinceId = 0;
        int cityId = 0;
        int regionId = 0;
        String provinceName = "";
        String cityName = "";
        String regionName = "";
        if (proList.size() == 1) {
            provinceId = proList.get(0).getRegionId();
            provinceName = proList.get(0).getName();
            if (cityList.size() == 1) {
                cityId = cityList.get(0).getRegionId();
                cityName = cityList.get(0).getName();
                if (regionList.size() == 1) {
                    regionId = regionList.get(0).getRegionId();
                    regionName = regionList.get(0).getName();
                }
            }
        }
        // 封装返回的数据
        SuperBean sb = new SuperBean();
        sb.put("provinceId", provinceId);
        sb.put("cityId", cityId);
        sb.put("regionId", regionId);
        sb.put("provinceName", provinceName);
        sb.put("cityName", cityName);
        sb.put("regionName", regionName);

        //返回结果
        return RespWrapper.makeResp(0, "", sb);
    }


    /**
     * 供货商器材数量
     * @param provinceId     // 省份ID
     * @param cityId        // 城市ID
     * @param regionId     // 地区ID
     * @param eqpsortId    // 分类ID
    */
    @RequestMapping("/getSupplierChart")
    public Object getSupplierChart(@RequestParam(value = "provinceId",defaultValue = "0") Integer provinceId,
                                   @RequestParam(value = "cityId",defaultValue = "0") Integer cityId,
                                   @RequestParam(value = "regionId",defaultValue = "0") Integer regionId,
                                   @RequestParam(value = "eqpsortId",defaultValue = "0") Integer eqpsortId){

        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(8L);
        admin.setAccount("admin");

        int parentId = regionId != 0 ? regionId : cityId != 0 ? cityId : provinceId != 0 ? provinceId : 0;
        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("parentId", parentId);
        params.put("eqpsortId", eqpsortId);

        RespWrapper<List<SuperBean>> resp = indexService.getSupplierChart(admin, params);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        //扩展
        Map<Integer, String> colors = new HashMap<Integer, String>();
        colors.put(1, "#566EE9");
        colors.put(2, "#16AAD3");
        colors.put(3, "#28C187");
        colors.put(4, "#E78830");
        int i = 1;
        for(SuperBean ret : resp.getObj()){
            int supplierId = ret.getInt("supplierId");
            Supplier supplier = supplierService.getOneSupplier(supplierId).getObj();
            ret.put("name", supplier == null ? "" : supplier.getName());

            SuperBean itemStyle = new SuperBean();
            SuperBean normal = new SuperBean();
            normal.put("color", i <= 3 ? colors.get(i++) : colors.get(i));
            itemStyle.put("normal", normal);
            ret.put("itemStyle", itemStyle);

        }
        //返回结果
        return resp;
    }




}
