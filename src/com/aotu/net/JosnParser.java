package com.aotu.net;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aotu.aotuge.AotugeApplication;
import com.aotu.data.AotuInfo;
import com.aotu.data.BaseChildItem;
import com.aotu.data.BaseDataItem;
import com.aotu.data.BaseGrade;
import com.aotu.data.BaseGradeLanguages;
import com.aotu.data.BaseItem;
import com.aotu.data.CVItem;
import com.aotu.data.CheckUpdate;
import com.aotu.data.CityItem;
import com.aotu.data.CompanyInfo;
import com.aotu.data.CompanyScoreitem;
import com.aotu.data.CurriculumVitaeItem;
import com.aotu.data.DirectionGridItem;
import com.aotu.data.EntranceTicket;
import com.aotu.data.HomePageItemData;
import com.aotu.data.IntentionItem;
import com.aotu.data.InterviewCalendarItem;
import com.aotu.data.InterviewConflictItem;
import com.aotu.data.InterviewInfoItem;
import com.aotu.data.InterviewProgressItem;
import com.aotu.data.InterviewTimeItem;
import com.aotu.data.Interviewscore;
import com.aotu.data.LanguageItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.DirectionsItem;
import com.aotu.data.PersonAssociationItem;
import com.aotu.data.PersonAwardsItem;
import com.aotu.data.PersonContactWayItem;
import com.aotu.data.PersonEducation;
import com.aotu.data.PersonInfoBase;
import com.aotu.data.PersonGrades;
import com.aotu.data.PersonInternshipItem;
import com.aotu.data.PersonUserInfoItem;
import com.aotu.data.PositionItem;
import com.aotu.data.Positioninfo;
import com.aotu.data.ProvinceCityItem;
import com.aotu.data.ReconciliationsItem;
import com.aotu.data.SchoolItem;
import com.aotu.data.ShuangXuanItem;
import com.aotu.data.Ways;
import com.aotu.data.WorkItem;


public class JosnParser {

	private static String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		String second = String.valueOf(c.get(Calendar.SECOND));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins + ":" + second);

		return sbBuffer.toString();
	}
	
	public static void getDataArray(String ajson,List<String> aArray) {
		
		
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			
			JSONArray arrays = jsonObjectTemp.getJSONArray("array");
			for(int i = 0; i < arrays.length(); i++)
			{
				String item  = String.valueOf(arrays.get(i));
				aArray.add(item);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static NetReturnInfo getBaseData(String ajson,BaseDataItem aBaseDataItem) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
				aBaseDataItem.hotline = jsonitem.getString("hotline");
				JSONObject url = jsonitem.getJSONObject("url");
				aBaseDataItem.about = url.getString("about");
				aBaseDataItem.regagr = url.getString("regagr");
				aBaseDataItem.feedback = url.getString("feedback");
				JSONArray genders = jsonitem.getJSONArray("gender");
				for(int i = 0; i <genders.length(); i++)
				{
					JSONObject gender = genders.getJSONObject(i);
					BaseChildItem item = new BaseChildItem();
					item.code = gender.getInt("code");
					item.name = gender.getString("name");
					aBaseDataItem.gender.add(item);
				}
				
				JSONArray salariess = jsonitem.getJSONArray("salaries");
				for(int i = 0; i <salariess.length(); i++)
				{
					JSONObject salaries = salariess.getJSONObject(i);
					BaseChildItem item = new BaseChildItem();
					item.code = salaries.getInt("code");
					item.name = salaries.getString("name");
					aBaseDataItem.salaries.add(item);
				}
				
				JSONArray work_types = jsonitem.getJSONArray("work_type");
				for(int i = 0; i <work_types.length(); i++)
				{
					JSONObject work_type = work_types.getJSONObject(i);
					BaseChildItem item = new BaseChildItem();
					item.code = work_type.getInt("code");
					item.name = work_type.getString("name");
					aBaseDataItem.work_type.add(item);
				}
				
				JSONArray graduates = jsonitem.getJSONArray("graduate");
				for(int i = 0; i <graduates.length(); i++)
				{
					JSONObject graduate = graduates.getJSONObject(i);
					BaseChildItem item = new BaseChildItem();
					item.code = graduate.getInt("code");
					item.name = graduate.getString("name");
					aBaseDataItem.graduate.add(item);
				}
				JSONArray company_propertys = jsonitem.getJSONArray("company_property");
				for(int i = 0; i <company_propertys.length(); i++)
				{
					JSONObject company_property = company_propertys.getJSONObject(i);
					BaseChildItem item = new BaseChildItem();
					item.code = company_property.getInt("code");
					item.name = company_property.getString("name");
					aBaseDataItem.company_property.add(item);
				}
				
				JSONArray company_sizes = jsonitem.getJSONArray("company_size");
				for(int i = 0; i <company_sizes.length(); i++)
				{
					JSONObject company_size = company_sizes.getJSONObject(i);
					BaseChildItem item = new BaseChildItem();
					item.code = company_size.getInt("code");
					item.name = company_size.getString("name");
					aBaseDataItem.company_size.add(item);
				}	
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getBaseGrade(String ajson,BaseGrade aBaseDataItem) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
				JSONArray grades = jsonitem.getJSONArray("grade");
				for(int i =0 ; i < grades.length();i++)
				{
					JSONObject gradesjson = grades.getJSONObject(i);
					BaseItem baseitem = new BaseItem();
					baseitem.code = gradesjson.getString("code");
					baseitem.name = gradesjson.getString("name");
					aBaseDataItem.grade.add(baseitem);
				}
				
				JSONArray computers = jsonitem.getJSONArray("computer");
				for(int i =0 ; i < computers.length();i++)
				{
					JSONObject computerjson = computers.getJSONObject(i);
					BaseItem baseitem = new BaseItem();
					baseitem.code = computerjson.getString("code");
					baseitem.name = computerjson.getString("name");
					aBaseDataItem.computer.add(baseitem);
				}
				
				JSONArray languagess = jsonitem.getJSONArray("languages");
				for(int i =0 ; i < languagess.length();i++)
				{
					JSONObject languagesjson = languagess.getJSONObject(i);
					BaseGradeLanguages baseitem = new BaseGradeLanguages();
					baseitem.lang = languagesjson.getString("lang");
					JSONArray levels = languagesjson.getJSONArray("level");
					for(int j = 0; j < levels.length(); j++)
					{
						String level = levels.getString(j);
						baseitem.level.add(level);
					}
					aBaseDataItem.languages.add(baseitem);
				}
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}

	public static NetReturnInfo getLogonData(String ajson, AotuInfo item) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code==0) {
				itemdata.success = true;
				itemdata.info = "登录成功";
				JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
				item.uid = jsonitem.getInt("id");
				item.token = jsonitem.getString("token");
				item.face = jsonitem.getString("face");
				item.name = jsonitem.getString("name");
				item.mobile = jsonitem.getString("mobile");
				item.email = jsonitem.getString("email");
			}
			else
			{
				itemdata.info = jsonObjectTemp.getString("message");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}

	public static NetReturnInfo getCodeData(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "验证码已经发出";
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getUserCheck(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = jsonObjectTemp.getString("message");
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getuserresetpwd(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "修改成功";
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getRegister(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				
				itemdata.success = true;
				itemdata.info = "注册成功";
				JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
				String toke = jsonitem.getString("token");
				if(AotugeApplication.getInstance().mAotuInfo==null)
					AotugeApplication.getInstance().mAotuInfo = new AotuInfo();
				 AotugeApplication.getInstance().mAotuInfo.token = toke;
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getSignIn(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				
				itemdata.success = true;
				itemdata.info = "登陆成功";
				JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
				if(AotugeApplication.getInstance().mAotuInfo == null)
					AotugeApplication.getInstance().mAotuInfo = new AotuInfo();
				AotugeApplication.getInstance().mAotuInfo.uid = jsonitem.getInt("uid");
				AotugeApplication.getInstance().mAotuInfo.token = jsonitem.getString("token");
				AotugeApplication.getInstance().mAotuInfo.face = jsonitem.getString("face");
				AotugeApplication.getInstance().mAotuInfo.name = jsonitem.getString("name");
				AotugeApplication.getInstance().mAotuInfo.mobile= jsonitem.getString("mobile");
				AotugeApplication.getInstance().mAotuInfo.email= jsonitem.getString("email");
				AotugeApplication.getInstance().mIsNullAotuInfo = false;
				AotugeApplication.getInstance().saveUserInfo();
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getProvinceCity(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				AotugeApplication.getInstance().mCityItemList.clear();
				
				ProvinceCityItem topcityitem = new ProvinceCityItem();
				topcityitem.code = 0;
				topcityitem.provinve="已选择工作地点";
				topcityitem.mType = 0;
				AotugeApplication.getInstance().mCityItemList.add(topcityitem);
				
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONArray jsonItemArray = jsonObjectTemp.getJSONArray("data");
				for(int i = 0; i < jsonItemArray.length();i++)
				{
					ProvinceCityItem provinceCityItem = new ProvinceCityItem();
					JSONObject jsonItem = jsonItemArray.getJSONObject(i);
					provinceCityItem.provinve = jsonItem.getString("name");
					provinceCityItem.code = jsonItem.getInt("code");
					provinceCityItem.mType = 1;
					JSONArray cityItemArray = jsonItem.getJSONArray("cities");
					for(int j = 0; j < cityItemArray.length();j++)
					{
						CityItem cityItem = new CityItem();
						JSONObject cityjsonItem = cityItemArray.getJSONObject(j);
						cityItem.name = cityjsonItem.getString("name");
						cityItem.code = cityjsonItem.getInt("code");
						provinceCityItem.mCityItemList.add(cityItem);
					}
					AotugeApplication.getInstance().mCityItemList.add(provinceCityItem);
				}
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getSchoolonCity(String ajson,List<SchoolItem> aData) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONArray jsonItemArray = jsonObjectTemp.getJSONArray("data");
				for(int i = 0; i < jsonItemArray.length();i++)
				{
					SchoolItem schoolItem = new SchoolItem();
				
					JSONObject jsonItem = jsonItemArray.getJSONObject(i);
					schoolItem.name = jsonItem.getString("name");
					schoolItem.code = jsonItem.getInt("code");
					schoolItem.mType = 1;
				
					aData.add(schoolItem);
				}
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getPosition(String ajson,List<DirectionsItem> aData) {
		aData.clear();
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "获取成功";
                DirectionsItem toppositionItem = new DirectionsItem();
				
                toppositionItem.Name = "已选择0个职位";
                toppositionItem.Code = 0;
                toppositionItem.mType = 0;
				aData.add(toppositionItem);
				JSONArray jsonItemArray = jsonObjectTemp.getJSONArray("data");
				for(int i = 0; i < jsonItemArray.length();i++)
				{
					String parentName;
					DirectionsItem positionItem = new DirectionsItem();
					JSONObject jsonItem = jsonItemArray.getJSONObject(i);
					
					positionItem.Name = jsonItem.getString("name");
					positionItem.Code = jsonItem.getInt("code");
					positionItem.mType = 1;
					parentName = positionItem.Name;
					JSONArray GridItemArray = jsonItem.getJSONArray("directions");
					for(int j = 0; j < GridItemArray.length();j++)
					{
						DirectionGridItem directionGridItem = new DirectionGridItem();
						JSONObject cityjsonItem = GridItemArray.getJSONObject(j);
						directionGridItem.Name = cityjsonItem.getString("name");
						directionGridItem.Code = cityjsonItem.getInt("code");
						directionGridItem.Type = 1;
						directionGridItem.ParentName = parentName;
						positionItem.mGridItemList.add(directionGridItem);
					}
					aData.add(positionItem);
				}
				
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getInterviews(String ajson,List<InterviewTimeItem> aData,int type) 
	{
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONArray jsonItemArray = jsonObjectTemp.getJSONArray("data");
				for(int i = 0; i < jsonItemArray.length();i++)
				{
					
					JSONObject jsonItem = jsonItemArray.getJSONObject(i);
					InterviewTimeItem item = new InterviewTimeItem();
					item.id = jsonItem.getInt("id"); // int 面试ID
					item.company= jsonItem.getString("company"); // string 公司名称
					item.title =jsonItem.getString("title");// string 职位名称
					item.interview_time =jsonItem.getString("interview_time"); // string 面试时间
					item.status =jsonItem.getInt("status"); // int 状态码
					item.sent_time =jsonItem.getString("sent_time"); // int 状态码
					item.type = type;
//					if(item.status < 20)
//						item.type = 0;
//					else if(item.status >= 20&&item.status<100)
//						item.type = 1;
//					else
//						item.type = 2;
					item.tips= jsonItem.getString("tips"); // string 小提示
					aData.add(item);
				}
				
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getCanlendarInterviews(String ajson,List<InterviewCalendarItem> aInterviewCalendarItem) 
	{
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONObject jsonItemArray = jsonObjectTemp.getJSONObject("data");
				JSONArray jsonItemArrays = jsonItemArray.names();
				for(int i = 0; i < jsonItemArrays.length();i++)
				{
					String name = jsonItemArrays.getString(i);
					
					JSONArray ItemArrays = jsonItemArray.getJSONArray(name);
					
					InterviewCalendarItem item = new InterviewCalendarItem();
					
					item.name = name;
					
					for(int j = 0; j < ItemArrays.length(); j++)
					{
						JSONObject itemobject = ItemArrays.getJSONObject(j);
						InterviewTimeItem timeitem = new InterviewTimeItem();
						timeitem.company = itemobject.getString("company");
						timeitem.id = itemobject.getInt("id");
						timeitem.title = itemobject.getString("title");
						timeitem.interview_time = itemobject.getString("interview_time");
						timeitem.sent_time = timeitem.interview_time;
						timeitem.status = itemobject.getInt("status");
						item.mInterviewTimeItems.add(timeitem);
						timeitem.type = 3;
					}
					aInterviewCalendarItem.add(item);
				}
				
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getInterviewInfo(String ajson,InterviewInfoItem aInterviewInfoItem) 
	{
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONObject jsonItems = jsonObjectTemp.getJSONObject("data");
				aInterviewInfoItem.id = jsonItems.getInt("id"); // int 面试ID
				aInterviewInfoItem.cid = jsonItems.getInt("cid");  // int 公司ID
		       
				aInterviewInfoItem.company=jsonItems.getString("company") ; // string 公司名称
				aInterviewInfoItem.title=jsonItems.getString("title") ; // string 职位名称
				aInterviewInfoItem.address=jsonItems.getString("address") ;// string 公司地址
				aInterviewInfoItem.tips=jsonItems.getString("tips") ; // string 注意事项
				aInterviewInfoItem.interview_time=jsonItems.getString("interview_time") ;// string 面试时间
				aInterviewInfoItem.status= jsonItems.getInt("status"); // int 状态码
				aInterviewInfoItem.ad = jsonItems.getString("ad");
				JSONArray progressArray = jsonItems.getJSONArray("progress");
				
				for(int i = 0; i <progressArray.length();i++)
				{
					JSONObject progressItem = progressArray.getJSONObject(i);
					
					InterviewProgressItem progitem = new InterviewProgressItem();
					progitem.code = progressItem.getInt("code"); // int 状态码
					progitem.name = progressItem.getString("name"); // string 状态名称
					progitem.time = progressItem.getString("time"); // string 操作时间
					aInterviewInfoItem.progress.add(progitem);
				}
				
				aInterviewInfoItem.longitude=jsonItems.getLong("longitude"); // number 经度
				aInterviewInfoItem.latitude=jsonItems.getLong("latitude"); // number 纬度
				
				JSONArray conflictArray = jsonItems.getJSONArray("conflict");
				for(int i = 0; i <conflictArray.length();i++)
				{
					JSONObject progressItem = conflictArray.getJSONObject(i);
					InterviewConflictItem progitem = new InterviewConflictItem();
					progitem.id = progressItem.getInt("id");// int 面试ID
					progitem.company = progressItem.getString("company"); // string 公司名称
					progitem.title = progressItem.getString("title");// string 职位名称
					progitem.interview_time = progressItem.getString("interview_time");// string 面试时间
					aInterviewInfoItem.conflict.add(progitem);
				}
				
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getInterviewAction(String ajson) 
	{
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = jsonObjectTemp.getString("message");
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getInterviewScore(String ajson,List<Interviewscore> aData) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONArray jsonItemArray = jsonObjectTemp.getJSONArray("data");
				for(int i = 0; i < jsonItemArray.length();i++)
				{
					Interviewscore Item = new Interviewscore();
					JSONObject jsonItem = jsonItemArray.getJSONObject(i);
					Item.name = jsonItem.getString("name");
					Item.code = jsonItem.getString("code");
					aData.add(Item);
				}
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getSubmitScore(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "已经提交";
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	/*
	 *      "id": "1",
                "cid": "1",
                "company": "美团网",
                "title": "产品经理（用户产品方向1）",
                "salary": "0",
                "work_type": "1",
                "graduate": "0",
                "location": [
                    {
                        "code": "100100",
                        "name": false
                    },
                    {
                        "code": "330100",
                        "name": "杭州"
                    }
                ],
                "welfare": [
                    "五险一金",
                    "20天年假",
                    "每年12天带薪病假"
                ],
                "update": "2015-08-09 01:49:50",
                "salary_text": "面议",
                "work_type_text": "全职",
                "graduate_text": "不限"
	 */
	public static NetReturnInfo getWorkList(String ajson,List<WorkItem> aData) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONObject datajsonItem = jsonObjectTemp.getJSONObject("data");
				JSONArray jsonItemArray = datajsonItem.getJSONArray("data");
				for(int i = 0; i < jsonItemArray.length();i++)
				{
					JSONObject jsonitem = jsonItemArray.getJSONObject(i);
					WorkItem schoolItem = new WorkItem();
					schoolItem.id = Integer.valueOf(jsonitem.getString("id"));
					schoolItem.cid = Integer.valueOf(jsonitem.getString("cid"));
					schoolItem.company = jsonitem.getString("company");
					schoolItem.title= jsonitem.getString("title");
					schoolItem.salary = Integer.valueOf(jsonitem.getString("salary"));
					schoolItem.worktype =   Integer.valueOf(jsonitem.getString("work_type"));//work_type
					schoolItem.graduate  =   Integer.valueOf(jsonitem.getString("graduate"));
					JSONArray locationItemArray = jsonitem.getJSONArray("location");
					for(int j = 0; j < locationItemArray.length();j++)
					{
						JSONObject locaItem = locationItemArray.getJSONObject(j);
						CityItem cityitem = new CityItem();
						cityitem.code =  Integer.valueOf(locaItem.getString("code"));
						cityitem.name =  locaItem.getString("name");
						schoolItem.location.add(cityitem);
					}
					
					JSONArray welfareItemArray = jsonitem.getJSONArray("welfare");
					for(int k = 0; k < welfareItemArray.length();k++)
					{
						String welfare = (String)welfareItemArray.get(k);
						schoolItem.welfare.add(welfare);
					}
		               
		         
					schoolItem.update = jsonitem.getString("update");
					schoolItem.salary_text = jsonitem.getString("salary_text");
					schoolItem.work_type_text = jsonitem.getString("work_type_text");
					schoolItem.graduate_text = jsonitem.getString("graduate_text");
		             
					schoolItem.update = jsonitem.getString("update_text");
					aData.add(schoolItem);
				}
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getCompanyinfo(String ajson,CompanyInfo aData) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "获取成功";
				
				JSONObject datajsonItem = jsonObjectTemp.getJSONObject("data");
				aData.cid = Integer.valueOf(datajsonItem.getString("cid"));
				aData.company = datajsonItem.getString("company");
				aData.logo =  datajsonItem.getString("logo");
				
				String company_property = datajsonItem.getString("company_property");
				if(company_property.equals("null"))
				{
				   aData.company_property = Integer.valueOf("0");
				}
				else
				{
					 aData.company_property = Integer.valueOf(company_property);
				}
				
				String  company_size = datajsonItem.getString("company_size");
				if(company_size.equals("null"))
					aData.company_size = Integer.valueOf("0");
				else
				{
					aData.company_size = Integer.valueOf(company_size);
				}
				   
				aData.website = datajsonItem.getString("website");
				if(!datajsonItem.isNull("address"))
				aData.address = datajsonItem.getString("address");  
				if(!datajsonItem.isNull("introduce"))
				aData.introduce =  datajsonItem.getString("introduce");  
				aData.registered =  datajsonItem.getString("registered"); 
				if(!datajsonItem.isNull("score"))
				    aData.score = Integer.valueOf(datajsonItem.getString("score")); 
				else
					 aData.score = 4;
				if(!datajsonItem.isNull("score_item"))
				{
			    JSONArray jsonarray = datajsonItem.getJSONArray("score_item");
			    for(int i =0; i <jsonarray.length(); i++)
			    {
			    	JSONObject scoreobject = jsonarray.getJSONObject(i);
			    	CompanyScoreitem scoreitem = new CompanyScoreitem();
			    	scoreitem.score = scoreobject.getInt("score");
			    	scoreitem.name = scoreobject.getString("name");
			    	aData.scorearray.add(scoreitem);
			    }
				}
				else
				{
					
				}
			    
			    aData.company_property_text=datajsonItem.getString("company_property_text");  // string 工作类型文本
			    aData.company_size_text=datajsonItem.getString("company_size_text");// string 学历要求文本
			    //aData.longitude = Long.valueOf(datajsonItem.getString("longitude")); // number 经度
			   // aData.latitude = Long.valueOf(datajsonItem.getString("latitude")); // number 纬度
				
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	/*
	 *   id : 1, // int 职位ID
        cid : 1, // int 企业ID
        company : 'Yahoo!', // string 企业名称
        title: 'Product Manager', // string 职位名称
        salary : 0, // int 薪酬范围
        work_type : 0, // int 工作类型
        graduate : 0, // int 学历要求
        location : [ // array 工作地点
            {
                code : 330701, // string 城市代码
                name : '杭州' // string 级别
            },
            ...
        ],
        welfare : [ // array 福利
            '20天年假', // string 具体福利
            ...
        ],
        requirements : '一段很长的话', // html 职位需求
        update : '2015-08-01 00:00:00' // date 更新时间
        salary_text : '面议', // string 薪酬范围文本
        work_type_text : '不限', // string 工作类型文本
        graduate_text : '不限' // string 学历要求文本
	 */
	
	public static NetReturnInfo getPositioninfo(String ajson,Positioninfo mdata) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "获取成功";
				
				JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
				
				mdata.id = jsonitem.getString("id");
				mdata.cid = jsonitem.getString("cid");
				mdata.company = jsonitem.getString("company");
				mdata.title= jsonitem.getString("title");
				mdata.salary = jsonitem.getString("salary");
				mdata.work_type =   jsonitem.getString("work_type");//work_type
				mdata.graduate  =   jsonitem.getString("graduate");
				JSONArray locationItemArray = jsonitem.getJSONArray("location");
				for(int j = 0; j < locationItemArray.length();j++)
				{
					JSONObject locaItem = locationItemArray.getJSONObject(j);
					CityItem cityitem = new CityItem();
					cityitem.code =  Integer.valueOf(locaItem.getString("code"));
					cityitem.name =  locaItem.getString("name");
					mdata.location.add(cityitem);
				}
				
				JSONArray welfareItemArray = jsonitem.getJSONArray("welfare");
				for(int k = 0; k < welfareItemArray.length();k++)
				{
					String welfare = (String)welfareItemArray.get(k);
					mdata.welfare.add(welfare);
				}
	               
	         
				mdata.update = jsonitem.getString("update");
				mdata.salary_text = jsonitem.getString("salary_text");
				mdata.work_type_text = jsonitem.getString("work_type_text");
				mdata.graduate_text = jsonitem.getString("graduate_text");
				mdata.requirements = jsonitem.getString("requirements");
				mdata.update_text = jsonitem.getString("update_text");
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getCVItem(String ajson, List<CVItem> items) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code==0) {
				items.clear();
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONArray jsonitems = jsonObjectTemp.getJSONArray("data");
				for(int i = 0 ; i < jsonitems.length(); i++)
				{
					JSONObject jsonitem = jsonitems.getJSONObject(i);
					CVItem cvitem = new CVItem();
					cvitem.id = jsonitem.getString("id");
					cvitem.title = jsonitem.getString("title");
					items.add(cvitem);
				}
			}
			else
			{
				itemdata.info = jsonObjectTemp.getString("message");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo deleteCVItem(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code==0) {
				
				itemdata.success = true;
				itemdata.info = jsonObjectTemp.getString("message");
				
			}
			else
			{
				itemdata.info = jsonObjectTemp.getString("message");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getCVInfo(String ajson, CurriculumVitaeItem  curriculumVitaeItem) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code==0) {
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
				String id = jsonitem.getString("id");
				curriculumVitaeItem.id = Integer.valueOf(id);// int 简历ID
				curriculumVitaeItem.title = jsonitem.getString("title");// string 简历标题
				if(!jsonitem.isNull("uid"))
				curriculumVitaeItem.uid = jsonitem.getString("uid");// string 简历标题
				
			    PersonUserInfoItem personUserInfoItem = new PersonUserInfoItem();
				
			    personUserInfoItem.name=jsonitem.getString("name");// string 姓名
			    if(!jsonitem.getString("birthdate").isEmpty())
			    {
			       personUserInfoItem.birthdate=jsonitem.getString("birthdate");// string 出生日期
			       if(personUserInfoItem.birthdate.equals("null"))
			    	   personUserInfoItem.birthdate = "";
			    }
			    else
			      personUserInfoItem.birthdate="1999-01-01";// string 出生日期
			    
			    if(!jsonitem.getString("gender").isEmpty())
			    {
			       personUserInfoItem.gender=jsonitem.getString("gender");// string 性别
			       if(personUserInfoItem.gender.equals("null"))
			    	   personUserInfoItem.gender = "";
			       if(!jsonitem.isNull("gender_text"))
			       personUserInfoItem.gender_text = jsonitem.getString("gender_text");
			    }
				else
				{
				  personUserInfoItem.gender="0";// string 性别
				  personUserInfoItem.gender_text = "男";
				}
			    
			    if(!jsonitem.getString("native").isEmpty())
			    {
			       personUserInfoItem.nativecode=jsonitem.getString("native");// string 籍贯
			       if(personUserInfoItem.nativecode.equals("null"))
			    	   personUserInfoItem.nativecode = "";
			       if(!jsonitem.isNull("native_text"))
				   personUserInfoItem.native_text = jsonitem.getString("native_text");  
			    }
				else
				{
				   personUserInfoItem.nativecode="110000";// string 籍贯
				   personUserInfoItem.native_text = "北京";  
				}
			   
			    curriculumVitaeItem.personUserInfoItem = personUserInfoItem;
			   
			   
			    
			    PersonContactWayItem personContactWayItem = new PersonContactWayItem();
			    if(!jsonitem.getString("email").isEmpty())
			    personContactWayItem.email=jsonitem.getString("email");// string 邮箱地址
			    if(personContactWayItem.email.equals("null"))
			    	  personContactWayItem.email = "";
			    if(!jsonitem.getString("mobile").isEmpty())
			    personContactWayItem.mobile=jsonitem.getString("mobile");// string 手机号
			    if(personContactWayItem.mobile.equals("null"))
			    	personContactWayItem.mobile = "";
			    curriculumVitaeItem.personContactWayItem = personContactWayItem;
			    
			   
			   
			    
			    PersonGrades personGrades = new PersonGrades();
			    if(!jsonitem.getString("computer").isEmpty())
			    {
			    String  computer=jsonitem.getString("computer");//计算机能力
			    if(computer.equals("null"))
			    	computer = "";
			    personGrades.computer = computer;
			    }
			    if(!jsonitem.getString("grade").isEmpty())
			    {
			    String  grade=jsonitem.getString("grade");//计算机能力
			    if(grade.equals("null"))
			    	grade = "";
			    personGrades.grade = grade;
			    }
			   
			     if(!jsonitem.isNull("languages")&&!jsonitem.getString("languages").isEmpty())
			     {
			    	JSONArray languagesarray = jsonitem.getJSONArray("languages");
			    	for(int i =0; i < languagesarray.length();i++)
			    	{
			    		JSONObject languagesjsonitem = languagesarray.getJSONObject(i);
			    		LanguageItem item = new LanguageItem();
			    		item.language= languagesjsonitem.getString("language");//语言
			    		item.level=  languagesjsonitem.getString("level");//语言;//级别
			    		personGrades.languages.add(item);   
			    	}
			     }
			    
			    curriculumVitaeItem.personGrades = personGrades;
			  
			    if(!jsonitem.isNull("education")&&!jsonitem.getString("education").isEmpty())
			    {
			  
			    	JSONArray educationarray = jsonitem.getJSONArray("education");
			    	for(int i =0; i < educationarray.length();i++)
			    	{
			    		JSONObject educationjsonitem = educationarray.getJSONObject(i);
			    	    PersonEducation personEducation = new PersonEducation();
			    	 
					    personEducation.school_text=educationjsonitem.getString("school");// 学校
					    personEducation.school_code=educationjsonitem.getString("school_code");//学校编码
					    personEducation.date_start=educationjsonitem.getString("date_start");// 入学时间，只到年份
					    personEducation.date_end=educationjsonitem.getString("date_end");// 毕业时间，只到年份
					    personEducation.department=educationjsonitem.getString("department");//学院
					    personEducation.major=educationjsonitem.getString("major");//专业
					    personEducation.graduate=educationjsonitem.getString("graduate");//学历
					    personEducation.graduate_code=educationjsonitem.getString("graduate_code");//学历
					    personEducation.description=educationjsonitem.getString("description");//其他说明*/
					    
			    	    curriculumVitaeItem.educations.add(personEducation);
			    	}
			    }
			   
			   
			    if(!jsonitem.isNull("internship")&&!jsonitem.getString("internship").isEmpty())
			    {
			    	JSONArray internshiparray = jsonitem.getJSONArray("internship");
			    	for(int i =0; i < internshiparray.length();i++)
			    	{
			    		JSONObject associationjsonitem = internshiparray.getJSONObject(i);
			    		
			    		PersonInternshipItem personSocialItem = new PersonInternshipItem();
			    		
					    personSocialItem.company=associationjsonitem.getString("company");//公司／实验室
					    personSocialItem.title=associationjsonitem.getString("title");//职务
					    personSocialItem.date_start=associationjsonitem.getString("date_start");//开始时间，年月
					    personSocialItem.date_end=associationjsonitem.getString("date_end");// 结束时间，年月
					    personSocialItem.description=associationjsonitem.getString("description");//其他说明
			    		curriculumVitaeItem.internships.add(personSocialItem);    
			    	}
			    }
			   
                	
			    if(!jsonitem.isNull("association")&&!jsonitem.getString("association").isEmpty())
			    {
			    	JSONArray associationarray = jsonitem.getJSONArray("association");
			    	for(int i =0; i < associationarray.length();i++)
			    	{
			    		JSONObject associationjsonitem = associationarray.getJSONObject(i);
			    		PersonAssociationItem personSocialItem = new PersonAssociationItem();
			    		
					    personSocialItem.society=associationjsonitem.getString("society");//公司／实验室
					    personSocialItem.title=associationjsonitem.getString("title");//职务
					    personSocialItem.date_start=associationjsonitem.getString("date_start");//开始时间，年月
					    personSocialItem.date_end=associationjsonitem.getString("date_end");// 结束时间，年月
					    personSocialItem.description=associationjsonitem.getString("description");//其他说明
			    		curriculumVitaeItem.associations.add(personSocialItem);    
					  
					   
			    	}
			    }
			   
			   
			    if(!jsonitem.isNull("awards")&&!jsonitem.getString("awards").isEmpty())
			    {
			    	JSONArray awardsarray = jsonitem.getJSONArray("awards");
			    	for(int i =0; i < awardsarray.length();i++)
			    	{
			    		JSONObject awardsjsonitem = awardsarray.getJSONObject(i);
			    		PersonAwardsItem personAwardsItem =new PersonAwardsItem();
			    		
						personAwardsItem.award=awardsjsonitem.getString("award");//奖项
						personAwardsItem.date=awardsjsonitem.getString("date");//获奖时间
						personAwardsItem.description="";//其他说明
			    		curriculumVitaeItem.awards.add(personAwardsItem);
					    
			    	}
			    }
			   
			   
			}
			else
			{
				itemdata.info = jsonObjectTemp.getString("message");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getCVInfonew(String ajson, CurriculumVitaeItem  curriculumVitaeItem,List<PersonInfoBase> mData) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code==0) {
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
				String id = jsonitem.getString("id");
				curriculumVitaeItem.id = Integer.valueOf(id);// int 简历ID
				curriculumVitaeItem.title = jsonitem.getString("title");// string 简历标题
				

			    PersonUserInfoItem personUserInfoItem = new PersonUserInfoItem();
			    //personUserInfoItem.uid = AotugeApplication.getInstance().mAotuInfo.uid;// int 用户ID
			    personUserInfoItem.name=jsonitem.getString("name");// string 姓名
			    personUserInfoItem.birthdate=jsonitem.getString("birthdate");// string 出生日期
			    personUserInfoItem.gender=jsonitem.getString("gender");// string 性别
			    personUserInfoItem.nativecode=jsonitem.getString("native");// string 籍贯
			    if(!jsonitem.isNull("native_text"))
			    {
			    personUserInfoItem.native_text = jsonitem.getString("native_text");  
			    }
			    if(!jsonitem.isNull("gender_text"))
			    {
			    personUserInfoItem.gender_text = jsonitem.getString("gender_text");
			    }
			    
			    curriculumVitaeItem.personUserInfoItem = personUserInfoItem;
			    personUserInfoItem.type = 0;
			    mData.add(personUserInfoItem);
			    
			    PersonContactWayItem personContactWayItem = new PersonContactWayItem();
			    personContactWayItem.email=jsonitem.getString("email");// string 邮箱地址
			    personContactWayItem.mobile=jsonitem.getString("mobile");// string 手机号
			    curriculumVitaeItem.personContactWayItem = personContactWayItem;
			    
			    personContactWayItem.type = 1;
			    mData.add(personContactWayItem);
			    
			  
			    String  computer=jsonitem.getString("computer");//计算机能力
			    String  grade=jsonitem.getString("grade");//计算机能力
			    PersonGrades personGrades = new PersonGrades();
			    personGrades.computer = computer;
			    personGrades.grade = grade;
			    
			    if(!jsonitem.isNull("languages"))
			    {
			    	JSONArray languagesarray = jsonitem.getJSONArray("languages");
			    	for(int i =0; i < languagesarray.length();i++)
			    	{
			    		JSONObject languagesjsonitem = languagesarray.getJSONObject(i);
			    		LanguageItem item = new LanguageItem();
			    		item.language= languagesjsonitem.getString("language");//语言
			    		item.level=  languagesjsonitem.getString("level");//语言;//级别
			    		personGrades.languages.add(item);   
			    	}
			    }
			    personGrades.type = 2;
			 	mData.add(personGrades);
			    curriculumVitaeItem.personGrades = personGrades;
			 
			    if(!jsonitem.isNull("education"))
			    {
			    	JSONArray educationarray = jsonitem.getJSONArray("education");
			    	for(int i =0; i < educationarray.length();i++)
			    	{
			    		JSONObject educationjsonitem = educationarray.getJSONObject(i);
			    	    PersonEducation personEducation = new PersonEducation();
			    	    if(!jsonitem.isNull("school"))
					      personEducation.school_text=educationjsonitem.getString("school");// 学校
					    if(!jsonitem.isNull("school_text"))
					    personEducation.school_text = jsonitem.getString("school_text");
					    personEducation.school_code=educationjsonitem.getString("school_code");//学校编码
					    personEducation.date_start=educationjsonitem.getString("date_start");// 入学时间，只到年份
					    personEducation.date_end=educationjsonitem.getString("date_end");// 毕业时间，只到年份
					    personEducation.department=educationjsonitem.getString("department");//学院
					    personEducation.major=educationjsonitem.getString("major");//专业
					    personEducation.graduate=educationjsonitem.getString("graduate");//学历
					    personEducation.graduate_code=educationjsonitem.getString("graduate_code");//学历
					    personEducation.description=educationjsonitem.getString("description");//其他说明*/
					    personEducation.type = 3;
			    	    curriculumVitaeItem.educations.add(personEducation);
					    mData.add(personEducation);
			    	}
			    }
			    else
			    {
			        PersonEducation personEducation = new PersonEducation();
			        curriculumVitaeItem.educations.add(personEducation);
				    personEducation.type = 7;
				    mData.add(personEducation);
			    }
			    
			    if(!jsonitem.isNull("association"))
			    {
			    	JSONArray associationarray = jsonitem.getJSONArray("association");
			    	for(int i =0; i < associationarray.length();i++)
			    	{
			    		JSONObject associationjsonitem = associationarray.getJSONObject(i);
			    		PersonAssociationItem personSocialItem = new PersonAssociationItem();
					    personSocialItem.society=associationjsonitem.getString("society");//公司／实验室
					    personSocialItem.title=associationjsonitem.getString("title");//职务
					    personSocialItem.date_start=associationjsonitem.getString("date_start");//开始时间，年月
					    personSocialItem.date_end=associationjsonitem.getString("date_end");// 结束时间，年月
					    personSocialItem.description=associationjsonitem.getString("description");//其他说明
			    		curriculumVitaeItem.associations.add(personSocialItem);    
					    personSocialItem.type = 5;
					    mData.add(personSocialItem);
			    	}
			    }
			    else
			    {
			    	PersonAssociationItem personSocialItem = new PersonAssociationItem();
				    curriculumVitaeItem.associations.add(personSocialItem);
				    personSocialItem.type = 9;
				    mData.add(personSocialItem);
			    }
			    
			    if(!jsonitem.isNull("internship"))
			    {
			    	JSONArray associationarray = jsonitem.getJSONArray("internship");
			    	for(int i =0; i < associationarray.length();i++)
			    	{
			    		JSONObject associationjsonitem = associationarray.getJSONObject(i);
			    		PersonInternshipItem personSocialItem = new PersonInternshipItem();
					    personSocialItem.company=associationjsonitem.getString("company");//公司／实验室
					    personSocialItem.title=associationjsonitem.getString("title");//职务
					    personSocialItem.date_start=associationjsonitem.getString("date_start");//开始时间，年月
					    personSocialItem.date_end=associationjsonitem.getString("date_end");// 结束时间，年月
					    personSocialItem.description=associationjsonitem.getString("description");//其他说明
			    		curriculumVitaeItem.internships.add(personSocialItem);    
					    personSocialItem.type = 4;
					    mData.add(personSocialItem);
			    	}
			    }
			    else
			    {
			    	PersonInternshipItem personSocialItem = new PersonInternshipItem();
				    curriculumVitaeItem.internships.add(personSocialItem);
				    personSocialItem.type = 8;
				    mData.add(personSocialItem);
			    }
			    
			    if(!jsonitem.isNull("awards"))
			    {
			    	JSONArray awardsarray = jsonitem.getJSONArray("awards");
			    	for(int i =0; i < awardsarray.length();i++)
			    	{
			    		JSONObject awardsjsonitem = awardsarray.getJSONObject(i);
			    		PersonAwardsItem personAwardsItem =new PersonAwardsItem();
						personAwardsItem.award=awardsjsonitem.getString("award");//奖项
						personAwardsItem.date=awardsjsonitem.getString("date");//获奖时间
						personAwardsItem.description="";//其他说明
			    		curriculumVitaeItem.awards.add(personAwardsItem);
					    personAwardsItem.type = 6;
						mData.add(personAwardsItem);
			    	}
			    }
			    else
			    {
			    	  PersonAwardsItem personAwardsItem =new PersonAwardsItem();
					  curriculumVitaeItem.awards.add(personAwardsItem);
					  personAwardsItem.type = 10;
					  mData.add(personAwardsItem);
			    }
			}
			else
			{
				itemdata.info = jsonObjectTemp.getString("message");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	/*
	 * 
	 */
	
	public static NetReturnInfo getHomePageItemData(String ajson,List<HomePageItemData> aData) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONArray jsonItemArray = jsonObjectTemp.getJSONArray("data");
				for(int i = 0; i < jsonItemArray.length();i++)
				{
					HomePageItemData intentionitem = new HomePageItemData();
					JSONObject jsonItem = jsonItemArray.getJSONObject(i);
					intentionitem.id = jsonItem.getInt("id");
					intentionitem.cid = jsonItem.getInt("cid");
					intentionitem.company = jsonItem.getString("company");
					intentionitem.title = jsonItem.getString("title");
					intentionitem.salary = jsonItem.getInt("salary");
					intentionitem.work_type = jsonItem.getInt("work_type");
					intentionitem.graduate = jsonItem.getInt("graduate");
					
					JSONArray citiyItemArray = jsonItem.getJSONArray("location");
					for(int j =0; j < citiyItemArray.length();j++)
					{
						JSONObject citiyItem = citiyItemArray.getJSONObject(j);
						CityItem aCityitem = new CityItem();
						aCityitem.code = citiyItem.getInt("code");
						aCityitem.name = citiyItem.getString("name");
						intentionitem.location.add(aCityitem);
					}
					
					JSONArray welfareArray = jsonItem.getJSONArray("welfare");
					for(int j =0; j < welfareArray.length();j++)
					{
						String welfare =(String) welfareArray.get(j);
						
						intentionitem.welfare.add(welfare);
					}
					intentionitem.update = jsonItem.getString("update");
				
				
					intentionitem.salary_text = jsonItem.getString("salary_text");
					intentionitem.work_type_text = jsonItem.getString("work_type_text");
					intentionitem.graduate_text = jsonItem.getString("graduate_text");
					intentionitem.update = jsonItem.getString("update_text");
					aData.add(intentionitem);
				}
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getIntention(String ajson,List<IntentionItem> aData) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				aData.clear();
				itemdata.success = true;
				itemdata.info = "获取成功";
				JSONArray jsonItemArray = jsonObjectTemp.getJSONArray("data");
				for(int i = 0; i < jsonItemArray.length();i++)
				{
					IntentionItem intentionitem = new IntentionItem();
					JSONObject jsonItem = jsonItemArray.getJSONObject(i);
					intentionitem.id = jsonItem.getInt("id");
					intentionitem.name = jsonItem.getString("name");
					
					JSONArray citiyItemArray = jsonItem.getJSONArray("cities");
					for(int j =0; j < citiyItemArray.length();j++)
					{
						JSONObject citiyItem = citiyItemArray.getJSONObject(j);
						CityItem aCityitem = new CityItem();
						aCityitem.code = citiyItem.getInt("code");
						aCityitem.name = citiyItem.getString("name");
						intentionitem.cities.add(aCityitem);
					}
					
					JSONArray posItemArray = jsonItem.getJSONArray("position");
					for(int j =0; j < posItemArray.length();j++)
					{
						JSONObject posItem = posItemArray.getJSONObject(j);
						BaseChildItem aCityitem = new BaseChildItem();
						aCityitem.code = posItem.getInt("code");
						aCityitem.name = posItem.getString("name");
						intentionitem.position.add(aCityitem);
					}
				
					intentionitem.salary = jsonItem.getInt("salary");
					intentionitem.work_type = jsonItem.getInt("work_type");
					intentionitem.salary_text = jsonItem.getString("salary_text");
					intentionitem.work_type_text = jsonItem.getString("work_type_text");
					aData.add(intentionitem);
				}
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	
	public static NetReturnInfo getCVSend(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code==0) {
				itemdata.success = true;
				itemdata.info = jsonObjectTemp.getString("message");
			}
			else
			{
				itemdata.info = jsonObjectTemp.getString("message");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getCV(String ajson,String cvid) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "注册成功";
				cvid = String.valueOf(jsonObjectTemp.getInt("data"));
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getUserinfo(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				
				itemdata.success = true;
				itemdata.info = "登陆成功";
				JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
				int uid = jsonitem.getInt("uid");
				AotugeApplication.getInstance().mAotuInfo.uid = uid;
				AotugeApplication.getInstance().mAotuInfo.token = jsonitem.getString("token");
				AotugeApplication.getInstance().mAotuInfo.face = jsonitem.getString("face");
				AotugeApplication.getInstance().mAotuInfo.name = jsonitem.getString("name");
				AotugeApplication.getInstance().mAotuInfo.mobile= jsonitem.getString("mobile");
				String email = jsonitem.getString("email");
			    if(email.equals("null"))
			    	email = "";
				AotugeApplication.getInstance().mAotuInfo.email=email ;
				
				String idcard = jsonitem.getString("idcard");
			    if(idcard.equals("null"))
			    	idcard = "";
				AotugeApplication.getInstance().mAotuInfo.idcard= idcard;
				
				String birthdate = jsonitem.getString("birthdate");
			    if(birthdate.equals("null"))
			    	birthdate = "1999-01-01";
				AotugeApplication.getInstance().mAotuInfo.birthdate= birthdate;
				
				String gender = jsonitem.getString("gender");
			    if(gender.equals("null"))
			    	gender = "1";
				AotugeApplication.getInstance().mAotuInfo.gender = gender;
				
				String usernative = jsonitem.getString("native");
			    if(usernative.equals("null"))
			    	usernative = "330800";
				AotugeApplication.getInstance().mAotuInfo.usernative = usernative;
				
				String graduate = jsonitem.getString("graduate");
			    if(graduate.equals("null"))
			    	graduate = "2";
				AotugeApplication.getInstance().mAotuInfo.graduate = graduate;
				
				String school = jsonitem.getString("school");
			    if(school.equals("null"))
			    	school = "10520";
				AotugeApplication.getInstance().mAotuInfo.school = school;
				
				String major = jsonitem.getString("major");
			    if(major.equals("null"))
			    	major = "";
				AotugeApplication.getInstance().mAotuInfo.major = major;
				
				AotugeApplication.getInstance().mAotuInfo.balance = jsonitem.getLong("balance");
				
				String school_text = jsonitem.getString("school_text");
			    if(school_text.equals("null"))
			    	school_text = "";
				AotugeApplication.getInstance().mAotuInfo.school_text = school_text;
				String native_text = "";
				if(!jsonitem.isNull("native_text"))
				{
					native_text = jsonitem.getString("native_text");
					    if(native_text.equals("null"))
					    	native_text = "";
				}
				AotugeApplication.getInstance().mAotuInfo.native_text = native_text;
				
				String gender_text = "";
				if(!jsonitem.isNull("gender_text"))
				{
					gender_text = jsonitem.getString("gender_text");
					    if(native_text.equals("null"))
					    	gender_text = "";
				}
				
				AotugeApplication.getInstance().mAotuInfo.gender_text = gender_text;  	
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	
	public static NetReturnInfo getUserHead(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				
				itemdata.success = true;
				String info = jsonObjectTemp.getString("data");
				AotugeApplication.getInstance().mAotuInfo.face = info;
				//itemdata.info = jsonObjectTemp.getString("message");	
				//JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
				
			
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getSetUserInfo(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				
				itemdata.success = true;
				itemdata.info = jsonObjectTemp.getString("message");	
				//JSONObject jsonitem = jsonObjectTemp.getJSONObject("data");
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}

	//Reconciliations
	
	public static NetReturnInfo getReconciliations(String ajson,List<ReconciliationsItem> aData) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				
				JSONObject jsonItemArray = jsonObjectTemp.getJSONObject("data");
				String balance = jsonItemArray.getString("balance");
				itemdata.info = balance;
				JSONArray ReconciliationsArray = jsonItemArray.getJSONArray("reconciliations");
				for(int i = 0; i < ReconciliationsArray.length();i++)
				{
					ReconciliationsItem reconciliationsItem = new ReconciliationsItem();
					JSONObject jsonItem = ReconciliationsArray.getJSONObject(i);
					reconciliationsItem.id = jsonItem.getInt("id");
					reconciliationsItem.title = jsonItem.getString("title");
					reconciliationsItem.time = jsonItem.getString("time");
					reconciliationsItem.amount = jsonItem.getInt("amount");
					if(reconciliationsItem.amount >0)
						reconciliationsItem.mType = 1;
					reconciliationsItem.balance = balance;  
					aData.add(reconciliationsItem);
				}
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getDeleteintention(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				
				itemdata.success = true;
				itemdata.info = jsonObjectTemp.getString("message");	
				
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getAddintention(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = "添加成功";		
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getTixian(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				itemdata.info = jsonObjectTemp.getString("message");			
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	
	public static NetReturnInfo getTixianInfo(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				
				JSONObject jsonItem = jsonObjectTemp.getJSONObject("data");
				AotugeApplication.getInstance().mTixianInfo.balance = jsonItem.getString("balance");
				JSONArray ways = jsonItem.getJSONArray("ways");
				AotugeApplication.getInstance().mTixianInfo.Ways.clear();
				for(int i =0;i< ways.length(); i++)
				{
					JSONObject wayitem = ways.getJSONObject(i);
					Ways waysitem = new Ways();
					waysitem.code = wayitem.getString("code");
					waysitem.name =  wayitem.getString("name");
					AotugeApplication.getInstance().mTixianInfo.Ways.add(waysitem);
				}
				JSONObject defaultjsonItem = jsonItem.getJSONObject("default");
				
				AotugeApplication.getInstance().mTixianInfo.name =  defaultjsonItem.getString("name");
				AotugeApplication.getInstance().mTixianInfo.account = defaultjsonItem.getString("account");
				AotugeApplication.getInstance().mTixianInfo.way = defaultjsonItem.getString("way");
				
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}

	public static NetReturnInfo getShuangXuan(String ajson,List<ShuangXuanItem> mItemList) {

		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				
				JSONObject jsonItem = jsonObjectTemp.getJSONObject("data");
				
				JSONArray ways = jsonItem.getJSONArray("data");
				mItemList.clear();
				for(int i =0;i< ways.length(); i++)
				{
					JSONObject wayitem = ways.getJSONObject(i);
					ShuangXuanItem waysitem = new ShuangXuanItem();
					waysitem.address = wayitem.getString("address");
					waysitem.city =  wayitem.getString("city");
					waysitem.id =  wayitem.getString("id");
					waysitem.name =  wayitem.getString("name");
					waysitem.province =  wayitem.getString("province");
					waysitem.time =  wayitem.getString("time");
					mItemList.add(waysitem);
				}
			
				
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	
	}
	
	public static NetReturnInfo getTicket(String ajson,List<EntranceTicket> mItemList) {

		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			if (code == 0) {
				itemdata.success = true;
				
				JSONObject jsonItem = jsonObjectTemp.getJSONObject("data");
				
				JSONArray ways = jsonItem.getJSONArray("data");
				mItemList.clear();
				for(int i =0;i< ways.length(); i++)
				{
					JSONObject wayitem = ways.getJSONObject(i);
					EntranceTicket waysitem = new EntranceTicket();
					waysitem.address = wayitem.getString("address");
					waysitem.city =  wayitem.getString("city");
					waysitem.id =  wayitem.getString("id");
					waysitem.name =  wayitem.getString("name");
					waysitem.province =  wayitem.getString("province");
					waysitem.time =  wayitem.getString("time");
					waysitem.qr = wayitem.getString("qr");
					mItemList.add(waysitem);
				}
			
				
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	
	}
	
	
	public static NetReturnInfo getPersonCurriculumVitae(String ajson) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			int code = jsonObjectTemp.getInt("status");
			itemdata.status = code;
			 itemdata.info = jsonObjectTemp.getString("message");
			if (code == 0) {
				
				itemdata.success = true;
			}
			else
			{
			   itemdata.info = jsonObjectTemp.getString("message");	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}
	
	public static NetReturnInfo getUpCheck(String ajson, CheckUpdate mCheckUpdate) {
		NetReturnInfo itemdata = new NetReturnInfo();
		JSONObject jsonObjectTemp = null;
		try {
			jsonObjectTemp = new JSONObject(ajson);
			mCheckUpdate.status = jsonObjectTemp.getInt("status");
			JSONObject jsonItem = jsonObjectTemp.getJSONObject("data");
			mCheckUpdate.version = jsonItem.getInt("version");
			mCheckUpdate.url = jsonItem.getString("url");
			mCheckUpdate.introduction = jsonItem.getString("introduction");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemdata;
	}

}
