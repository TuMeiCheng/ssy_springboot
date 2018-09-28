package com.wande.ssy.utils;

public class ToEnglistUtil {

    /**
     * 转英文
     * @param eng
     * @return
     */
    public static String ChineseToEnglish(String eng){
        String english = "";
        switch (eng) {
            case "功能失效":english = "Functional failure";break;
            case "阻尼失效":english = "Damping failure";break;
            case "拉绳把手套丢失":english = "Stretch gloves are missing";break;
            case "外观不良":english = "bad apperance";break;
            case "涂层脱落":english = "Coating off";break;
            case "焊道锈蚀":english = "Weld bead rust";break;
            case "表面漆膜磕碰生锈":english = "Surface paint bumps rust";break;
            case "安装不良":english = "Poor installation";break;
            case "铭牌不贴合":english = "The nameplate does not fit";break;
            case "安全距离不符合要求":english = "";break;
            case "缓冲层不符合要求":english = "";break;
            case "主柱松动":english = "Main column loose";break;
            case "螺丝脱落":english = "Screw off";break;
            case "部件损坏":english = "Parts are damaged";break;
            case "拉绳丢失":english = "Pull rope lost";break;
            case "防盗帽丢失":english = "Anti-theft cap is lost";break;
            case "轴承损坏":english = "Bearing damage";break;
            case "塑木板腐朽":english = "Plastic boards are decaying";break;
            case "脚踏板胶皮脱落":english = "Foot pedal rubber off";break;
            case "脚踏板胶皮开裂":english = "Foot pedal rubber crack";break;
            case "阻尼不符合要求":english = "Damping does not match";break;
            case "安全隐患":english = "";break;
            case "利边":english = "";break;
            case "卡夹":english = "";break;
            case "勾挂":english = "";break;
            case "其他":english = "other";break;
            case "其他原因":english = "other reasons";break;
            case "器材偏移":english = "";break;
            case "器材老化严重":english = "";break;
            default:english = "";break;
        }
        return english;
    }

}
