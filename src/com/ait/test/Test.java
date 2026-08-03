package com.ait.test;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Test {
	public static void main(String[] args) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");

		engine.put("engine", engine);

		try {
			eval(engine, "TestScriptEngine.js");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void eval(ScriptEngine engine, String name) throws Exception {
		/*
		 * This class is compiled into a jar file. The jar file contains few
		 * scripts under /resources URL.
		 */
		InputStream is = Test.class.getResourceAsStream(name);
		// current script file name for better error messages
		engine.put(ScriptEngine.NAME, name);
		// evaluate the script in the InputStream
		engine.eval(new InputStreamReader(is));
	}

}
