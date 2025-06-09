/*
 * Copyright 2025 Mayvel. All Rights Reserved.
 */

package com.mayvel.snDriver;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

import com.mayvel.snDriver.utils.CustomLicenseGenerator;
import com.tridium.json.JSONObject;
import com.tridium.ndriver.BNNetwork;
import com.tridium.ndriver.comm.*;
import com.tridium.ndriver.datatypes.*;
import com.tridium.ndriver.discover.*;
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
        name = "out",
        type = "String",
        defaultValue = "",
        flags = Flags.READONLY | Flags.SUMMARY
)
public class BSnDriverNetwork
  extends BNNetwork
{
  private Timer licenseTimer;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.BSnDriverNetwork(4077497335)1.0$ @*/
/* Generated Fri Jun 06 12:40:39 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

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
        if (isValid) {

          String message = json.optString("message", "License is valid.");

          setValidFrom(validFrom);
          setValidThru(validThru);
          setLicenseValid(true);
          setOut(message);
        } else {
          setValidFrom(validFrom);
          setValidThru(validThru);
          setLicenseValid(false);
          setOut("Invalid License: " + json.optString("message", "Unknown error"));
        }
      } catch (Exception e) {
        e.printStackTrace();
        setValidFrom("");
        setValidThru("");
        setLicenseValid(false);
        setOut("License check failed: " + e.getMessage());
      }
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
    }, 0, 60 * 1000); // every 10 seconds
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
