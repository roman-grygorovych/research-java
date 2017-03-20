/*
 * @(#)TestRejectedExecption.java         1.00, 2017/02/14 (14 February, 2017)
 *
 * This file is part of 'research-common' project.
 * Can not be copied and/or distributed without
 * the express permission of implementation provider.
 *
 * Copyright (C) 2017
 * All Rights Reserved.
 */
package org.rgrygorovych.research;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TestRejectedExecption
 *
 * @author Roman Grygorovych
 * @version 1.00, 02/14/2017
 * @since 1.0
 */
public class TestRejectedException implements Runnable {

	public void afterProperties() {
		Executors.newSingleThreadExecutor().submit(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
			}
		});
	}
	@Override
	public void run() {
		try {
			afterProperties();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void main(String[] args) throws Exception {
		ExecutorService execMain = Executors.newFixedThreadPool(2);
		TestRejectedException testcase = new TestRejectedException();
		execMain.submit(new Runnable(){

			@Override
			public void run() {
				while(true) {
					System.gc();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}});
		for (;;) {
			execMain.submit(testcase);
			Thread.yield();
		}
	}
}
