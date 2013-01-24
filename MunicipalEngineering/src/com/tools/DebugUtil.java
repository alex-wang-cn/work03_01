package com.tools;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class DebugUtil {
	static {
		final LogConfigurator logConfigurator = new LogConfigurator();
		logConfigurator.setFileName(Environment.getExternalStorageDirectory()
				+ File.separator + "myapp.log");
		logConfigurator.setRootLevel(Level.DEBUG);
		logConfigurator.setLevel("com.wang", Level.ERROR);
		logConfigurator.configure();
	}

	public static void log(String tag, String info) {
		Logger.getLogger(tag).info(info);

	}

	public static void log(String tag, String info, Throwable tr) {
		Logger.getLogger(tag).info(info, tr);

	}

}
