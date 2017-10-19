package com.aotu.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InterviewInfoItem implements Serializable {
	 public int id ; // int 面试ID
     public int cid ; // int 公司ID
     public String company ; // string 公司名称
     public String title ; // string 职位名称
     public String address ; // string 公司地址
     public String tips; // string 注意事项
     public String interview_time; // string 面试时间
     public int status; // int 状态码
     public List<InterviewProgressItem>progress = new ArrayList<InterviewProgressItem>();
     public long longitude ; // number 经度
     public long latitude ; // number 纬度
     public List<InterviewConflictItem>conflict = new ArrayList<InterviewConflictItem>();
     public String ad;
     
    


}
