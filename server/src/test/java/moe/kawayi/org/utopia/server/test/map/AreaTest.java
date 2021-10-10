//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The AreaTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.test.map;

import moe.kawayi.org.utopia.server.map.Area;
import moe.kawayi.org.utopia.server.map.AreaImpl;
import moe.kawayi.org.utopia.server.map.Position;
import moe.kawayi.org.utopia.server.map.WorldInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AreaTest {

    private final Area area = new AreaImpl();

    @Test
    public void areaAccessTest() {
        ArrayList<Object> objs = new ArrayList<>();

        for (int x = 0; x != WorldInfo.BLOCK_FLOOR_X_SIZE; x++)
            for (int y = 0; y != WorldInfo.BLOCK_FLOOR_Y_SIZE; y++) {
                var block = area.getBlock(new Position(x, y, WorldInfo.GROUND_Z));

                // 确保存在
                Assertions.assertTrue(block.isPresent());

                // 并且不重复
                Assertions.assertEquals(objs.indexOf(block.get()), -1);

                objs.add(block.get());
            }
    }

    @Test
    public void areaAccessWrongTest() {
        var result1 = area.getBlock(
                new Position(WorldInfo.BLOCK_FLOOR_X_SIZE, WorldInfo.BLOCK_FLOOR_Y_SIZE, WorldInfo.GROUND_Z));

        var result2 = area.getBlock(
                new Position(-1, -1, WorldInfo.GROUND_Z));

        Assertions.assertFalse(result1.isPresent());
        Assertions.assertFalse(result2.isPresent());
    }

    @Test
    public void areaNullPointerTest() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    area.getBlock(null);
                }
        );
    }

}
