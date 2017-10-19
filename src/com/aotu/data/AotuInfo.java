package com.aotu.data;

import java.io.Serializable;

public class AotuInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int uid ;// int 用户ID
    public String token="";// string 用户Token，32位
    public String face =""; // string 头像
    public String name ="";// string 真实姓名
    public String mobile ="";// string 手机号
    public String email="";// string 电子邮箱
    
    
    public String idcard =""; // string 身份证号
    public String birthdate="";// string 出生年月
    public String  school = ""; // int 毕业院校代码
    public String   gender = "1" ; // int 性别; 1:男, 0:女
    public String usernative=""; // string 籍贯
    public String graduate=""; // string 学历
    public String major="";//专业
    public String school_text=""; // string 毕业院校名称
    public String gender_text=""; // string 姓别文本
    public String native_text=""; // string 籍贯文本
    public String graduate_text=""; // string 学历文本
    public long balance = -1 ; // number 余额
    
}
