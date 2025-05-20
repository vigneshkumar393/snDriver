/*
 * Copyright 2025 Mayvel. All Rights Reserved.
 */

package com.mayvel.snDriver.point;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ndriver.point.BNPointFolder;

import com.mayvel.snDriver.*;

/**
 * BSnDriverPointFolder
 *
 * @author   Mayvel on 15 May 2025
 */
@NiagaraType
public class BSnDriverPointFolder
  extends BNPointFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.point.BSnDriverPointFolder(2979906276)1.0$ @*/
/* Generated Thu May 15 11:10:33 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnDriverPointFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the network cast to a BSnDriverNetwork.
   *
   * @return network as a BSnDriverNetwork.
   */
  public final BSnDriverNetwork getSnDriverNetwork()
  {
    return (BSnDriverNetwork) getNetwork();
  }

  /**
   * Get the device cast to a BSnDriverDevice.
   *
   * @return device as a BSnDriverDevice.
   */
  public final BSnDriverDevice getSnDriverDevice()
  {
    return (BSnDriverDevice) getDevice();
  }
}
