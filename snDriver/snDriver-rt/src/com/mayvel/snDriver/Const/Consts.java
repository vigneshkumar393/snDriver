package com.mayvel.snDriver.Const;

public class Consts {
    public static int port = 5005;
    public static int alarmPort = 8005;
    public static int HistoryPort = 8006;
    public static String host = "localhost";
    public static String web_key_header = "sec-websocket-key";
    public static String default_pattern = "dd-MMM-yy hh:mm:ss a z";

    public static String alarm_sync_all_route = "/alarm_syncall";
    public static String alarm_sync_route = "/alarm_sync";
    public static String history_sync_all_route = "/history_syncall";
    public static String history_sync_route = "/history_sync";
    //public static String health_route = "/health";
}
