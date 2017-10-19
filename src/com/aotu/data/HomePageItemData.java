package com.aotu.data;

import java.util.ArrayList;
import java.util.List;

public class HomePageItemData {
    public int id; // int 职位ID
    public int cid; // int 企业ID
    public String company; // string 企业名称
    public String title; // string 职位名称
    public int salary; // int 薪酬范围
    public int work_type; // int 工作类型
    public int graduate; // int 学历要求
    public List<CityItem> location = new ArrayList<CityItem>();
    public List<String>welfare = new ArrayList<String>();
    public String update; // date 更新时间
    public String salary_text; // string 薪酬范围文本
    public String work_type_text;// string 工作类型文本
    public String graduate_text;// string 学历要求文本  
}
