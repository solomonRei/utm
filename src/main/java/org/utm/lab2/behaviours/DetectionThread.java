package org.utm.lab2.behaviours;

import org.utm.lab2.services.ChangeDetectorWithNoChangeLogs;
import org.utm.lab2.services.ChangeDetectorWithoutNoChangeLogs;
import org.utm.lab2.utils.DateUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DetectionThread {

    private final ChangeDetectorWithoutNoChangeLogs detector;

    private final ChangeDetectorWithNoChangeLogs detectorForConsole;

    private final Lock lock = new ReentrantLock();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public DetectionThread(String folderPath) {
        this.detector = new ChangeDetectorWithoutNoChangeLogs(folderPath);
        this.detectorForConsole = new ChangeDetectorWithNoChangeLogs(folderPath);
    }

    public void startScheduler() {
        scheduler.scheduleAtFixedRate(this::runDetection, 0, 5, TimeUnit.SECONDS);
    }

    private void runDetection() {
        lock.lock();
        try {
            if (!detector.checkIfFileLastModifiedMapIsEmpty()) {
                detector.detectChanges();
                var changeMessages = detector.getChangeMessages();
                if (!changeMessages.isEmpty()) {
                    System.out.println("----------------------------------------");
                    System.out.println("Changes detected at: " + DateUtils.formatTimestamp(detector.getSnapshotTime()));
                    changeMessages.forEach(System.out::println);
                    System.out.println("----------------------------------------");
                    detector.clearChangeMessages();
                    detector.commit();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void commitForConsole() {
        lock.lock();
        try {
            detectorForConsole.commit();
            detector.commit();

            System.out.println("Created Snapshot at: " + DateUtils.formatTimestamp(detectorForConsole.getSnapshotTime()));
        } finally {
            lock.unlock();
        }
    }

    public void infoForConsole(String fileName) {
        lock.lock();
        try {
            detectorForConsole.info(fileName);
        } finally {
            lock.unlock();
        }
    }

    public void statusForConsole() {
        lock.lock();
        try {
            detectorForConsole.status();
        } finally {
            lock.unlock();
        }
    }

    public void stop() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate in the specified time.");
                List<Runnable> droppedTasks = scheduler.shutdownNow();
                System.err.println("Executor was abruptly shut down. " + droppedTasks.size() + " tasks will not be executed.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}



