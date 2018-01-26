package com.galvanize;

import com.galvanize.util.ClassProxy;
import com.galvanize.util.InstanceProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.galvanize.ProxyFactory.getCallingCardProxy;
import static com.galvanize.ProxyFactory.getCellPhoneProxy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("CellPhone Test")
public class CellPhonesAssessmentTest {

    ClassProxy CallingCardProxy;
    ClassProxy CellPhoneProxy;

    @BeforeEach
    public void setupObjects() {
        CallingCardProxy = getCallingCardProxy();
        CellPhoneProxy = getCellPhoneProxy(CallingCardProxy);
    }

    @DisplayName("Making Phone Calls")
    @Test
    public void phoneCallsWork() {
        InstanceProxy card = CallingCardProxy.newInstance(25);
        card.invoke("addDollars", 1); // adds 4 minutes

        InstanceProxy phone = CellPhoneProxy.newInstance(card);

        assertEquals(
                false,
                phone.invoke("isTalking"),
                "`isTalking()` should return false when a call has not yet been made"
        );

        CellPhoneProxy.ensureMethod(m -> m
                .isPublic()
                .returns(Void.TYPE)
                .named("call")
                .withParameters(String.class)
        );

        phone.invoke("call", "555-1111");

        assertEquals(
                true,
                phone.invoke("isTalking"),
                "`isTalking()` should return true after isTalking has been called"
        );

        CellPhoneProxy.ensureMethod(m -> m
                .isPublic()
                .returns(Void.TYPE)
                .named("tick")
        );

        phone.invoke("tick"); // 3 minutes left
        phone.invoke("tick"); // 2 minutes left

        CellPhoneProxy.ensureMethod(m -> m
                .isPublic()
                .returns(String.class)
                .named("getHistory")
        );

        assertEquals(
                "",
                phone.invoke("getHistory"),
                "`getHistory()` should return an empty string until the first call has ended"
        );


        CellPhoneProxy.ensureMethod(m -> m
                .isPublic()
                .returns(Void.TYPE)
                .named("endCall")
        );

        phone.invoke("endCall");

        assertEquals(
                false,
                phone.invoke("isTalking"),
                "`isTalking()` should return false after endCall was called"
        );

        assertEquals(
                "555-1111 (2 minutes)",
                phone.invoke("getHistory"),
                "`getHistory()` should return \"555-1111 (2 minutes)\" after calling `startCall(\"555-1111\"); tick(); tick(); endCall();`"
        );

        phone.invoke("call", "555-2222");
        phone.invoke("tick");
        phone.invoke("endCall");

        assertEquals(
                "555-1111 (2 minutes), 555-2222 (1 minute)",
                phone.invoke("getHistory"),
                "`getHistory()` should return a comma-delimited list of calls, with correct pluralization"
        );

        assertEquals(
                1,
                card.invoke("getRemainingMinutes"),
                "Making calls (with `call/endCall`) should reduce the number of minutes on the card"
        );

        phone.invoke("call", "555-3333");
        phone.invoke("tick");

        assertEquals(
                false,
                phone.invoke("isTalking"),
                "Calling `tick` on the last minute should end the call, setting isTalking to false"
        );

        assertEquals(
                "555-1111 (2 minutes), 555-2222 (1 minute), 555-3333 (cut off at 1 minute)",
                phone.invoke("getHistory"),
                "Calling `tick` on the last minute should end the call, setting isTalking to false"
        );

        phone.invoke("tick");
        assertEquals(
                "555-1111 (2 minutes), 555-2222 (1 minute), 555-3333 (cut off at 1 minute)",
                phone.invoke("getHistory"),
                "Calling `tick` should do nothing if a call is not in progress"
        );

    }

}
