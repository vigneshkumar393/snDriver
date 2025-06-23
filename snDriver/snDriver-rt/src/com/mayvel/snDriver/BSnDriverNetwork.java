/*
 * Copyright 2025 Mayvel. All Rights Reserved.
 */

package com.mayvel.snDriver;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.schedule.BBooleanSchedule;
import javax.baja.sys.*;
import javax.baja.util.Lexicon;

import com.mayvel.snDriver.utils.CustomLicenseGenerator;
import com.tridium.json.JSONObject;
import com.tridium.ndriver.BNNetwork;
import com.tridium.ndriver.poll.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * BSnDriverNetwork models a network of devices
 *
 * @author Mayvel on 15 May 2025
 */
@NiagaraType
@NiagaraProperty(
  name = "pollScheduler",
  type = "BNPollScheduler",
  defaultValue = "new BNPollScheduler()",
  flags = Flags.HIDDEN
)
@NiagaraProperty(name = "license",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY
)
@NiagaraProperty(
        name = "licenseValid",
        type = "boolean",
        defaultValue = "false",
        flags = Flags.SUMMARY | Flags.READONLY
)
@NiagaraProperty(
        name = "validFrom",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY | Flags.READONLY
)
@NiagaraProperty(
        name = "validThru",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY| Flags.READONLY
)
@NiagaraProperty(
        name = "planName",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY| Flags.READONLY
)
@NiagaraProperty(
        name = "SnHttpClientLimit",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY| Flags.READONLY
)
@NiagaraProperty(
        name = "SnSchedulerLimit",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY| Flags.READONLY
)
@NiagaraProperty(
        name = "SnAlarmLimit",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY| Flags.READONLY
)
@NiagaraProperty(
        name = "SnHistoryLimit",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY| Flags.READONLY
)
@NiagaraProperty(
        name = "SnSwitchLimit",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY| Flags.READONLY
)
@NiagaraProperty(
        name = "out",
        type = "String",
        defaultValue = "",
        flags = Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
        name = "totalTriggerCount",
        type = "int",
        defaultValue = "BInteger.make(0)",
        flags = Flags.SUMMARY | Flags.READONLY
)
public class BSnDriverNetwork
  extends BNNetwork
{
  private Timer licenseTimer;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.BSnDriverNetwork(4153381786)1.0$ @*/
/* Generated Mon Jun 16 13:21:06 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "pollScheduler"

  /**
   * Slot for the {@code pollScheduler} property.
   * @see #getPollScheduler
   * @see #setPollScheduler
   */
  public static final Property pollScheduler = newProperty(Flags.HIDDEN, new BNPollScheduler(), null);

  /**
   * Get the {@code pollScheduler} property.
   * @see #pollScheduler
   */
  public BNPollScheduler getPollScheduler() { return (BNPollScheduler)get(pollScheduler); }

  /**
   * Set the {@code pollScheduler} property.
   * @see #pollScheduler
   */
  public void setPollScheduler(BNPollScheduler v) { set(pollScheduler, v, null); }

  //endregion Property "pollScheduler"

  //region Property "license"

  /**
   * Slot for the {@code license} property.
   * @see #getLicense
   * @see #setLicense
   */
  public static final Property license = newProperty(Flags.SUMMARY, "", null);

  /**
   * Get the {@code license} property.
   * @see #license
   */
  public String getLicense() { return getString(license); }

  /**
   * Set the {@code license} property.
   * @see #license
   */
  public void setLicense(String v) { setString(license, v, null); }

  //endregion Property "license"

  //region Property "licenseValid"

  /**
   * Slot for the {@code licenseValid} property.
   * @see #getLicenseValid
   * @see #setLicenseValid
   */
  public static final Property licenseValid = newProperty(Flags.SUMMARY | Flags.READONLY, false, null);

  /**
   * Get the {@code licenseValid} property.
   * @see #licenseValid
   */
  public boolean getLicenseValid() { return getBoolean(licenseValid); }

  /**
   * Set the {@code licenseValid} property.
   * @see #licenseValid
   */
  public void setLicenseValid(boolean v) { setBoolean(licenseValid, v, null); }

  //endregion Property "licenseValid"

  //region Property "validFrom"

  /**
   * Slot for the {@code validFrom} property.
   * @see #getValidFrom
   * @see #setValidFrom
   */
  public static final Property validFrom = newProperty(Flags.SUMMARY | Flags.READONLY, "", null);

  /**
   * Get the {@code validFrom} property.
   * @see #validFrom
   */
  public String getValidFrom() { return getString(validFrom); }

  /**
   * Set the {@code validFrom} property.
   * @see #validFrom
   */
  public void setValidFrom(String v) { setString(validFrom, v, null); }

  //endregion Property "validFrom"

  //region Property "validThru"

  /**
   * Slot for the {@code validThru} property.
   * @see #getValidThru
   * @see #setValidThru
   */
  public static final Property validThru = newProperty(Flags.SUMMARY | Flags.READONLY, "", null);

  /**
   * Get the {@code validThru} property.
   * @see #validThru
   */
  public String getValidThru() { return getString(validThru); }

  /**
   * Set the {@code validThru} property.
   * @see #validThru
   */
  public void setValidThru(String v) { setString(validThru, v, null); }

  //endregion Property "validThru"

  //region Property "planName"

  /**
   * Slot for the {@code planName} property.
   * @see #getPlanName
   * @see #setPlanName
   */
  public static final Property planName = newProperty(Flags.SUMMARY | Flags.READONLY, "", null);

  /**
   * Get the {@code planName} property.
   * @see #planName
   */
  public String getPlanName() { return getString(planName); }

  /**
   * Set the {@code planName} property.
   * @see #planName
   */
  public void setPlanName(String v) { setString(planName, v, null); }

  //endregion Property "planName"

  //region Property "SnHttpClientLimit"

  /**
   * Slot for the {@code SnHttpClientLimit} property.
   * @see #getSnHttpClientLimit
   * @see #setSnHttpClientLimit
   */
  public static final Property SnHttpClientLimit = newProperty(Flags.SUMMARY | Flags.READONLY, "", null);

  /**
   * Get the {@code SnHttpClientLimit} property.
   * @see #SnHttpClientLimit
   */
  public String getSnHttpClientLimit() { return getString(SnHttpClientLimit); }

  /**
   * Set the {@code SnHttpClientLimit} property.
   * @see #SnHttpClientLimit
   */
  public void setSnHttpClientLimit(String v) { setString(SnHttpClientLimit, v, null); }

  //endregion Property "SnHttpClientLimit"

  //region Property "SnSchedulerLimit"

  /**
   * Slot for the {@code SnSchedulerLimit} property.
   * @see #getSnSchedulerLimit
   * @see #setSnSchedulerLimit
   */
  public static final Property SnSchedulerLimit = newProperty(Flags.SUMMARY | Flags.READONLY, "", null);

  /**
   * Get the {@code SnSchedulerLimit} property.
   * @see #SnSchedulerLimit
   */
  public String getSnSchedulerLimit() { return getString(SnSchedulerLimit); }

  /**
   * Set the {@code SnSchedulerLimit} property.
   * @see #SnSchedulerLimit
   */
  public void setSnSchedulerLimit(String v) { setString(SnSchedulerLimit, v, null); }

  //endregion Property "SnSchedulerLimit"

  //region Property "SnAlarmLimit"

  /**
   * Slot for the {@code SnAlarmLimit} property.
   * @see #getSnAlarmLimit
   * @see #setSnAlarmLimit
   */
  public static final Property SnAlarmLimit = newProperty(Flags.SUMMARY | Flags.READONLY, "", null);

  /**
   * Get the {@code SnAlarmLimit} property.
   * @see #SnAlarmLimit
   */
  public String getSnAlarmLimit() { return getString(SnAlarmLimit); }

  /**
   * Set the {@code SnAlarmLimit} property.
   * @see #SnAlarmLimit
   */
  public void setSnAlarmLimit(String v) { setString(SnAlarmLimit, v, null); }

  //endregion Property "SnAlarmLimit"

  //region Property "SnHistoryLimit"

  /**
   * Slot for the {@code SnHistoryLimit} property.
   * @see #getSnHistoryLimit
   * @see #setSnHistoryLimit
   */
  public static final Property SnHistoryLimit = newProperty(Flags.SUMMARY | Flags.READONLY, "", null);

  /**
   * Get the {@code SnHistoryLimit} property.
   * @see #SnHistoryLimit
   */
  public String getSnHistoryLimit() { return getString(SnHistoryLimit); }

  /**
   * Set the {@code SnHistoryLimit} property.
   * @see #SnHistoryLimit
   */
  public void setSnHistoryLimit(String v) { setString(SnHistoryLimit, v, null); }

  //endregion Property "SnHistoryLimit"

  //region Property "SnSwitchLimit"

  /**
   * Slot for the {@code SnSwitchLimit} property.
   * @see #getSnSwitchLimit
   * @see #setSnSwitchLimit
   */
  public static final Property SnSwitchLimit = newProperty(Flags.SUMMARY | Flags.READONLY, "", null);

  /**
   * Get the {@code SnSwitchLimit} property.
   * @see #SnSwitchLimit
   */
  public String getSnSwitchLimit() { return getString(SnSwitchLimit); }

  /**
   * Set the {@code SnSwitchLimit} property.
   * @see #SnSwitchLimit
   */
  public void setSnSwitchLimit(String v) { setString(SnSwitchLimit, v, null); }

  //endregion Property "SnSwitchLimit"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.READONLY | Flags.SUMMARY, "", null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public String getOut() { return getString(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(String v) { setString(out, v, null); }

  //endregion Property "out"

  //region Property "totalTriggerCount"

  /**
   * Slot for the {@code totalTriggerCount} property.
   * @see #getTotalTriggerCount
   * @see #setTotalTriggerCount
   */
  public static final Property totalTriggerCount = newProperty(Flags.SUMMARY | Flags.READONLY, BInteger.make(0), null);

  /**
   * Get the {@code totalTriggerCount} property.
   * @see #totalTriggerCount
   */
  public int getTotalTriggerCount() { return getInt(totalTriggerCount); }

  /**
   * Set the {@code totalTriggerCount} property.
   * @see #totalTriggerCount
   */
  public void setTotalTriggerCount(int v) { setInt(totalTriggerCount, v, null); }

  //endregion Property "totalTriggerCount"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnDriverNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Specify name for network resources.
   */
  public String getNetworkName()
  {
    return "SnDriverNetwork";
  }

  /**
   * return device folder type
   */
  @Override
  public Type getDeviceFolderType()
  {
    return BSnDriverDeviceFolder.TYPE;
  }

  /**
   * return device type
   */
  @Override
  public Type getDeviceType()
  {
    return BSnDriverDevice.TYPE;
  }

  /* TODO - Add license check if needed
  @Override
  public final Feature getLicenseFeature()
  {
    return Sys.getLicenseManager().getFeature("?? vendor", "?? feature");
  }
  */

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning()) {
      return;
    }

    // Handle license change
    if (p == license) {
      validateLicenseKey();
    }
    // Any other property-specific logic here
  }

  private void validateLicenseKey() {
    String jsonString = CustomLicenseGenerator.validateLicense();

    try {
      JSONObject json = new JSONObject(jsonString);
      boolean isValid = json.optBoolean("result", false);
      String validFrom = json.optString("validFrom", "");
      String validThru = json.optString("validThru", "");
      String planName = json.optString("planName", "");

      // Correctly handle numeric values (convert int to string)
      String SnHttpClient = String.valueOf(json.optInt("SnHttpClient", 0));
      String SnScheduler = String.valueOf(json.optInt("SnScheduler", 0));
      // String SnAlarm = String.valueOf(json.optInt("SnAlarm", 0));
      // String SnHistory = String.valueOf(json.optInt("SnHistory", 0));

      int booleanScheduleTC = CustomLicenseGenerator.countComponentsOfType(Sys.getStation(), BBooleanSchedule.class);
      int snHttpClientTC = CustomLicenseGenerator.countComponentsOfType(Sys.getStation(), BSnHttpClient.class);

      // Set values
      setValidFrom(validFrom);
      setValidThru(validThru);
      setPlanName(planName);

      setSnHttpClientLimit(snHttpClientTC+"/"+SnHttpClient);
      setSnSchedulerLimit(booleanScheduleTC+"/"+SnScheduler);
      setSnAlarmLimit("Infinite");
      setSnHistoryLimit("Infinite");
      setSnSwitchLimit("Infinite");

      if (isValid) {
        String message = json.optString("message", "License is valid.");
        setLicenseValid(true);
        setOut(message);
      } else {
        setLicenseValid(false);
        setOut("Invalid License: " + json.optString("message", "Unknown error"));

        // Reset values on invalid license
        setPlanName("");
        setSnHttpClientLimit("0");
        setSnSchedulerLimit("0");
        setSnAlarmLimit("0");
        setSnHistoryLimit("0");
      }

    } catch (Exception e) {
      e.printStackTrace();
      setValidFrom("");
      setValidThru("");
      setLicenseValid(false);
      setOut("License check failed: " + e.getMessage());

      // Reset all values on error
      setPlanName("");
      setSnHttpClientLimit("0");
      setSnSchedulerLimit("0");
      setSnAlarmLimit("0");
      setSnHistoryLimit("0");
    }
    CustomLicenseGenerator.writeLSFile();
  }

////////////////////////////////////////////////////////////////
//Utilities
////////////////////////////////////////////////////////////////

  public String getLicenseKey() {
    return getLicense();
  }

  @Override
  public void started() throws Exception {
    super.started();
    licenseTimer = new Timer("LicenseValidationTimer", true); // true = daemon thread
    licenseTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        try {
          validateLicenseKey();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }, 0, 1000); // every 1 second
  }

  @Override
  public void stopped() throws Exception {
    if (licenseTimer != null) {
      licenseTimer.cancel();
      licenseTimer = null;
    }
    super.stopped();
  }



  public static Lexicon LEX = Lexicon.make(BSnDriverNetwork.class);
}
