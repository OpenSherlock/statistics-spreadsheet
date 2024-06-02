/**
 * 
 */
package org.topicquests.os.asr.statistics.spreadshet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 
 */
public class SpreadsheetEngine extends Thread {
	private Environment environment;
	private RedisClient redis;
	private final String REDIS_TOPIC;
	private boolean isRunning = true;
	private Object waitObject = new Object();
	private final long WAIT_TIME = 30000; // 30 seconds 

	/**
	 * 
	 */
	public SpreadsheetEngine(Environment env) {
		environment = env;
		REDIS_TOPIC = environment.getStringProperty("REDIS_TOPIC");
		redis = new RedisClient(environment);
	}
	
	public void shutDown() {
		synchronized(waitObject) {
			isRunning = false;
			waitObject.notify();
		}
	}
	public void run() {
		String json = null;
		while (isRunning) {
				json = redis.getNext(REDIS_TOPIC);
			System.out.println("JSON "+json);
			while (json == null) {
				synchronized(waitObject) {
					try {
						waitObject.wait(WAIT_TIME);
					} catch(Exception e) {}
					if (!isRunning)
						return;
				}
				json = redis.getNext(REDIS_TOPIC);
				System.out.println("JSOX "+json);
			}
		}
		performMagic(json);
	}
	void performMagic(String json) {
		JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
		//TODO
	}
}
