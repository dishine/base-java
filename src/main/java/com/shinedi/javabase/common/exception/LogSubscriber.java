package com.shinedi.javabase.common.exception;

/**
 * @author D-S
 * Created on 2019/12/9 11:11 上午
 */
public interface LogSubscriber {


    @Deprecated
    default void consume(String msg) {

    }

    default void onErrorMsg(String msg) {

    }

    default void onInfoMsg(String msg) {

    }

    default void onDebugMsg(String msg) {

    }

    default void onWarnMsg(String msg) {

    }

    default void onTraceMsg(String msg) {

    }
}
