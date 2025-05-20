package com.mayvel.snDriver.subscribers;

import com.mayvel.snDriver.Const.Consts;
import com.mayvel.snDriver.Processor.WebSocketClient;
import com.mayvel.snDriver.utils.Generic;

import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmRecord;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.Subscriber;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AlarmSubscriber extends Subscriber {
    private String url;
    private String port;
    private BlockingQueue<BComponentEvent> eventQueue;
    private ResultCallback callback; // Interface for callback

    public AlarmSubscriber(String url, String port, ResultCallback callback) {
        this.url = url;
        this.port = port;
        this.callback = callback;
        this.eventQueue = new LinkedBlockingQueue<>();
        startEventProcessingThread();
    }

    private void startEventProcessingThread() {
        Thread eventProcessingThread = new Thread(() -> {
            while (true) {
                try {
                    BComponentEvent event = eventQueue.take();
                    parseEvent(event);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        eventProcessingThread.start();
    }

    @Override
    public void event(BComponentEvent event) {
        if (event.getId() == BComponentEvent.TOPIC_FIRED && event.getSourceComponent().getType().is(BAlarmClass.TYPE)) {
            try {
                eventQueue.put(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void parseEvent(BComponentEvent event) {
        try {
            System.out.println("Subscribed");
            BAlarmRecord record = (BAlarmRecord) event.getValue();
            String alarmTypeAck = record.getAckState().toString();
            if (alarmTypeAck.equals("Unacked")) {
                String recordString = Generic.buildRecordToString(record);

                // If callback is provided, trigger it
                if (callback != null) {
                    callback.onAlarmTriggered(recordString);  // Pass result to the callback
                }

               // WebSocketClient.clientSideWebsocketRealtime(Consts.host, recordString, String.valueOf(Consts.port), Consts.alarm_sync_route);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
