package com.aotu.data;

import java.util.ArrayList;
import java.util.List;

public class ProvinceCityItem {
	public String provinve;
	public int    code;
	public List<CityItem> mCityItemList = new ArrayList<CityItem>();
	public int    mType=0;
	public boolean mIsShowGridview = true;
}
