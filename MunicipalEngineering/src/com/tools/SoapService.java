package com.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Handler;
import android.util.Log;

import com.municipalengineering.activity.Login;
import com.municipalengineering.entity.TendersBean;
import com.municipalengineering.entity.UserBean;

public class SoapService {
	public final String NameSpace = "http://xyglpt.cdxinyong.com/";
	public final String Url = "http://xyglpt.cdxinyong.com/checkpic/Mobile.asmx";
	public final String MethodName = "FileUploadImage";
	// all
	public final static int SERVICE_COMPLETE = 1;
	public final static int ValidateUser = SERVICE_COMPLETE + 1;
	public final static int GetTaskNumber = ValidateUser + 1;
	public final static int GetTaskByUser = GetTaskNumber + 1;
	public final static int GetTenders = GetTaskByUser + 1;
	public final static int UpLoadImage = GetTenders + 1;
	public final static int UpLoadTenderData = UpLoadImage + 1;
	public final static int GetPersonData = UpLoadTenderData + 1;
	public final static int QuiryCamera = GetPersonData + 1;
	public final static int QuiryTenders = QuiryCamera + 1;
	public final static int SERVICE_ERRO = -1;
	public final static int TIMEOUT = 9 * 1000;
	private SoapObject mSoapObject;
	private String result = null;
	private HashMap<Integer, String> results = new HashMap<Integer, String>();
	private Handler mHandler;

	public SoapService(Handler handler) {
		mHandler = handler;
	}

	public void iamgeUpload(final String filePath, final int resultCode) {
		try {
			FileInputStream fis = new FileInputStream(filePath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) >= 0) {
				baos.write(buffer, 0, count);
			}
			String uploadBuffer = new String(Base64.encode(baos.toByteArray()));
			fis.close();
			mSoapObject = new SoapObject(NameSpace, MethodName);
			mSoapObject.addProperty("bytestr", uploadBuffer);
			connectionWebService(MethodName, resultCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void connectionWebService(final String methodName,
			final int resultCode) {
		new Thread() {
			public void run() {
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(mSoapObject);
				HttpTransportSE httpTranstation = new HttpTransportSE(Url);
				try {
					httpTranstation.call(NameSpace + methodName, envelope);
					result = envelope.getResponse().toString();
					mHandler.sendEmptyMessage(resultCode);
				} catch (Exception e) {
					e.printStackTrace();
					mHandler.sendEmptyMessage(SERVICE_ERRO);
				}
			};

		}.start();
	}

	public String getReuslt() {
		return result;
	}

	public void validateUser(final UserBean userBean, final int resultCode) {

		String Url = "http://xyglpt.cdxinyong.com/checkpic/Mobile.asmx/BLog?LogName="
				+ userBean.getUserName()
				+ "&Pwd="
				+ userBean.getPassward()
				+ "&Sys=1";
		doGet(Url, resultCode);
	}

	private void doGet(String url, final int resultCode) {
		Log.i("url", url);
		final HttpGet httpRequest = new HttpGet(
				url.replaceAll("\\s{1,}", "%20"));
		final HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT);
		new Thread() {
			public void run() {
				HttpResponse httpResponse = null;
				if (httpRequest.getURI().getHost() != null
						&& !httpRequest.getURI().getHost().equals("")) {
					try {
						httpResponse = new DefaultHttpClient(httpParameters)
								.execute(httpRequest);
					} catch (Exception e) {
						e.printStackTrace();
						mHandler.sendEmptyMessage(SERVICE_ERRO);
					}

					try {
						int i = httpResponse.getStatusLine().getStatusCode();
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							result = EntityUtils.toString(httpResponse
									.getEntity());
							mHandler.sendEmptyMessage(resultCode);
						}
					} catch (Exception e) {
						e.printStackTrace();
						mHandler.sendEmptyMessage(SERVICE_ERRO);
					}

				}
			};
		}.start();
	}

	public void getTaskNumber(int getTaskCode) {
		/*String url = "http://xyglpt.cdxinyong.com/checkpic/Mobile.asmx/GetTask?UserId="
				+ Login.getUserBean().getUserId() + "&Flag=0";
		doGet(url, getTaskCode);*/
	}

	public void getTaskByUser(int getTaskCode) {
		String url = "http://xyglpt.cdxinyong.com/checkpic/Mobile.asmx/GetTask?UserId="
				+ Login.getUserBean().getUserId() + "&Flag=1";
		doGet(url, getTaskCode);
	}

	public void getTenders(String string, int getTendersResult) {
		String url = "http://xyglpt.cdxinyong.com/checkpic/Mobile.asmx/GetBInfor?BId="
				+ string;
		doGet(url, getTendersResult);
	}

	public void upLoadImage(final String filePath, final String id,
			final int reusltCode) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(filePath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) >= 0) {
				baos.write(buffer, 0, count);
			}
			String uploadBuffer = new String(Base64.encode(baos.toByteArray()));
			String url = "http://xyglpt.cdxinyong.com/checkpic/Mobile.asmx/FileUploadImage?bytestr="
					+ uploadBuffer + "&Id=" + id + "&Seq=" + reusltCode;
			doGet(url, reusltCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upLoadTenderData(final TendersBean bean,
			final int uploadInspectionData) {
		String url = " http://xyglpt.cdxinyong.com/checkpic/Mobile.asmx/SaveTask?Id="
				+ bean.getId()
				+ "& Visitor="
				+ Login.getUserBean().getUserId()
				+ "&Xmlxr="
				+ bean.getTenderPersonName()
				+ "&Lxdh="
				+ bean.getTenderPersionPhone()
				+ "&Visit="
				+ bean.getComments()
				+ "&Problem=Ë¢¿¨»ú×´Ì¬£º"
				+ bean.getSkjzt()
				+ ",ÊÓÆÁ×´Ì¬:"
				+ bean.getSpzt();
	}

	public void getPersonData(String idcard, int requestCode) {

	}

	public void quiryCamera(String tenderName, int requestCode) {
		String url = "http://xyglpt.cdxinyong.com/checkpic/Mobile.asmx/GetBVList?PageSize=20&PageIndex=1&BName=";
		try {
			url = url + URLEncoder.encode("%", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		doGet(url, requestCode);
	}

	public void quiryTenders(String tenderNaem, int reqestCode) {

	}
}
