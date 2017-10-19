package com.aotu.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonGrades extends PersonInfoBase implements Serializable{
	public List<LanguageItem> languages = new ArrayList<LanguageItem>();
	public String grade = "";//成绩情况
	public String computer = "";//计算机能力
}
