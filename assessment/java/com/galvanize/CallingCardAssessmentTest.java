package com.galvanize;

import com.galvanize.util.ClassProxy;
import com.galvanize.util.InstanceProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.galvanize.ProxyFactory.getCallingCardProxy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("CallingCard Test")
public class CallingCardAssessmentTest {

    ClassProxy CallingCardProxy;

    @BeforeEach
    public void setupObjects() {
        CallingCardProxy = getCallingCardProxy();
    }

    @ParameterizedTest(name = "{0} cents/minute: adding {1} dollars")
    @DisplayName("Adding / Removing Minutes")
    @MethodSource("minutesProvider")
    public void callingCardWorks(int centsPerMinute, int dollars, int expectedRemainingMinutes) {
        InstanceProxy card = CallingCardProxy.newInstance(centsPerMinute);
        card.invoke("addDollars", dollars);
        assertEquals(
                expectedRemainingMinutes,
                card.invoke("getRemainingMinutes"),
                String.format(
                        "Expected card to have %s remaining minutes after adding %s dollars @ %s cents/hour",
                        expectedRemainingMinutes,
                        dollars,
                        centsPerMinute)
        );

        card.invoke("useMinutes", expectedRemainingMinutes - 1);
        assertEquals(
                1,
                card.invoke("getRemainingMinutes"),
                String.format(
                        "Expected card to have 1 remaining minute after adding %s dollars and then using %s minutes",
                        dollars,
                        expectedRemainingMinutes - 1)
        );

        card.invoke("useMinutes", 2);
        assertEquals(
                0,
                card.invoke("getRemainingMinutes"),
                "Expected card to have 0 remaining minutes after calling useMinutes(2) with 1 minute left"
        );

    }

    static Stream<Arguments> minutesProvider() {
        return Stream.of(
                Arguments.of(10, 2, 20),
                Arguments.of(20, 2, 10),
                Arguments.of(9, 4, 44),
                Arguments.of(17, 7, 41),
                Arguments.of(39, 12, 30)
        );
    }


}
