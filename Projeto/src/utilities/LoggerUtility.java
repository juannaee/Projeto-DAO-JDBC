package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;

public class LoggerUtility {

	public static final Logger logger = LoggerFactory.getLogger(Main.class);

	private LoggerUtility() {

	}

	// Logs de informação
	public static void info(String message, Object... params) {
		if (logger.isInfoEnabled()) {
			logger.info(message, params);
		}

	}

	// Logs de error
	public static void error(String message, Object... params) {
		if (logger.isErrorEnabled()) {
			logger.error(message, params);
		}

	}

	// Logs de debug
	public static void debug(String message, Object... params) {
		if (logger.isDebugEnabled()) {
			logger.debug(message, params);
		}
	}

	// Logs de aviso
	public static void warn(String message, Object... params) {
		if (logger.isWarnEnabled()) {
			logger.warn(message, params);
		}
	}

}
