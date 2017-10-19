package com.aotu.data;

import java.util.ArrayList;
import java.util.List;

public class CompanyInfo {
	 public int cid ;// int 企业ID
     public String company="" ;// string 企业名称
     public String logo="";// string 企业Logo
     public int company_property;// int 企业性质
     public int company_size;// int 企业规模
     public String website=""; // string 企业网站
     public String address="";// string 企业地址
     public String introduce="";// string 公司介绍
     public int score;// int 总评分
     public List<CompanyScoreitem> scorearray = new ArrayList<CompanyScoreitem>();
    
     public String company_property_text="";// string 工作类型文本
     public String company_size_text=""; // string 学历要求文本
     public long longitude; // number 经度
     public long latitude; // number 纬度
     public String registered="";
}
