package com.tools;

import java.util.ArrayList;


import com.municipalengineering.activity.R;
import com.municipalengineering.entity.IconBean;
import com.municipalengineering.fragment.CompanyInquiryFragment;
import com.municipalengineering.fragment.PersonnelInquiryFragment;
import com.municipalengineering.fragment.TendersInquiryFragment;
import com.municipalengineering.fragment.TendersInspectionFragment;
import com.municipalengineering.view.CompanyInquiryView;
import com.municipalengineering.view.HomeView;
import com.municipalengineering.view.PersonnelInquiryView;
import com.municipalengineering.view.TendersInspectionView;
import com.municipalengineering.view.TendersInquiryView;

public class InitIconBeans {
	private ArrayList<IconBean> mHandleIncos = new ArrayList<IconBean>();
	private ArrayList<IconBean> mHomeIcons = new ArrayList<IconBean>();
	private static InitIconBeans mInitIconBeans;

	private InitIconBeans() {
        //this should init from the native file  to allow the location modify
		mHandleIncos.add(new IconBean(HomeView.class.getName(),
				R.drawable.icon_home));
		mHandleIncos.add(new IconBean(TendersInspectionFragment.class.getName(),
				R.drawable.icon_bdxj));

		// ==================init the home icons==================
		mHomeIcons.add(new IconBean(TendersInspectionFragment.class.getName(),
				R.drawable.icon_bdxj));
		mHomeIcons.add(new IconBean(TendersInquiryFragment.class.getName(),
				R.drawable.icon_bdcx));
		mHomeIcons.add(new IconBean(CompanyInquiryFragment.class.getName(),
				R.drawable.icon_gscx));
		mHomeIcons.add(new IconBean(PersonnelInquiryFragment.class.getName(),
				R.drawable.icon_rycx));
	};

	public static InitIconBeans getInstance() {
		if (mInitIconBeans == null) {
			mInitIconBeans = new InitIconBeans();
		}
		return mInitIconBeans;
	}

	public ArrayList<IconBean> getHanderIcons() {
		return this.mHandleIncos;
	}

	public ArrayList<IconBean> getHomeIcons() {
		return this.mHomeIcons;
	}
}
