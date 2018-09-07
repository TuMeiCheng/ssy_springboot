package com.wande.ssy.utils;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Model;
import com.ynm3k.mvc.model.RespWrapper;

public class ResultJson {

    public  static Object  ModelConvertUtil(Object object){
        if (object == null){
            return null;
        }
        boolean bln;
        bln =  object instanceof Model ? true : false;
        if (bln){
            //是model的子类
            Model model =(Model) object;
            return JSON.toJSON(model.toRecord().getColumns());
        }

        return object;
    }

    public static Ret ReturnJson(RespWrapper respWrapper){

        return  Ret.ok("obj", ModelConvertUtil(respWrapper.getObj()))
                .set("errMsg", respWrapper.getErrMsg())
                .set("errCode",respWrapper.getErrCode());

    }
}
