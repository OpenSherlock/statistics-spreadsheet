/**
 * 
 */
package org.topicquests.os.asr.statistics.spreadshet;

import org.topicquests.support.RootEnvironment;

/**
 * 
 */
public class Environment extends RootEnvironment {
	private SpreadsheetEngine engine;
	/**
	 */
	public Environment() {
		super("config-props.xml");
		engine = new SpreadsheetEngine(this);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				shutDown();
			}
		});
	}

	@Override
	public void shutDown() {
		engine.shutDown();
	}

}
