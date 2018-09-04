package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class Msg extends Model<Msg> {

    private static final long serialVersionUID = 5761728294152887172L;

    private int  id;
    private String content;
    private String url;
    private String mtype;
    private long createTime;

}
