//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The GameLogicLoop.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.logic;

import moe.kawayi.org.utopia.core.log.LogManagers;
import moe.kawayi.org.utopia.core.log.Logger;
import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.core.map.Position;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.server.map.World;
import moe.kawayi.org.utopia.server.map.WorldImpl;
import moe.kawayi.org.utopia.server.map.WorldInfo;
import moe.kawayi.org.utopia.server.net.NetMain;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 游戏逻辑循环
 */
public final class GameLogicLoop {

    private GameLogicLoop() {
    }

    /**
     * 日志器
     */
    private static final Logger LOGGER = LogManagers.getLogger(GameLogicLoop.class);

    /**
     * 是否运行
     */
    private static final AtomicBoolean IS_RUNNING = new AtomicBoolean(false);

    /**
     * 游戏循环
     */
    public static void mainLoop() {
        // 防止二次进入
        if (IS_RUNNING.getAndSet(true))
            return;

        try {
            // update
            World world = new WorldImpl(0, new FlatPosition(2, 2));

            int xMax = world.getMaxXSize();
            int xMin = world.getMinXSize();

            int yMax = world.getMaxYSize();
            int yMin = world.getMinYSize();

            LOGGER.info("逻辑线程启动");

            // 网络服务器关闭，loop关闭
            while (IS_RUNNING.get() && NetMain.isRun()) {
                // 计算时间
                long startTime = System.nanoTime();

                for (int x = xMin; x <= xMax; x++) {
                    for (int y = yMin; y <= yMax; y++) {

                        var block = world.getBlock(new Position(x, y, WorldInfo.GROUND_Z));

                        var entities = block.orElseThrow().getEntities();

                        for (var entity : entities) {
                            if (entity.needUpdate()) {
                                entity.update();
                            }
                        }
                    }
                }

                while ((System.nanoTime() - startTime) < 50000000) {
                    // 忙等待
                    Thread.sleep(1);
                }
            }

        } catch (@NotNull Throwable ex) {
            LOGGER.error("logic thread crash", ex);
            IS_RUNNING.set(false);
        } finally {
            shutdown();
            NetMain.shutdown();
            LOGGER.info("logic thread exit");
        }
    }


    /**
     * 获取逻辑线程运行状态
     *
     * @return 如果返回true，则逻辑线程正在运行
     */
    public static boolean isRun() {
        return IS_RUNNING.get();
    }

    /**
     * 关闭服务器
     */
    public static void shutdown() {
        IS_RUNNING.set(false);
    }

}
