package com.aotu.data;

import java.io.Serializable;

public class PersonInternshipItem extends PersonInfoBase implements Serializable{
	public String company;//公司／实验室
	public String title;//职务
	public String date_start;//开始时间，年月
	public String date_end;// 结束时间，年月
	public String description;//其他说明
	  
}
