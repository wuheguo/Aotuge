package com.aotu.data;

import java.util.ArrayList;
import java.util.List;

public class BaseDataItem {
	public String hotline;// string 热线电话
	public String regagr;
    public String about;
    public String feedback;
	public List<BaseChildItem>gender = new ArrayList<BaseChildItem>();// array 性别
	public List<BaseChildItem>salaries= new ArrayList<BaseChildItem>();// 薪资范围（用户）
	public List<BaseChildItem>work_type= new ArrayList<BaseChildItem>();// 工作类型
	public List<BaseChildItem>graduate= new ArrayList<BaseChildItem>();// 学历
	public List<BaseChildItem> company_property= new ArrayList<BaseChildItem>(); // array 公司性质
	public List<BaseChildItem> company_size= new ArrayList<BaseChildItem>();//公司规模
}
