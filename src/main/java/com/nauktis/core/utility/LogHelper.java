package com.nauktis.core.utility;

import org.apache.logging.log4j.Level;

import com.google.common.base.Preconditions;

import cpw.mods.fml.common.FMLLog;

public class LogHelper {
	private final String mModId;

	public LogHelper(String pModId) {
		mModId = Preconditions.checkNotNull(pModId);
	}

	public void log(Level pLogLevel, String pFormat, Object... pData) {
		FMLLog.log(mModId, pLogLevel, pFormat, pData);
	}

	public void all(String pFormat, Object... pData) {
		log(Level.ALL, pFormat, pData);
	}

	public void trace(String pFormat, Object... pData) {
		log(Level.TRACE, pFormat, pData);
	}

	public void debug(String pFormat, Object... pData) {
		log(Level.DEBUG, pFormat, pData);
	}

	public void info(String pFormat, Object... pData) {
		log(Level.INFO, pFormat, pData);
	}

	public void warn(String pFormat, Object... pData) {
		log(Level.WARN, pFormat, pData);
	}

	public void error(String pFormat, Object... pData) {
		log(Level.ERROR, pFormat, pData);
	}

	public void fatal(String pFormat, Object... pData) {
		log(Level.FATAL, pFormat, pData);
	}

	public void off(String pFormat, Object... pData) {
		log(Level.OFF, pFormat, pData);
	}
}
