package com.aotu.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.aotu.baseview.CustomGridView;
import com.aotu.data.CurriculumVitaeItem;
import com.aotu.data.HomePageItemData;
import com.aotu.data.DirectionGridItem;
import com.aotu.data.DirectionsItem;
import com.aotu.data.PersonAssociationItem;
import com.aotu.data.PersonAwardsItem;
import com.aotu.data.PersonContactWayItem;
import com.aotu.data.PersonEducation;
import com.aotu.data.PersonInfoBase;
import com.aotu.data.PersonGrades;
import com.aotu.data.PersonInternshipItem;
import com.aotu.data.PersonUserInfoItem;
import com.aotu.data.SchoolItem;
import com.auto.aotuge.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CurriculumVitaeAdapter extends BaseAdapter {

	private List<PersonInfoBase> mData;
	private Context mContext;
	private LayoutInflater mInflater;
    private CurriculumVitaeInterface mCurriculumVitaeInterface;
    
	public interface CurriculumVitaeInterface {
		public void onAddLanguage();
		public void onGrade();
		public void onComputer();
		public void onChange(PersonEducation item);
		public void onAddPersonEducation();
		
		public void onChangeInternships(PersonInternshipItem item);
		public void onAddInternships();
		public void onChangeAssociations(PersonAssociationItem item);
		public void onAddAssociations();
	
		public void onChange(PersonAwardsItem item);
		public void onAddAwards();
		public void onSex();
		public void onBirthday();
		public void onNative();
		
		public void onChangeName(String name);
		public void onChangeEmail(String email);
		public void onChangeMobile(String moblie);
	}
	
	
	TextWatcher mName = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			String text = arg0.toString();
			PersonUserInfoItem item = (PersonUserInfoItem)mData.get(0);
			item.name = text;	
			mCurriculumVitaeInterface.onChangeName(text);
		}
	};
	
	TextWatcher mMoblie = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			String text = arg0.toString();
			PersonContactWayItem item = (PersonContactWayItem)mData.get(1);
			item.mobile = text;	
			mCurriculumVitaeInterface.onChangeMobile(text);
		}
	};
	
	TextWatcher mEmail = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			String text = arg0.toString();
			PersonContactWayItem item = (PersonContactWayItem)mData.get(1);
			item.email = text;	
			mCurriculumVitaeInterface.onChangeEmail(text);
		}
	};
	public CurriculumVitaeAdapter(Context context, List<PersonInfoBase> data,CurriculumVitaeInterface aCurriculumVitaeInterface) {
		this.mContext = context;
		this.mData = data;
		mInflater = LayoutInflater.from(mContext);
		mCurriculumVitaeInterface = aCurriculumVitaeInterface;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		PersonInfoBase itemData = mData.get(position);
		return itemData.type;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 11;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		PersonInfoBase itemData = mData.get(position);
		int messageType = getItemViewType(position);
		
		TabViewHolder tabViewHolder = null;
		ContactinformationHolder   contactinformationHolder = null;
		GradeViewHolder gradeViewHolder = null;
		EducationViewHolder educationViewHolder = null;
		InternshipViewHolder internshipViewHolder = null;
		AssociationViewHolder associationViewHolder = null;
		AwardsViewHolder awardsViewHolder = null;
	
		if (convertView == null) {
			switch(messageType)
			{
				case 0:
				{
				  tabViewHolder = new TabViewHolder();
				  convertView = mInflater.inflate(R.layout.item_curriculum_vitae_info, null);
				  tabViewHolder.tx_company=(EditText)convertView.findViewById(R.id.tx_company);
				  tabViewHolder.tx_company.addTextChangedListener(mName);
				  tabViewHolder.tx_sex=(TextView)convertView.findViewById(R.id.tx_sex);
				  tabViewHolder.tx_sex.setTag(itemData);
				  tabViewHolder.tx_sex.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							mCurriculumVitaeInterface.onSex();
						}
					});
				  tabViewHolder.tx_birthday=(TextView)convertView.findViewById(R.id.tx_birthday);
				  tabViewHolder.tx_birthday.setTag(itemData);
				  tabViewHolder.tx_birthday.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							mCurriculumVitaeInterface.onBirthday();
						}
					});
				  tabViewHolder.tx_native=(TextView)convertView.findViewById(R.id.tx_native);
				  tabViewHolder.tx_native.setTag(itemData);
				  tabViewHolder.tx_native.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							mCurriculumVitaeInterface.onNative();
						}
					});
				  convertView.setTag(tabViewHolder);
				}
				break;
				case 1:
				{
					contactinformationHolder = new ContactinformationHolder();
					convertView = mInflater.inflate(R.layout.item_contact_information, null);
					contactinformationHolder.tx_moblie = (EditText)convertView.findViewById(R.id.tx_moblie);
					contactinformationHolder.tx_moblie.addTextChangedListener(mMoblie);
					contactinformationHolder.tx_email = (EditText)convertView.findViewById(R.id.tx_email);
					contactinformationHolder.tx_email.addTextChangedListener(mEmail);
					convertView.setTag(contactinformationHolder);
				}
					break;
				case 2:
				{
					gradeViewHolder = new GradeViewHolder();
					convertView = mInflater.inflate(R.layout.item_curriculum_vitae_grade, null);
					gradeViewHolder.rl_grade = (RelativeLayout)convertView.findViewById(R.id.rl_grade);
					gradeViewHolder.rl_grade.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							mCurriculumVitaeInterface.onGrade();
						}
					});
					gradeViewHolder.tx_grade = (TextView)convertView.findViewById(R.id.tx_grade);
					
					gradeViewHolder.rl_computer = (RelativeLayout)convertView.findViewById(R.id.rl_computer);
					gradeViewHolder.rl_computer.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							mCurriculumVitaeInterface.onComputer();
						}
					});
					gradeViewHolder.tx_computer = (TextView)convertView.findViewById(R.id.tx_computer);
					gradeViewHolder.tx_languages = (TextView)convertView.findViewById(R.id.tx_languages);
					gradeViewHolder.rl_languages = (RelativeLayout)convertView.findViewById(R.id.rl_languages);
					gradeViewHolder.rl_languages.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							mCurriculumVitaeInterface.onAddLanguage();
						}
					});
					convertView.setTag(gradeViewHolder);
				}
				break;
				case 3:
				{
					educationViewHolder = new EducationViewHolder();
					convertView = mInflater.inflate(R.layout.item_curriculum_vitae_education, null);
					
					educationViewHolder.tx_school = (TextView)convertView.findViewById(R.id.tx_school);
					educationViewHolder.rl_add = (RelativeLayout)convertView.findViewById(R.id.rl_add);
					educationViewHolder.rl_headtab = (RelativeLayout)convertView.findViewById(R.id.rl_headtab);
					educationViewHolder.tx_educationinfo= (TextView)convertView.findViewById(R.id.tx_educationinfo);
					educationViewHolder.tx_change = (TextView)convertView.findViewById(R.id.tx_change);
					educationViewHolder.tx_description= (TextView)convertView.findViewById(R.id.tx_description);
					convertView.setTag(educationViewHolder);
				}
				break;
				case 4:
				{
					internshipViewHolder = new InternshipViewHolder();
                    convertView = mInflater.inflate(R.layout.item_curriculum_vitae_internship, null);
                    internshipViewHolder.rl_headtab = (RelativeLayout)convertView.findViewById(R.id.rl_headtab);
                    internshipViewHolder.tx_company = (TextView)convertView.findViewById(R.id.tx_company);
					internshipViewHolder.rl_add = (RelativeLayout)convertView.findViewById(R.id.rl_add);
					internshipViewHolder.tx_info= (TextView)convertView.findViewById(R.id.tx_info);
					internshipViewHolder.tx_change = (TextView)convertView.findViewById(R.id.tx_change);
					internshipViewHolder.tx_description= (TextView)convertView.findViewById(R.id.tx_description);
					convertView.setTag(internshipViewHolder);
				}
				break;
				case 5:
				{
					associationViewHolder = new AssociationViewHolder();
                    convertView = mInflater.inflate(R.layout.item_curriculum_vitae_associaltion, null);
                    associationViewHolder.rl_headtab = (RelativeLayout)convertView.findViewById(R.id.rl_headtab);
                    associationViewHolder.tx_company = (TextView)convertView.findViewById(R.id.tx_company);
                    associationViewHolder.rl_add = (RelativeLayout)convertView.findViewById(R.id.rl_add);
                    associationViewHolder.tx_info= (TextView)convertView.findViewById(R.id.tx_info);
                    associationViewHolder.tx_change = (TextView)convertView.findViewById(R.id.tx_change);
                    associationViewHolder.tx_description= (TextView)convertView.findViewById(R.id.tx_description);
					convertView.setTag(associationViewHolder);
				}
				break;
				case 6:
				{
					awardsViewHolder = new AwardsViewHolder();
                    convertView = mInflater.inflate(R.layout.item_curriculum_vitae_awards, null);
                    awardsViewHolder.rl_headtab = (RelativeLayout)convertView.findViewById(R.id.rl_headtab);
                    awardsViewHolder.tx_award = (TextView)convertView.findViewById(R.id.tx_award);
                    awardsViewHolder.rl_add = (RelativeLayout)convertView.findViewById(R.id.rl_add);
                    awardsViewHolder.tx_date= (TextView)convertView.findViewById(R.id.tx_date);
                    awardsViewHolder.tx_change = (TextView)convertView.findViewById(R.id.tx_change);
					convertView.setTag(awardsViewHolder);
				}
				break;
				case 7:
				{
					educationViewHolder = new EducationViewHolder();
					convertView = mInflater.inflate(R.layout.item_curriculum_vitae_no_education, null);
					educationViewHolder.rl_add = (RelativeLayout)convertView.findViewById(R.id.rl_add);
					convertView.setTag(educationViewHolder);
				}
				break;
				case 8:
				{
					internshipViewHolder = new InternshipViewHolder();
                    convertView = mInflater.inflate(R.layout.item_curriculum_vitae_no_internship, null);
					internshipViewHolder.rl_add = (RelativeLayout)convertView.findViewById(R.id.rl_add);
					convertView.setTag(internshipViewHolder);
				}
				break;
				case 9:
				{
					associationViewHolder = new AssociationViewHolder();
                    convertView = mInflater.inflate(R.layout.item_curriculum_vitae_no_association, null);
                    associationViewHolder.rl_add = (RelativeLayout)convertView.findViewById(R.id.rl_add);
					convertView.setTag(associationViewHolder);
				}
				break;
				case 10:
				{
					awardsViewHolder = new AwardsViewHolder();
                    convertView = mInflater.inflate(R.layout.item_curriculum_vitae_no_awards, null);
                    awardsViewHolder.rl_add = (RelativeLayout)convertView.findViewById(R.id.rl_add);
					convertView.setTag(awardsViewHolder);
				}
				break;
			}
		}

		switch(messageType)
		{
			case 0:
			{
				PersonUserInfoItem item = (PersonUserInfoItem)itemData;
				tabViewHolder = (TabViewHolder)convertView.getTag();
				tabViewHolder.tx_company.setText(item.name);// string 姓名
				tabViewHolder.tx_sex.setText(item.gender_text);// string 性别
				tabViewHolder.tx_birthday.setText(item.birthdate);// string 出生日期
				tabViewHolder.tx_native.setText(item.native_text);// string 籍贯
			}
			break;
			case 1:
			{
				PersonContactWayItem item = (PersonContactWayItem)itemData;
				contactinformationHolder = (ContactinformationHolder)convertView.getTag();
				contactinformationHolder.tx_email.setText(item.email);
				contactinformationHolder.tx_moblie.setText(item.mobile);
			}
			break;
			case 2:
			{
				PersonGrades item = (PersonGrades)itemData;
				gradeViewHolder = (GradeViewHolder)convertView.getTag();
				gradeViewHolder.tx_grade.setText(item.grade);
				gradeViewHolder.tx_computer.setText(item.computer);
				String language = "";
				for(int i = 0; i < item.languages.size(); i++)
				{
					String clanguage = item.languages.get(i).language;
					if(clanguage.length()>0)
					{
					language += clanguage;
					}
					String levle =  item.languages.get(i).level;
					if(levle.length()>0)
					{
					language += "("+levle+")"+" ";
					}
				}
				gradeViewHolder.tx_languages.setText(language);
			}
			break;
			case 3:
			{
				PersonEducation item = (PersonEducation)itemData;
				educationViewHolder = (EducationViewHolder)convertView.getTag();
				educationViewHolder.tx_school.setText(item.school_text);
				String info = item.date_start+"-"+item.date_end;
				info +="  "+item.major+"  "+item.graduate;		
				educationViewHolder.tx_educationinfo.setText(info);
				educationViewHolder.tx_description.setText(item.description);
				educationViewHolder.rl_headtab.setVisibility(View.GONE);
				if(itemData.isTop)
					educationViewHolder.rl_headtab.setVisibility(View.VISIBLE);
				educationViewHolder.rl_add.setTag(itemData);
				educationViewHolder.rl_add.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mCurriculumVitaeInterface.onAddPersonEducation();
					}
				});
				educationViewHolder.tx_change.setTag(itemData);
				educationViewHolder.tx_change.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						PersonEducation item = (PersonEducation)arg0.getTag();
						mCurriculumVitaeInterface.onChange(item);
					}
				});
			}
			break;
			case 4:
			{
				PersonInternshipItem item = (PersonInternshipItem)itemData;
				internshipViewHolder = (InternshipViewHolder)convertView.getTag();
				internshipViewHolder.tx_company.setText(item.company);
				String info = item.date_start+"-"+item.date_end+"  "+item.title;
				internshipViewHolder.tx_info.setText(info);
				internshipViewHolder.tx_description.setText(item.description);
				internshipViewHolder.rl_headtab.setVisibility(View.GONE);
				if(itemData.isTop)
					internshipViewHolder.rl_headtab.setVisibility(View.VISIBLE);
				internshipViewHolder.rl_add.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mCurriculumVitaeInterface.onAddInternships();
					}
				});
				internshipViewHolder.tx_change.setTag(item);
				internshipViewHolder.tx_change.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						PersonInternshipItem item = (PersonInternshipItem)arg0.getTag();
						mCurriculumVitaeInterface.onChangeInternships(item);
					}
				});
			}
			break;
			case 5:
			{
				PersonAssociationItem item = (PersonAssociationItem)itemData;
				associationViewHolder = (AssociationViewHolder)convertView.getTag();
				associationViewHolder.tx_company.setText(item.society);
				String info = item.date_start+"-"+item.date_end+"  "+item.title;
				associationViewHolder.tx_info.setText(info);
				associationViewHolder.tx_description.setText(item.description);
				
				associationViewHolder.rl_headtab.setVisibility(View.GONE);
				if(itemData.isTop)
					associationViewHolder.rl_headtab.setVisibility(View.VISIBLE);
				associationViewHolder.rl_add.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mCurriculumVitaeInterface.onAddAssociations();
					}
				});
				associationViewHolder.tx_change.setTag(item);
				associationViewHolder.tx_change.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						PersonAssociationItem item = (PersonAssociationItem)arg0.getTag();
						mCurriculumVitaeInterface.onChangeAssociations(item);
					}
				});
			}
			break;
			case 6:
			{
				PersonAwardsItem item = (PersonAwardsItem)itemData;
				awardsViewHolder  =(AwardsViewHolder)convertView.getTag();
				awardsViewHolder.tx_award.setText(item.award);
				awardsViewHolder.tx_date.setText(item.date);
				awardsViewHolder.rl_headtab.setVisibility(View.GONE);
				if(itemData.isTop)
					awardsViewHolder.rl_headtab.setVisibility(View.VISIBLE);
                awardsViewHolder.rl_add.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mCurriculumVitaeInterface.onAddAwards();
					}
				});
               awardsViewHolder.tx_change.setTag(item);
               awardsViewHolder.tx_change.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						PersonAwardsItem item = (PersonAwardsItem)arg0.getTag();
						mCurriculumVitaeInterface.onChange(item);
					}
				});
				//awardsViewHolder.tx_description.setText(item.description);
			}
			break;
			case 7:
			{
				educationViewHolder = (EducationViewHolder)convertView.getTag();
				educationViewHolder.rl_add.setTag(itemData);
				educationViewHolder.rl_add.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mCurriculumVitaeInterface.onAddPersonEducation();
					}
				});
				
			
			}
			break;
			case 8:
			{

				internshipViewHolder = (InternshipViewHolder)convertView.getTag();
				internshipViewHolder.rl_add.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mCurriculumVitaeInterface.onAddInternships();
					}
				});
				
			
			}
			break;
			case 9:
			{
				
				associationViewHolder = (AssociationViewHolder)convertView.getTag();
				
				associationViewHolder.rl_add.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mCurriculumVitaeInterface.onAddAssociations();
					}
				});
				
			}
			break;
			case 10:
			{
				
				awardsViewHolder  =(AwardsViewHolder)convertView.getTag();
				
                awardsViewHolder.rl_add.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mCurriculumVitaeInterface.onAddAwards();
					}
				});
             
			}
			break;
		}
		return convertView;

	}
	
	class TabViewHolder {
	   public EditText tx_company;
	   public TextView tx_sex;
	   public TextView tx_birthday;
	   public TextView tx_native;
	   
	 
	}
	
	class ContactinformationHolder {
		  public EditText tx_moblie;
		  public EditText tx_email;
		}
	
	class GradeViewHolder {
		  public RelativeLayout rl_languages;
		  public RelativeLayout rl_grade;
		  public RelativeLayout rl_computer;
		  public TextView tx_grade;
		  public TextView tx_languages;
		  public TextView tx_computer;
		}
	
	class EducationViewHolder {
		  public TextView tx_school;
		  public RelativeLayout rl_add;
		
		  public TextView tx_educationinfo;
		  public TextView tx_change;
		  public TextView tx_description;
		  public RelativeLayout rl_headtab;
		
		}
	
	class InternshipViewHolder {
		  public TextView tx_company;
		  public RelativeLayout rl_add;
		  public TextView tx_info;
		  public TextView tx_change;
		  public TextView tx_description;
		  public RelativeLayout rl_headtab;
		}
	
	class AssociationViewHolder {
		  public TextView tx_company;
		  public RelativeLayout rl_add;
		  public TextView tx_info;
		  public TextView tx_change;
		  public TextView tx_description;
		  public RelativeLayout rl_headtab;
		}
	class AwardsViewHolder {
		  public TextView tx_award;
		  public RelativeLayout rl_add;
		  public TextView tx_date;
		  public TextView tx_change;
		  public TextView tx_description;
		  public RelativeLayout rl_headtab;
		}
	

	
}
