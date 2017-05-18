package com.gdx.game.utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * {@link TimerTool} class hold reference to {@link ScheduledExecutorService} giving the ability to create delayed tasks
 */
public class TimerTool {
    private static final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(10);

    /**
     * Task that dies after given delay.
     * @param runnable {@link Runnable} to execute
     * @param delay delay before execution of runnable starts
     * @param unit {@link TimeUnit} specifying in which units delay is measuring
     * @return {@link ScheduledFuture} for being able to cancel scheduled task
     */
    @NotNull
    public static ScheduledFuture schedule(Runnable runnable, long delay, TimeUnit unit) {
        return scheduler.schedule(runnable, delay, unit);
    }

    /**
     * Task that runs in a loop until {@link java.util.concurrent.Future}.cancel(boolean) is called to kill it.
     * @param runnable {@link Runnable} to execute
     * @param delayBeforeStart delay before execution of runnable starts
     * @param period used for delaying each cycle of executing runnable
     * @param unit {@link TimeUnit} specifying in which units delay is measuring
     * @return {@link ScheduledFuture} for being able to cancel scheduled task
     */
    @NotNull
    public static ScheduledFuture scheduleAtFixedRate(Runnable runnable, long delayBeforeStart, long period, TimeUnit unit) {
        return scheduler.scheduleAtFixedRate(runnable, delayBeforeStart, period, unit);
    }
}
