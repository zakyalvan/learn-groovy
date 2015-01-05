package com.innovez.learn;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * How to execute groovy script from java using standard or jsr way.
 * 
 * @author zakyalvan
 */
public class StandardExecuteGroovyScript {
	public static void main(String[] args) throws Exception {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
		
		/**
		 * Evaluate string directly.
		 */
		engine.eval("println 'Hello, World!'");
		
		/**
		 * Evaluate string from script file (hello.groovy)
		 */
		engine.eval(new InputStreamReader(new FileInputStream("src/com/innovez/learn/hello.groovy")));
	}
}
