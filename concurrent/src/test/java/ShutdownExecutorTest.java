/*
 * @(#)ShutdownExecutorTest.java         1.00, 2017/02/14 (14 February, 2017)
 *
 * This file is part of 'research-common' project.
 * Can not be copied and/or distributed without
 * the express permission of implementation provider.
 *
 * Copyright (C) 2017
 * All Rights Reserved.
 */

import lombok.Data;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ShutdownExecutorTest
 *
 * @author Roman Grygorovych
 * @version 1.00, 02/14/2017
 * @since 1.0
 */
public class ShutdownExecutorTest {

	ScheduledThreadPoolExecutor m_ses = new ScheduledThreadPoolExecutor(1);

	@Data
	class TaskLoader {

		AtomicInteger outstandingRowCount = new AtomicInteger(0);

		ExecutorService ses =
				new ThreadPoolExecutor(1, 1,
						0L, TimeUnit.MILLISECONDS,
						new LinkedBlockingQueue<Runnable>());

		void drain() throws InterruptedException {
			ses.submit(() -> {
				System.out.println("submit: m_outstandingRowCount = " + outstandingRowCount.get());
				while (outstandingRowCount.get() != 0) {
					System.out.println("sleep: m_outstandingRowCount = " + outstandingRowCount.get());
					Thread.sleep(outstandingRowCount.decrementAndGet());
				}
				return true;
			});

			while (outstandingRowCount.get() != 0) {
				System.out.println("yield: m_outstandingRowCount = " + outstandingRowCount.get());
				Thread.sleep(100L);
			}
		}

		void close() throws InterruptedException {
			ses.shutdown();
			ses.awaitTermination(365, TimeUnit.DAYS);
		}
	}

	@Test
	public void testShutdownWhileTasksInProgress() throws InterruptedException {
		final TaskLoader taskLoader = new TaskLoader();

		taskLoader.setOutstandingRowCount(new AtomicInteger(300));

		m_ses.scheduleAtFixedRate(() -> {
			try {
				taskLoader.drain();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 10L, 1L, TimeUnit.SECONDS);

		final long millis = 30_000L;
		System.out.println("sleep: " + millis);
		Thread.sleep(millis);
		System.out.println("sleep: done");

		taskLoader.close();
		m_ses.shutdown();
	}

	@Test/*(expected = RejectedExecutionException.class)*/
	public void testRejectedExecutionException() throws InterruptedException {
		final ExecutorService executorService = Executors.newFixedThreadPool(1);

		execute(executorService);

		executorService.shutdown();
		System.out.println("shutdown");

		execute(executorService);
	}

	private void execute(ExecutorService executorService) {
		executorService.submit(() -> {
			try {
				Thread.sleep(10000L);
				System.out.println("done");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, executorService);
	}
}
