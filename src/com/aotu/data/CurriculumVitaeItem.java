package com.aotu.data;

import java.util.ArrayList;
import java.util.List;

public class CurriculumVitaeItem {
	
	public int  id;// int 简历ID
    public String  title="";// string 简历标题
    public String uid = "";
    public PersonUserInfoItem personUserInfoItem = new PersonUserInfoItem();
    public PersonContactWayItem personContactWayItem = new PersonContactWayItem();
    public PersonGrades personGrades = new PersonGrades();//语言
    public List<PersonEducation> educations = new ArrayList<PersonEducation>();//教育经历
    public List<PersonInternshipItem> internships = new ArrayList<PersonInternshipItem>(); //工作／实习经历
    public List<PersonAssociationItem> associations = new ArrayList<PersonAssociationItem>();//社团经历 
    public List<PersonAwardsItem> awards = new ArrayList<PersonAwardsItem>();//获奖情况
}
