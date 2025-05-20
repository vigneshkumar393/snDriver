/*
 * Copyright 2025 Mayvel. All Rights Reserved.
 */

package com.mayvel.snDriver.point;

import javax.baja.driver.point.BReadWriteMode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.driver.util.DrUtil;
import com.tridium.ndriver.point.BNProxyExt;

import com.mayvel.snDriver.*;

/**
 * BSnDriverProxyExt
 *
 * @author Mayvel on 15 May 2025
 */
@NiagaraType
/*
Override ProxyExt default status to clear stale state if needed
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.READONLY | Flags.TRANSIENT,
  override = true
)
*/
public class BSnDriverProxyExt
  extends BNProxyExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.point.BSnDriverProxyExt(2979906276)1.0$ @*/
/* Generated Thu May 15 11:10:33 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnDriverProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the network cast to a BSnDriverNetwork.
   */
  public final BSnDriverNetwork getSnDriverNetwork()
  {
    return (BSnDriverNetwork) getNetwork();
  }

  /**
   * Get the device cast to a BSnDriverDevice.
   */
  public final BSnDriverDevice getBSnDriverDevice()
  {
    return (BSnDriverDevice) DrUtil.getParent(this, BSnDriverDevice.TYPE);
  }

  /**
   * Get the point device ext cast to a BSnDriverPointDeviceExt.
   */
  public final BSnDriverPointDeviceExt getSnDriverPointDeviceExt()
  {
    return (BSnDriverPointDeviceExt) getDeviceExt();
  }

////////////////////////////////////////////////////////////////
// ProxyExt
////////////////////////////////////////////////////////////////
  public void readSubscribed(Context cx)
    throws Exception
  {
    // TODO
  }

  public void readUnsubscribed(Context cx)
    throws Exception
  {
     // TODO
  }

  public boolean write(Context cx)
    throws Exception
  {
    // TODO
    return false;
  }

  /**
   * Return the device type.
   */
  public Type getDeviceExtType()
  {
    return BSnDriverPointDeviceExt.TYPE;
  }

  /**
   * Return the read/write mode of this proxy.
   */
  public BReadWriteMode getMode()
  {
    // TODO
    return BReadWriteMode.readonly;
  }

  public boolean isBoolean()
  {
    return getParentPoint().getOutStatusValue() instanceof BStatusBoolean;
  }

  public boolean isNumeric()
  {
    return getParentPoint().getOutStatusValue() instanceof BStatusNumeric;
  }

  public boolean isString()
  {
    return getParentPoint().getOutStatusValue() instanceof BStatusString;
  }

  public boolean isEnum()
  {
    return getParentPoint().getOutStatusValue() instanceof BStatusEnum;
  }
}
