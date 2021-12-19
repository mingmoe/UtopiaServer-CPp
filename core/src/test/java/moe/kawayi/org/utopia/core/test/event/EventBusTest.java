//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The EventBusTest.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.test.event;

import moe.kawayi.org.utopia.core.event.EventBus;
import moe.kawayi.org.utopia.core.event.EventImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class EventBusTest {

    private boolean called = false;

    private final EventBus<EventImpl<Boolean>> eventBus = new EventBus<>();

    @Test
    public void eventBusTestCaller() throws java.lang.Throwable {
        // test
        var id = eventBus.register(event -> {
            called = (boolean)event.getParameter().orElseThrow();
            return null;
        });

        eventBus.fireEvent(new EventImpl<Boolean>(true));

        eventBus.unregister(id);

        eventBus.fireEvent(new EventImpl<Boolean>(false));

        Assertions.assertTrue(called);
    }

}