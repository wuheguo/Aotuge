package com.aotu.data;

import java.util.ArrayList;
import java.util.List;

//意向数据
public class IntentionItem {
	
	 public int id; // int 意向ID
     public int uid; // int 用户ID
     public String name; // string 名称
     public List<CityItem>cities = new ArrayList<CityItem>(); // array 目标城市，最多三个
     public List<BaseChildItem> position = new ArrayList<BaseChildItem>();
     
     public int salary ; // int 期望薪金
     public int work_type; // int 工作类型
     public int company_property; // int 企业类型
     public int company_size; // int 企业规模
     public int cv_id ; // int 简历ID
     public String salary_text; // string 期望薪金文本
     public String work_type_text; // string 工作类型文本
     public String company_property_text; // string 企业类型文本
     public String company_size_text; // string 企业规模文本

}
