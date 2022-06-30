// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Log4j2LogManager.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import java.util.Objects;

import moe.kawayi.org.utopia.core.util.NotNull;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.apache.logging.log4j.core.LoggerContext;

/**
 * Log4j2的LogManager
 */
public class Log4j2LogManager extends InternalLoggerFactory implements LogManager {

    private final LoggerContext context;

    /**
     * 使用Log4j2的日志上下文初始化
     * @param context 上下文
     */
    public Log4j2LogManager(@NotNull LoggerContext context) {
        Objects.requireNonNull(context);
        this.context = context;
    }

    @Override
    @NotNull
    public Logger getLogger() {
        var trace = Thread.currentThread().getStackTrace()[2];
        return new Log4j2Logger(context.getLogger(trace.getClassName()));
    }

    @Override
    @NotNull
    public Logger getLogger(@NotNull String name) {
        return new Log4j2Logger(context.getLogger(name));
    }

    @Override
    @NotNull
    public Logger getLogger(@NotNull Class<?> clazz) {
        return new Log4j2Logger(context.getLogger(clazz));
    }

    @Override
    @NotNull
    protected InternalLogger newInstance(@NotNull String name) {
        Objects.requireNonNull(name);
        return (Log4j2Logger) getLogger(name);
    }
}
