package com.mayvel.snDriver.Processor;


import com.mayvel.snDriver.Const.Consts;
import com.mayvel.snDriver.utils.Generic;
import com.mayvel.snDriver.utils.Scheduler;

import java.util.concurrent.TimeUnit;
public class HealthChecker {
    public static void scheduleHealthMonitor(){
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{");
        Generic.appendField(jsonString, "status", "ok");
        if (jsonString.length() > 1) {
            jsonString.setLength(jsonString.length() - 1);
        }
        jsonString.append("}");

        Runnable task = new Runnable() {
            @Override
            public void run() {
                WebSocketClient.clientSideWebsocketRealtime(Consts.host,jsonString.toString(),String.valueOf(Consts.port),Consts.alarm_sync_route);
                WebSocketClient.clientSideWebsocketRealtime(Consts.host,jsonString.toString(),String.valueOf(Consts.port),Consts.history_sync_route);
            }
        };
        Scheduler.schedule(task, 0, 1, TimeUnit.MINUTES);
    }
}
