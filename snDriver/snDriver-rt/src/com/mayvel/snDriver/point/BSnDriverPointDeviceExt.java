/*
 * Copyright 2025 Mayvel. All Rights Reserved.
 */

package com.mayvel.snDriver.point;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ndriver.discover.*;
import com.tridium.ndriver.point.*;

import com.mayvel.snDriver.*;

/**
 * BSnDriverPointDeviceExt is a container for snDriver proxy points.
 *
 * @author Mayvel on 15 May 2025
 */
@NiagaraType
@NiagaraProperty(
  name = "discoveryPreferences",
  type = "BSnDriverPointDiscoveryPreferences",
  defaultValue = "new BSnDriverPointDiscoveryPreferences()",
  override = true
)
public class BSnDriverPointDeviceExt
  extends BNPointDeviceExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.point.BSnDriverPointDeviceExt(3693433849)1.0$ @*/
/* Generated Thu May 15 11:10:33 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "discoveryPreferences"

  /**
   * Slot for the {@code discoveryPreferences} property.
   * @see #getDiscoveryPreferences
   * @see #setDiscoveryPreferences
   */
  public static final Property discoveryPreferences = newProperty(0, new BSnDriverPointDiscoveryPreferences(), null);

  //endregion Property "discoveryPreferences"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnDriverPointDeviceExt.class);

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

////////////////////////////////////////////////////////////////
// PointDeviceExt
////////////////////////////////////////////////////////////////

  /**
   * @return the Device type.
   */
  public Type getDeviceType()
  {
    return BSnDriverDevice.TYPE;
  }

  /**
   * @return the PointFolder type.
   */
  public Type getPointFolderType()
  {
    return BSnDriverPointFolder.TYPE;
  }

  /**
   * @return the ProxyExt type.
   */
  public Type getProxyExtType()
  {
    return BSnDriverProxyExt.TYPE;
  }

////////////////////////////////////////////////////////////////
//BINDiscoveryHost
////////////////////////////////////////////////////////////////

  /**
   * Call back for discoveryJob to get an array of discovery objects.
   * Override point for driver specific discovery.
   */
  public BINDiscoveryObject[] getDiscoveryObjects(BNDiscoveryPreferences prefs)
    throws Exception
  {
    //
    // TODO  get array of discovery objects
    //
//    Array<??> a = new Array<>(??.class);
//    for(??)
//     a.add(new BSnDriverPointDiscoveryLeaf(??));
//    return a.trim();
    return null;
  }
}
