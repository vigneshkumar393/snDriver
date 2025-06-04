package com.mayvel.snDriver;

import com.mayvel.snDriver.utils.Logger;
import com.tridium.json.JSONArray;
import com.tridium.json.JSONException;
import com.tridium.json.JSONObject;

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
import java.util.*;

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
@NiagaraProperty(
        name = "scheduleArray",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY
)
@NiagaraAction(
        name = "scheduleClearAll")
@NiagaraAction(name = "scheduleCreate")
public class BSnScheduler extends BComponent {
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.BSnScheduler(986045235)1.0$ @*/
/* Generated Wed Jun 04 12:03:36 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

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

  //region Property "scheduleArray"

  /**
   * Slot for the {@code scheduleArray} property.
   * @see #getScheduleArray
   * @see #setScheduleArray
   */
  public static final Property scheduleArray = newProperty(Flags.SUMMARY, "", null);

  /**
   * Get the {@code scheduleArray} property.
   * @see #scheduleArray
   */
  public String getScheduleArray() { return getString(scheduleArray); }

  /**
   * Set the {@code scheduleArray} property.
   * @see #scheduleArray
   */
  public void setScheduleArray(String v) { setString(scheduleArray, v, null); }

  //endregion Property "scheduleArray"

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
    String[] paths = getAllBooleanSchedulePaths();
    for (String path:
         paths) {
      try {
        // Use the full path to the schedule in the station
        String schedulePath = path;
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
  }

  public void doScheduleCreate() {
    if(getScheduleArray().isEmpty()){
      scheduleCreateBasedOnInput();
    }else{
      scheduleCreateBasedOnJson();
    }

  }

  public void scheduleCreateBasedOnInput(){
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

  public void scheduleCreateBasedOnJson() {
    new Thread(() -> {
      try {

        JSONArray jsonArray = convertStringToJsonArray(getScheduleArray());
        for (int a = 0; a < jsonArray.length(); a++) {
          JSONObject jsonObject = jsonArray.getJSONObject(a);
          String[] paths = getMatchingSchedulePaths(jsonObject);
          if(paths.length==0){
            continue;
          }
          String startTimeVal = getFromDate(jsonObject);
          String toTimeVal = getToDate(jsonObject);

          for (String path:paths) {
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

            Date startDate = sdf.parse(startTimeVal);
            Date endDate = sdf.parse(toTimeVal);

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

            String scheduleResultJson = String.format(
                    "{ \"fromTime\": \"%s\", \"toTime\": \"%s\", \"status\": \"%s\", \"id\": \"%s\" }",
                    startTimeVal,
                    toTimeVal,
                    "Success",
                    getId(jsonObject) // or any unique ID you want to use
            );
            setScheduleOut(scheduleResultJson);

            Logger.Log("✅ Scheduled on " + bWeekday + " from " + startTime + " to " + endTime);
          }
        }
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

  public String[] getMatchingSchedulePaths(JSONObject scheduleObjectJson) {
    List<String> matchedPaths = new ArrayList<>();

    try {
      // Get niagara_tag string and split into individual tags
      String niagaraTagStr = scheduleObjectJson.optString("niagara_tag", "");
      if (niagaraTagStr.isEmpty()) {
        Logger.Log("⚠️ No niagara_tag provided.");
        setScheduleOut("⚠️ No niagara_tag provided.");
        return new String[0];
      }

      // Build tag set for fast lookup
      Set<String> tags = new HashSet<>();
      for (String tag : niagaraTagStr.split(",")) {
        tags.add(tag.trim());
      }

      Logger.Log("🔍 Looking for tags: " + tags);

      // Traverse sibling components and collect matches
      BComponent container = (BComponent) this.getParent();
      for (BComponent comp : container.getChildComponents()) {
        if (comp instanceof BBooleanSchedule) {
          BBooleanSchedule scheduler = (BBooleanSchedule) comp;
          String name = scheduler.getName();
          String actualName = name.startsWith("BS_") ? name.substring(3) : name;
          if (tags.contains(actualName)) {
            Logger.Log("✅ Match found: " + tags + " => " + actualName);
            String path = scheduler.getSlotPath().toString();  // Assumes this method exists
            if (path != null && !path.isEmpty()) {
              matchedPaths.add("station:|"+path);
              Logger.Log("✅ Match found: " + actualName + " => " + path);
            }
          }
        }
      }

    } catch (Exception e) {
      Logger.Log("❌ Error parsing JSON or matching tags: " + e.getMessage());
      setScheduleOut("❌ Error parsing JSON or matching tags.");
      e.printStackTrace();
    }

    return matchedPaths.toArray(new String[0]);
  }

  public String getFromDate(JSONObject obj) {
    try {
      return obj.optString("booking_from", "");
    } catch (Exception e) {
      setScheduleOut("❌ Error parsing booking_from");

      e.printStackTrace();
      return "";
    }
  }

  public String getToDate(JSONObject obj) {
    try {
      return obj.optString("booking_to", "");
    } catch (Exception e) {
      setScheduleOut("❌ Error parsing booking_to");
      e.printStackTrace();
      return "";
    }
  }


  public String getId(JSONObject obj) {
    try {
      return obj.optString("sys_id", "");
    } catch (Exception e) {
      setScheduleOut("❌ Error parsing sys_id");
      e.printStackTrace();
      return "";
    }
  }

  public String[] getAllBooleanSchedulePaths() {
    List<String> allPaths = new ArrayList<>();

    try {
      BComponent container = (BComponent) this.getParent();

      for (BComponent comp : container.getChildComponents()) {
        if (comp instanceof BBooleanSchedule) {
          BBooleanSchedule schedule = (BBooleanSchedule) comp;

          // Get full slot path as string
          String path = schedule.getSlotPath().toString();

          if (path != null && !path.isEmpty()) {
            allPaths.add("station:|"+path);
            Logger.Log("✅ Found BooleanSchedule: " + path);
          }
        }
      }

    } catch (Exception e) {
      Logger.Log("❌ Error collecting BBooleanSchedule paths: " + e.getMessage());
      e.printStackTrace();
    }

    return allPaths.toArray(new String[0]);
  }

  public JSONArray convertStringToJsonArray(String jsonString) {
    try {
      return new JSONArray(jsonString);
    } catch (Exception e) {
      Logger.Log("❌ Failed to parse JSON Array: " + e.getMessage());
      return new JSONArray(); // safe fallback
    }
  }


}
