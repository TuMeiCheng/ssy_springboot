package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class Msg extends Model<Msg> {

    private static final long serialVersionUID = 5761728294152887172L;

    private Integer  id;
    private String content;
    private String url;
    private String mtype;
    private Long createTime;

    public Integer getId() {
        return get("id");
    }

    public void setId(Integer id) {
       set("id",id);
       
    }

    public String getContent() {
        return get("content");
    }

    public void setContent(String content) {
        set("content",content);
        
    }

    public String getUrl() {
        return get("url");
    }

    public void setUrl(String url) {
      set("url",url);
      
    }

    public String getMtype() {
        return get("mtype");
    }

    public void setMtype(String mtype) {
       set("mtype",mtype);
       
    }

    public Long getCreateTime() {
        return get("createTime");
    }

    public void setCreateTime(Long createTime) {
        set("createTime",createTime);
        
    }
}
