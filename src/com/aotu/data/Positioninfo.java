package com.aotu.data;

import java.util.ArrayList;
import java.util.List;

public class Positioninfo {
	   public String    id;
	   public String    cid;
	   public String    cuid;
	   public String    company;
	   public String    title;
	   public String    position;
	   public String    salary;
	   public String    work_type;
	   public String    graduate;
	   public List<CityItem> location = new ArrayList<CityItem>();
	   public List<String>welfare = new ArrayList<String>();
	   public String update; // date 更新时间
	   public String salary_text; // string 薪酬范围文本
	   public String work_type_text;// string 工作类型文本
	   public String graduate_text;// string 学历要求文本  
	   public String requirements;
	   public String update_text;
	      

}
