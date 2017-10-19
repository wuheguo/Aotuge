package com.aotu.data;

import java.io.Serializable;

public class PersonEducation extends PersonInfoBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean isTab = false;
	public String  school_text;// 学校
	public String  school_code;//学校编码
	public String  date_start;// 入学时间，只到年份
	public String  date_end;// 毕业时间，只到年份
	public String  department;//学院
	public String  major;//专业
	public String  graduate;//学历
	public String  graduate_code;//学历代码
	public String  description;//其他说明 
	
	
}
