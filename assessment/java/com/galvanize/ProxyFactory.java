package com.galvanize;

import com.galvanize.util.ClassProxy;

public class ProxyFactory {

    public static ClassProxy getCallingCardProxy() {
        return ClassProxy.classNamed("com.galvanize.CallingCard")
                .ensureConstructor(Integer.TYPE)
                .ensureMethod(m -> m
                        .isPublic()
                        .returns(Void.TYPE)
                        .named("addDollars")
                        .withParameters(Integer.TYPE)
                )
                .ensureMethod(m -> m
                        .isPublic()
                        .returns(Integer.TYPE)
                        .named("getRemainingMinutes")
                )
                .ensureMethod(m -> m
                        .isPublic()
                        .returns(Void.TYPE)
                        .named("useMinutes")
                        .withParameters(Integer.TYPE)
                );
    }

    public static ClassProxy getCellPhoneProxy(ClassProxy CallingCardProxy) {
        return ClassProxy.classNamed("com.galvanize.CellPhone")
                .ensureConstructor(CallingCardProxy)
                .ensureMethod(m -> m
                        .isPublic()
                        .returns(Boolean.TYPE)
                        .named("isTalking")
                );
    }


}
