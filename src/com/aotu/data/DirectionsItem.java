package com.aotu.data;

import java.util.ArrayList;
import java.util.List;

public class DirectionsItem {
	public String Name;//
	public int    Code;
	public List<DirectionGridItem> mGridItemList = new ArrayList<DirectionGridItem>();
	public int    mType=0;
	public boolean mIsShowGridview = true;
}
