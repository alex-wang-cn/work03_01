package com.municipalengineering.myinterface;

import android.content.Context;
import android.view.View;

public interface IMyView{
   
	public  View getMyView();	
	public  IMyView reusme();
	public  IMyView onPause();
}
