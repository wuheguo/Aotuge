package com.aotu.net;

public class NetUrlManager {
	//private static final String URL_HOST = "http://aotuge.honglei.net/interface";
	private static final String URL_HOST = "http://api.aotujob.com";
	private static final String URL_CHECKUP = URL_HOST +"/update.php?";
	private static final String URL_GET_DATA_GRADE =URL_HOST + "/data_grade.php";
	private static final String URL_GET_BASE_DATA = URL_HOST +"/data_base.php";
	private static final String URL_USER_SIGNIN = URL_HOST +"/user_signin.php";//登陆
	private static final String URL_USER_SIGNOUT = URL_HOST + "/user_signout.php";//登出
	private static final String URL_REGISTER = URL_HOST + "/user_signup.php";
	private static final String URL_GET_CODE = URL_HOST + "/user_send_code.php?mobile=";
	private static final String URL_USER_CHECK = URL_HOST + "/user_check.php?mobile=";//用户检测
	
	
	private static final String URL_USERINFO_SET = URL_HOST +"/user_info.php";
	
	private static final String URL_GET_PCDATA = URL_HOST +"/data_pcd.php?filter=all";//基础数据-省市
	private static final String URL_GET_SCHOOL = URL_HOST +"/data_schools.php?pcode=";//基础数据-院校
	private static final String URL_POSTION = URL_HOST + "/data_position.php";//基础数据-行业方向
	
	private static final String URL_FIND_PASSWORD = URL_HOST + "/user_resetpwd.php";//用户-修改密码
	
	private static final String URL_RECONCILIATIONS = URL_HOST +"/reconciliations.php";//bug0000380
	
	private static final String URL_INTERVIEW = URL_HOST  +"/interview.php?type=";//面试信息-列表
	private static final String URL_INTERVIEW_DETAILS = URL_HOST +"/interview.php?id=";
	private static final String URL_INTERVIEW_ACTION = URL_HOST+ "/interview.php?";
	private static final String URL_INTERVIEW_SCORE = URL_HOST+"/interview_score.php";
	private static final String URL_INTERVIEW_SCORE_SUBMIT = URL_HOST+"/interview_score.php";
	
	private static final String URL_POSITION_INFO = URL_HOST+"/jd.php?id=";
	private static final String URL_COMPANY_INFO =  URL_HOST+"/company.php?id=";
	private static final String URL_COMPANY_LIST = URL_HOST+"/search.php?id=";
	
	private static final String URL_SEND_CV = URL_HOST+"/cv_send.php";
	private static final String URL_GET_CV_LIST = URL_HOST+"/cv.php";
	private static final String URL_SAVE_CV_LIST = URL_HOST+"/cv.php";
	private static final String URL_GET_CV_INFO = URL_HOST+"/cv.php?id=";
	private static final String URL_DELETE_CV = URL_HOST+"/cv.php?action=delete&id=";
	
	private static final String URL_INTENTION_LIST = URL_HOST+"/intention.php";
	private static final String URL_ADD_INTENTION = URL_HOST+"/intention.php";
	private static final String URL_DELETE_INTENTION = URL_HOST+"/intention.php?action=delete&id=";
	private static final String URL_INTENTION_ITEM = URL_HOST+"/intention.php?type=select";
	
	private static final String URL_SEARCH_WORK = URL_HOST+"/search.php?";
	
	
	private static final String URL_HOME_PAGE = URL_HOST+ "/hp.php";
	
	
	
	private static final String URL_HEAD_IMAGE = URL_HOST +"/user_face.php";
	
	private static final String URL_TI_XIAN = URL_HOST +"/withdraw.php";
	private static final String URL_GET_TI_XIAN_INFO = URL_HOST +"/withdraw.php";
	private static final String URL_GET_INTERVIEW_DATE = URL_HOST +"/interview.php?date=";
	
	private static final String URL_GET_SUAN_XUAN = URL_HOST +"/shuangxuanhui.php";
    private static final String URL_GET_TICKET =  URL_HOST + "/ticket.php";
	public static String getBaseDataUrl()
	{
		return URL_GET_BASE_DATA;
	}
	public static String getBaseGradeUrl()
	{
		return URL_GET_DATA_GRADE;
	}
	
    public static String getUserSignout()
    {
    	return URL_USER_SIGNOUT;
    }
    
    public static String getCheckUpUrl()
    {
    	return URL_CHECKUP;
    }
    
    public static String getUserSignin()
    {
    	return URL_USER_SIGNIN;
    }
    
    public static String getRegisterUrl()
    {
    	return URL_REGISTER;
    }
    
    public static String getCodeUrl(String mobile)
    {
    	return URL_GET_CODE+mobile;
    }
    
    public static String getUserInfoSetUrl()
    {
    	return URL_USERINFO_SET;
    }
    
    public static String getPCDataUrl()
    {
    	return URL_GET_PCDATA;
    }
    
    public static String getSchoolUrl(String city)
    {
    	return URL_GET_SCHOOL+city;
    }
    
    public static String getFindPaddwordUrl()
    {
    	return URL_FIND_PASSWORD;
    }
    
    public static String getPostionUrl()
    {
    	return URL_POSTION;
    	
    }
    
    public static String getReconciliations()
    {
    	return URL_RECONCILIATIONS;
    }
    
    public static String getInterview(String type)
    {
    	return URL_INTERVIEW+type;
    }
    
    public static String getInterViewDetails(String id)
    {
    	return URL_INTERVIEW_DETAILS+id;
    }
 
    public static String getInterViewAction(String id,String action)
    {
    	return URL_INTERVIEW_ACTION+"id="+id+"&action="+action;
    }
    
    public static String getInterviewScore()
    {
    	return URL_INTERVIEW_SCORE;
    }
    
    public static String getInterviewSumbitScore()
    {
    	return URL_INTERVIEW_SCORE_SUBMIT;
    }
    
    public static String getCompanyInfo(String id)
    {
    	return URL_COMPANY_INFO + id;
    }
    
    public static String getCompanyList(String id)
    {
    	return URL_COMPANY_LIST + id;
    }
    
    public static String getPositionInfo(String id)
    {
    	return URL_POSITION_INFO + id;
    }
    
    public static String getSaveCVURL()
    {
    	return URL_SAVE_CV_LIST;
    }
    
    public static String getSendCvURL()
    {
    	return URL_SEND_CV;
    }
    
    public static String getCVInfoURL(String id)
    {
    	return  URL_GET_CV_INFO+id;
    }
    public static String getDeleteCV(String id)
    {
    	return URL_DELETE_CV+id;
    }
    public static String getCVList()
    {
    	return URL_GET_CV_LIST;
    }
 
    public static String getIntentionList()
     {
    	 return URL_INTENTION_LIST;
     }
     
     public static String getAddIntention()
     {
    	 return URL_ADD_INTENTION;
     }
     
     public static String getDeleteIntention(String id)
     {
    	 return URL_DELETE_INTENTION+id;
     }
     
     public static String getIntentionItems()
     {
    	 return URL_INTENTION_ITEM;
     }
     
     public static String getSearchWork()
     {
    	 return URL_SEARCH_WORK;
     }
     
     public static String getHomePage()
     {
    	 return URL_HOME_PAGE;
     }
     
   
     
     public static String getHeadImage()
     {
    	 return URL_HEAD_IMAGE;
     }
     
     public static String getUserCheck(String mobile)
     {
    	 return URL_USER_CHECK+mobile;
     }
     
     public static String getTixianUrl()
     {
    	 return URL_TI_XIAN;
     }
     
     public static String getTixianInfoUrl()
     {
    	 return URL_GET_TI_XIAN_INFO;
     }
     
     public static String getInterviewDate(String date)
     {
    	 return URL_GET_INTERVIEW_DATE+date;
     }
     public static String getSuanxuanURL()
     {
    	 return URL_GET_SUAN_XUAN;
     }
     
     public static String getTicketURL()
     {
    	 return URL_GET_TICKET;
     }
}
