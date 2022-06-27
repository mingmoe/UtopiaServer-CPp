//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The World.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import moe.kawayi.org.utopia.core.map.Position;
import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.Optional;

/**
 * 世界接口
 */
public interface World {

    /**
     * 获取方块
     *
     * @param position 方块位置
     * @return 获取到的方块。如果方块未加载或者超出范围，返回空的Optional。
     */
    @NotNull
    Optional<Block> getBlock(@NotNull Position position);

    /**
     * 获取世界id
     *
     * @return 世界id
     */
    long getWorldId();

    /**
     * 获取X轴最大值
     * @return 返回X轴最大值
     */
    int getMaxXSize();

    /**
     * 获取X轴最小值
     * @return 返回X轴最小值
     */
    int getMinXSize();

    /**
     * 获取Y轴最大值
     * @return 返回Y轴最大值
     */
    int getMaxYSize();

    /**
     * 获取Y轴最小值
     * @return 返回Y轴最小值
     */
    int getMinYSize();
}
