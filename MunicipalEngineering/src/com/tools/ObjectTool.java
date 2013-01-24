package com.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

public class ObjectTool {

	/*
	 *  this object must is a object of Serializable
	 */
	public static String ObjectToString(Object object) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream obj = null;
		try {
			obj = new ObjectOutputStream(baos);
			obj.writeObject(object);
			return new String(Base64.encodeBase64(baos.toByteArray()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object StringToObject(String string) {
		byte[] bsByte = Base64.decodeBase64(string.getBytes());
		ByteArrayInputStream bais = new ByteArrayInputStream(bsByte);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
