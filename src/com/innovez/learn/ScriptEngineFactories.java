package com.innovez.learn;

import java.util.List;
import java.util.logging.Logger;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 * Just a simple class for checking which scripting engine or language available
 * on jvm. Please note, each groovy appearance installation on classpath
 * autoamtically adding groovy scripting engine to JVM.
 * 
 * @author zakyalvan
 */
public class ScriptEngineFactories {
	private static final Logger LOGGER = Logger.getLogger(ScriptEngineFactories.class.getSimpleName());
	
	public static void main(String[] args) {
		List<ScriptEngineFactory> factories = new ScriptEngineManager().getEngineFactories();
		for(ScriptEngineFactory factory : factories) {
			LOGGER.info("================================================================");
			LOGGER.info("Engine name 		: " + factory.getEngineName());
			LOGGER.info("Engine version		: " + factory.getEngineVersion());
			LOGGER.info("Language name		: " + factory.getLanguageName());
			LOGGER.info("Language version	: " + factory.getLanguageVersion());
			LOGGER.info("================================================================");
		}
	}
}
