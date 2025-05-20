/*
 * Copyright 2025 Mayvel. All Rights Reserved.
 */

package com.mayvel.snDriver.point;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ndriver.discover.BNDiscoveryPreferences;

/**
 * BSnDriverPointDiscoveryPreferences controls the type of discovery leafs using during
 * point discovery for snDriver
 *
 * @author Mayvel on 15 May 2025
 */
@NiagaraType
public class BSnDriverPointDiscoveryPreferences
  extends BNDiscoveryPreferences
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.point.BSnDriverPointDiscoveryPreferences(2979906276)1.0$ @*/
/* Generated Thu May 15 11:10:33 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnDriverPointDiscoveryPreferences.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  public BSnDriverPointDiscoveryPreferences()
  {
  }

  public Type getDiscoveryLeafType()
  {
    return BSnDriverPointDiscoveryLeaf.TYPE;
  }
}
