package com.mayvel.snDriver;

import com.mayvel.snDriver.subscribers.AlarmSubscriber;
import com.mayvel.snDriver.subscribers.ResultCallback;
import com.mayvel.snDriver.utils.HttpAlarmPost;

import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmService;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.io.IOException;

@NiagaraType

@NiagaraProperty(
        name = "alarmOut",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY | Flags.READONLY,
        facets = {
                @Facet(name = "BFacets.MULTI_LINE", value = "BBoolean.TRUE"),
        }
)
public class BSnAlarm extends BComponent {
    public String URL = null;
    public String PORT = null;
    AlarmSubscriber subscriber = null;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.BSnAlarm(1905527638)1.0$ @*/
/* Generated Tue May 20 10:01:17 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "alarmOut"

  /**
   * Slot for the {@code alarmOut} property.
   * @see #getAlarmOut
   * @see #setAlarmOut
   */
  public static final Property alarmOut = newProperty(Flags.SUMMARY | Flags.READONLY, "", BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code alarmOut} property.
   * @see #alarmOut
   */
  public String getAlarmOut() { return getString(alarmOut); }

  /**
   * Set the {@code alarmOut} property.
   * @see #alarmOut
   */
  public void setAlarmOut(String v) { setString(alarmOut, v, null); }

  //endregion Property "alarmOut"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnAlarm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
    public void started() throws Exception {
        super.started();

        try {
            ResultCallback resultCallback = new ResultCallback() {
                @Override
                public void onAlarmTriggered(String result) {
                    setAlarmOut(result);
                }
            };
            subscriber = new AlarmSubscriber(URL, PORT, resultCallback);

            BAlarmService alarmService = (BAlarmService) Sys.getService(BAlarmService.TYPE);
            BAlarmClass[] res = alarmService.getChildren(BAlarmClass.class);
            for (BAlarmClass c : res) {
                System.out.println("The class is " + c);
                subscriber.subscribe(c);
            }
           startTriggerStateThreads();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


  @Override
  public void stopped() throws Exception {
    super.stopped();
      try {
          HttpAlarmPost.stop();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  private void startTriggerStateThreads() {
    try {
      HttpAlarmPost.start();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
