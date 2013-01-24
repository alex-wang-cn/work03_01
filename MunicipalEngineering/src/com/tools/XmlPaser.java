package com.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.municipalengineering.entity.PersonBean;
import com.municipalengineering.entity.TendersBean;
import com.municipalengineering.entity.VDTBean;

import android.util.Log;

public class XmlPaser {

	public static final String Tag = "XmlPaser";

	public static String paserSingleTag(String xml, String tagName) {
		String result = "";
		try {
			XmlPullParser xmlParser = XmlPullParserFactory.newInstance()
					.newPullParser();
			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
			xmlParser.setInput(bais, "UTF-8");
			int eventType = xmlParser.getEventType();
			while ((eventType != XmlPullParser.END_DOCUMENT)) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (xmlParser.getName().equalsIgnoreCase(tagName)) {
						result = xmlParser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}

				eventType = xmlParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String paserTaskNumber(String reuslt) {
		Log.i(Tag, reuslt);
		return "2";
	}

	public static void paserTenderA(ArrayList<TendersBean> outData, String xml) {
		try {
			outData.clear();
			TendersBean bean = null;
			XmlPullParser xmlParser = XmlPullParserFactory.newInstance()
					.newPullParser();
			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
			xmlParser.setInput(bais, "UTF-8");
			int eventType = xmlParser.getEventType();
			while ((eventType != XmlPullParser.END_DOCUMENT)) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (xmlParser.getName().equalsIgnoreCase("Table")) {
						bean = new TendersBean();
					}
					if (bean != null) {
						if ("Id".equalsIgnoreCase(xmlParser.getName())) {
							bean.setId(xmlParser.nextText());
						} else if ("BId".equalsIgnoreCase(xmlParser.getName())) {
							bean.setBId(xmlParser.nextText());

						} else if ("BiaoduanName".equalsIgnoreCase(xmlParser
								.getName())) {
							bean.setName(xmlParser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if (xmlParser.getName().equalsIgnoreCase("Table")) {
						if (bean != null) {
							outData.add(bean);
							bean = null;
						}
					}
					break;
				}
				eventType = xmlParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void paserTendersData(TendersBean outBean, String xml) {
		try {
			TendersBean bean = null;
			XmlPullParser xmlParser = XmlPullParserFactory.newInstance()
					.newPullParser();
			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
			xmlParser.setInput(bais, "UTF-8");
			int eventType = xmlParser.getEventType();
			while ((eventType != XmlPullParser.END_DOCUMENT)) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("BiaoDuanName".equalsIgnoreCase(xmlParser.getName())) {
						outBean.setName(xmlParser.nextText());
					} else if ("Area".equalsIgnoreCase(xmlParser.getName())) {
						outBean.setArea(xmlParser.nextText());

					} else if ("BAddress".equalsIgnoreCase(xmlParser.getName())) {
						outBean.setAddress(xmlParser.nextText());
					} else if ("ProjectManager".equalsIgnoreCase(xmlParser
							.getName())) {
						outBean.setProjectManager(xmlParser.nextText());
					} else if ("ProjectPhone".equalsIgnoreCase(xmlParser
							.getName())) {
						outBean.setProjectManagerPhone(xmlParser.nextText());
					} else if ("Name2".equalsIgnoreCase(xmlParser.getName())) {
						outBean.setName2(xmlParser.nextText());
					} else if ("Phone2".equalsIgnoreCase(xmlParser.getName())) {
						outBean.setPhone2(xmlParser.nextText());
					} else if ("GfZt".equalsIgnoreCase(xmlParser.getName())) {
						outBean.setGfZt(xmlParser.nextText());
					} else if ("MonitorType".equalsIgnoreCase(xmlParser
							.getName())) {
						outBean.setMonitorType(xmlParser.nextText());
					} else if ("MonitorTypeGz".equalsIgnoreCase(xmlParser
							.getName())) {
						outBean.setMonitorTypeGz(xmlParser.nextText());
					} else if ("Skjzt".equalsIgnoreCase(xmlParser.getName())) {
						outBean.setSkjzt(xmlParser.nextText());
					} else if ("Spzt".equalsIgnoreCase(xmlParser.getName())) {
						outBean.setSpzt(xmlParser.nextText());
					}

					break;
				case XmlPullParser.END_TAG:
					break;
				}
				eventType = xmlParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void paserPersonData(String reuslt, PersonBean mBean) {
	}

	public static void parserCamera(String xml, List<TendersBean> outBean) {
		try {
			Log.i("wang", xml);
			TendersBean bean = null;
			XmlPullParser xmlParser = XmlPullParserFactory.newInstance()
					.newPullParser();
			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
			xmlParser.setInput(bais, "UTF-8");
			int eventType = xmlParser.getEventType();
			while ((eventType != XmlPullParser.END_DOCUMENT)) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("Table".equals(xmlParser.getName())) {
						bean = new TendersBean();
					} else if ("BiaoDuanName".equalsIgnoreCase(xmlParser
							.getName())) {
						bean.setName(xmlParser.nextText());
					} else if ("Camera".equalsIgnoreCase(xmlParser.getName())) {
						getCameras(bean, xmlParser.nextText());
					}

					break;
				case XmlPullParser.END_TAG:
					if ("Table".equals(xmlParser.getName())) {
						outBean.add(bean);
						bean = null;
					}
					break;
				}
				eventType = xmlParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getCameras(TendersBean bean, String text) {
		String[] cameras = text.split(",");

		for (int i = 0; i < cameras.length; i++) {
			String[] camera = cameras[i].split("@");
			VDTBean vdtBean = new VDTBean();
			vdtBean.setId(camera[0]);
			vdtBean.setInstallPlace(camera[1]);
			bean.getVdts().add(vdtBean);
		}
	}
}
