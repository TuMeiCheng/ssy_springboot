package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.BrokenreasonService;
import com.wande.ssy.entity.Brokenreason;
import com.wande.ssy.utils.ToEnglistUtil;
import com.wande.ssy.utils.TreeUtil;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/brokenreason")
@RestController
public class BrokenreasonController {

    @Reference(interfaceClass=BrokenreasonService.class)
    private BrokenreasonService brokenreasonService;


    /**
     * 市民二维码扫描使用的接口
     * 获取报修原因树形下拉选
     */
    @RequestMapping("/getBrokenreasonByGroup")
    public  Object getBrokenreasonByGroup(@RequestParam(value = "isEng",defaultValue = "")String isEng) {
        // 1 场地非英文模式     2 英文模式
        if (isEng == null || "undefined".equals(isEng) || "null".equals(isEng)) {
            RespWrapper<List<Brokenreason>> resp = brokenreasonService.getBrokenreasonList(0);
            if (resp.getErrCode() != 0 || resp.getObj() == null) {
                throw new RespException(resp.getErrCode(), resp.getErrMsg());
            }
            List<Brokenreason> rets = resp.getObj();
            for (Brokenreason ret : rets) {
                List<Brokenreason> childs = brokenreasonService.getBrokenreasonList(ret.getReasonId()).getObj();
                ret.set("childs", childs == null ? new ArrayList<Brokenreason>() : childs);
            }
            return RespWrapper.makeResp(0, "", rets);
        } else {            // 英文模式
            RespWrapper<List<Brokenreason>> resp = brokenreasonService.getBrokenreasonList(0);
            if (resp.getErrCode() != 0 || resp.getObj() == null) {
                throw new RespException(resp.getErrCode(), resp.getErrMsg());
            }
            List<Brokenreason> rets = resp.getObj();
            for (int i = 0; i < rets.size(); i++) {
                String eng = ToEnglistUtil.ChineseToEnglish(rets.get(i).getName());
                if (eng.isEmpty()) {
                    rets.remove(i);
                    i--;
                    continue;
                }
                rets.get(i).setName(eng);
                List<Brokenreason> childs = brokenreasonService.getBrokenreasonList(rets.get(i).getReasonId()).getObj();
                for (int j = 0; j < childs.size(); j++) {
                    String childName = ToEnglistUtil.ChineseToEnglish(childs.get(j).getName());
                    if (childName.isEmpty()) {
                        childs.remove(j);
                        j--;
                        continue;
                    }
                    childs.get(j).setName(childName);
                }
                rets.get(i).set("childs", childs == null ? new ArrayList<Brokenreason>() : childs);
            }
            return RespWrapper.makeResp(0, "", rets);
        }

    }



    /**
     * 获取报修原因树形下拉选
     *
     */
    @RequestMapping("getBrokenreasonTreeSelect")
    public Object getBrokenreasonTreeSelect(){
        RespWrapper<List<Brokenreason>> orgs = brokenreasonService.getBrokenreasonList(Integer.MAX_VALUE);
        if (orgs.getErrCode() != 0 || orgs.getObj() == null) {
            throw new RespException(orgs.getErrCode(), orgs.getErrMsg());
        }
        List<Brokenreason> objs = orgs.getObj();
        TreeUtil<Brokenreason> treeUtil = new TreeUtil<Brokenreason>();
        List<Brokenreason> result = treeUtil.buidTree(objs, Brokenreason.class, "reasonId", "parentId", 0);
        for (Brokenreason o : result) {
            StringBuilder t = new StringBuilder();
            t.append("");
            if (o.getParentId() != 0) {
                t.append("==");
            }
            t.append(o.getName());
            o.set("namelabel", t.toString());
        }
       return RespWrapper.makeResp(0, "", result);
    }





}



