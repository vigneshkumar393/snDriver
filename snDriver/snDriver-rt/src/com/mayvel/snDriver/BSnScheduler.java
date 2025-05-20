package com.mayvel.snDriver;

import com.mayvel.snDriver.utils.Logger;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.schedule.BBooleanSchedule;
import javax.baja.schedule.BDaySchedule;
import javax.baja.schedule.BTimeSchedule;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.sys.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@NiagaraType
// Schedule properties
@NiagaraProperty(
        name = "schedulePath",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY
)
@NiagaraProperty(
        name = "scheduleStartTime",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY
)
@NiagaraProperty(
        name = "scheduleEndTime",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY
)
@NiagaraProperty(
        name = "scheduleValueToSet",
        type = "boolean",
        defaultValue = "true",
        flags = Flags.SUMMARY
)
@NiagaraProperty(
        name = "scheduleOut",
        type = "String",
        defaultValue = "",
        flags = Flags.READONLY | Flags.SUMMARY
)
@NiagaraAction(
        name = "scheduleClearAll")
@NiagaraAction(name = "scheduleCreate")
public class BSnScheduler extends BComponent {
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.BSnScheduler(297064012)1.0$ @*/
/* Generated Tue May 20 10:01:18 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "schedulePath"

  /**
   * Slot for the {@code schedulePath} property.
   *  Schedule properties
   * @see #getSchedulePath
   * @see #setSchedulePath
   */
  public static final Property schedulePath = newProperty(Flags.SUMMARY, "", null);

  /**
   * Get the {@code schedulePath} property.
   *  Schedule properties
   * @see #schedulePath
   */
  public String getSchedulePath() { return getString(schedulePath); }

  /**
   * Set the {@code schedulePath} property.
   *  Schedule properties
   * @see #schedulePath
   */
  public void setSchedulePath(String v) { setString(schedulePath, v, null); }

  //endregion Property "schedulePath"

  //region Property "scheduleStartTime"

  /**
   * Slot for the {@code scheduleStartTime} property.
   * @see #getScheduleStartTime
   * @see #setScheduleStartTime
   */
  public static final Property scheduleStartTime = newProperty(Flags.SUMMARY, "", null);

  /**
   * Get the {@code scheduleStartTime} property.
   * @see #scheduleStartTime
   */
  public String getScheduleStartTime() { return getString(scheduleStartTime); }

  /**
   * Set the {@code scheduleStartTime} property.
   * @see #scheduleStartTime
   */
  public void setScheduleStartTime(String v) { setString(scheduleStartTime, v, null); }

  //endregion Property "scheduleStartTime"

  //region Property "scheduleEndTime"

  /**
   * Slot for the {@code scheduleEndTime} property.
   * @see #getScheduleEndTime
   * @see #setScheduleEndTime
   */
  public static final Property scheduleEndTime = newProperty(Flags.SUMMARY, "", null);

  /**
   * Get the {@code scheduleEndTime} property.
   * @see #scheduleEndTime
   */
  public String getScheduleEndTime() { return getString(scheduleEndTime); }

  /**
   * Set the {@code scheduleEndTime} property.
   * @see #scheduleEndTime
   */
  public void setScheduleEndTime(String v) { setString(scheduleEndTime, v, null); }

  //endregion Property "scheduleEndTime"

  //region Property "scheduleValueToSet"

  /**
   * Slot for the {@code scheduleValueToSet} property.
   * @see #getScheduleValueToSet
   * @see #setScheduleValueToSet
   */
  public static final Property scheduleValueToSet = newProperty(Flags.SUMMARY, true, null);

  /**
   * Get the {@code scheduleValueToSet} property.
   * @see #scheduleValueToSet
   */
  public boolean getScheduleValueToSet() { return getBoolean(scheduleValueToSet); }

  /**
   * Set the {@code scheduleValueToSet} property.
   * @see #scheduleValueToSet
   */
  public void setScheduleValueToSet(boolean v) { setBoolean(scheduleValueToSet, v, null); }

  //endregion Property "scheduleValueToSet"

  //region Property "scheduleOut"

  /**
   * Slot for the {@code scheduleOut} property.
   * @see #getScheduleOut
   * @see #setScheduleOut
   */
  public static final Property scheduleOut = newProperty(Flags.READONLY | Flags.SUMMARY, "", null);

  /**
   * Get the {@code scheduleOut} property.
   * @see #scheduleOut
   */
  public String getScheduleOut() { return getString(scheduleOut); }

  /**
   * Set the {@code scheduleOut} property.
   * @see #scheduleOut
   */
  public void setScheduleOut(String v) { setString(scheduleOut, v, null); }

  //endregion Property "scheduleOut"

  //region Action "scheduleClearAll"

  /**
   * Slot for the {@code scheduleClearAll} action.
   * @see #scheduleClearAll()
   */
  public static final Action scheduleClearAll = newAction(0, null);

  /**
   * Invoke the {@code scheduleClearAll} action.
   * @see #scheduleClearAll
   */
  public void scheduleClearAll() { invoke(scheduleClearAll, null, null); }

  //endregion Action "scheduleClearAll"

  //region Action "scheduleCreate"

  /**
   * Slot for the {@code scheduleCreate} action.
   * @see #scheduleCreate()
   */
  public static final Action scheduleCreate = newAction(0, null);

  /**
   * Invoke the {@code scheduleCreate} action.
   * @see #scheduleCreate
   */
  public void scheduleCreate() { invoke(scheduleCreate, null, null); }

  //endregion Action "scheduleCreate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnScheduler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  // Schdule methods
  public void doScheduleClearAll() {
    Logger.Log("BSNGroup schedule action triggered");

    try {
      // Use the full path to the schedule in the station
      String schedulePath = getSchedulePath();
      BOrd remoteOrd = BOrd.make(schedulePath);
      BObject obj = remoteOrd.resolve().get();
      if (obj instanceof BBooleanSchedule) {
        BBooleanSchedule schedule = (BBooleanSchedule) obj;
        // Clear the schedule
        schedule.clear();
        setScheduleOut("Successfully cleared schedule at path: " + schedulePath);
      } else {
        setScheduleOut("Component at path is not a BBooleanSchedule.");
      }
    } catch (Exception e) {
      setScheduleOut("Failed to clear schedule: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void doScheduleCreate() {
    new Thread(() -> {
      try {
        String path = getSchedulePath();
        BOrd ord = BOrd.make(path);
        BObject obj = ord.resolve().get();

        if (!(obj instanceof BBooleanSchedule)) {
          setScheduleOut("❌ Target is not a BBooleanSchedule: " + path);
          return;
        }

        BBooleanSchedule schedule = (BBooleanSchedule) obj;

        // Parse the input start and end times
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh:mm:ss a", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date startDate = sdf.parse(getScheduleStartTime());
        Date endDate = sdf.parse(getScheduleEndTime());

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        // Create BTime for start and end times
        BTime startTime = BTime.make(
                startCal.get(Calendar.HOUR_OF_DAY),
                startCal.get(Calendar.MINUTE),
                startCal.get(Calendar.SECOND)
        );

        BTime endTime = BTime.make(
                endCal.get(Calendar.HOUR_OF_DAY),
                endCal.get(Calendar.MINUTE),
                endCal.get(Calendar.SECOND)
        );

        // Get day of the week (1 = Sunday, 2 = Monday, ..., 7 = Saturday)
        int dayOfWeek = startCal.get(Calendar.DAY_OF_WEEK);
        BWeekday bWeekday = null;

        switch (dayOfWeek) {
          case Calendar.SUNDAY:
            bWeekday = BWeekday.sunday;
            break;
          case Calendar.MONDAY:
            bWeekday = BWeekday.monday;
            break;
          case Calendar.TUESDAY:
            bWeekday = BWeekday.tuesday;
            break;
          case Calendar.WEDNESDAY:
            bWeekday = BWeekday.wednesday;
            break;
          case Calendar.THURSDAY:
            bWeekday = BWeekday.thursday;
            break;
          case Calendar.FRIDAY:
            bWeekday = BWeekday.friday;
            break;
          case Calendar.SATURDAY:
            bWeekday = BWeekday.saturday;
            break;
        }

        if (bWeekday == null) {
          setScheduleOut("❌ Could not determine day of week.");
          return;
        }

        // Create status value
        BStatusBoolean statusValue = new BStatusBoolean(getScheduleValueToSet(), BStatus.ok);

        // Add time range to correct weekday
        BDaySchedule daySchedule = schedule.get(bWeekday);

// Get the schedules sorted in order
        BTimeSchedule[] schedules = daySchedule.getTimesInOrder();

        for (int i = 0; i < schedules.length; i++) {
          BTimeSchedule bTimeSchedule = schedules[i];

          // Get the start and end times of the current schedule
          BTime stTime = bTimeSchedule.getStart();
          BTime edTime = bTimeSchedule.getFinish();

          // Compare the start and end times with the input values
          if (stTime.equals(startTime) && edTime.equals(endTime)) {
            // Remove the schedule if it matches the given time range
            daySchedule.remove(bTimeSchedule);
            break; // Exit the loop after the schedule is removed
          }
        }

        daySchedule.add(startTime, endTime, statusValue);

        setScheduleOut("✅ Schedule set on " + bWeekday + " from " + getScheduleStartTime() + " to " + getScheduleEndTime());
        Logger.Log("✅ Scheduled on " + bWeekday + " from " + startTime + " to " + endTime);

      } catch (ParseException pe) {
        setScheduleOut("❌ Invalid date format. Use format: yyyy-MM-dd HH:mm:ss");
        Logger.Log("❌ Date parse error: " + pe.getMessage());
        pe.printStackTrace();
      } catch (Exception e) {
        setScheduleOut("❌ Failed to create schedule: " + e.getMessage());
        Logger.Log("❌ Failed to create schedule: " + e.getMessage());
        e.printStackTrace();
      }
    }, "ScheduleStateThread").start();
  }

  @Override
  public void stopped() throws Exception {
    super.stopped();
  }
}
