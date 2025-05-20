/*
 * Copyright 2025 Mayvel. All Rights Reserved.
 */

package com.mayvel.snDriver.point;

import javax.baja.control.BBooleanPoint;
import javax.baja.control.BBooleanWritable;
import javax.baja.control.BControlPoint;
import javax.baja.control.BEnumPoint;
import javax.baja.control.BEnumWritable;
import javax.baja.control.BNumericPoint;
import javax.baja.control.BNumericWritable;
import javax.baja.control.BStringPoint;
import javax.baja.control.BStringWritable;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.registry.TypeInfo;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusString;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComponent;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ndriver.discover.BNPointDiscoveryLeaf;
import com.tridium.ndriver.util.SfUtil;

/**
 * BSnDriverPointDiscoveryLeaf is container class for point elements to display in
 * point discovery pane and pass to new point callback.
 *
 * @author Mayvel on 15 May 2025
 */
@NiagaraType
@NiagaraProperty(
  name = "statusValue",
  type = "BStatusValue",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT",
  flags = Flags.READONLY,
  facets = @Facet(name = "SfUtil.KEY_MGR", value = "SfUtil.MGR_UNSEEN")
)
public class BSnDriverPointDiscoveryLeaf
  extends BNPointDiscoveryLeaf
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.point.BSnDriverPointDiscoveryLeaf(1643154273)1.0$ @*/
/* Generated Thu May 15 11:10:33 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "statusValue"

  /**
   * Slot for the {@code statusValue} property.
   * @see #getStatusValue
   * @see #setStatusValue
   */
  public static final Property statusValue = newProperty(Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code statusValue} property.
   * @see #statusValue
   */
  public BStatusValue getStatusValue() { return (BStatusValue)get(statusValue); }

  /**
   * Set the {@code statusValue} property.
   * @see #statusValue
   */
  public void setStatusValue(BStatusValue v) { set(statusValue, v, null); }

  //endregion Property "statusValue"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.READONLY, BFacets.DEFAULT, BFacets.make(SfUtil.KEY_MGR, SfUtil.MGR_UNSEEN));

  /**
   * Get the {@code facets} property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnDriverPointDiscoveryLeaf.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  public BSnDriverPointDiscoveryLeaf()
  {
  }

  // Return TypeInfo for valid new objects - match proxy type to statusValue type.
  public TypeInfo[] getValidDatabaseTypes()
  {
    Array<TypeInfo> a = new Array<>(TypeInfo.class);
    BStatusValue sv = getStatusValue();

    //
    // TODO determine valid types for this leaf
    //

//    if(sv instanceof BStatusNumeric)
//    {
    a.add(BNumericPoint.TYPE.getTypeInfo());
//      if(writable) a.add(BNumericWritable.TYPE.getTypeInfo());
//    }
//    if(sv instanceof BStatusBoolean)
//    {
//      a.add(BBooleanPoint.TYPE.getTypeInfo());
//      if(writable) a.add(BBooleanWritable.TYPE.getTypeInfo());
//    }
//    if(sv instanceof BStatusString)
//    {
//      a.add(BStringPoint.TYPE.getTypeInfo());
//      if(writable) a.add(BStringWritable.TYPE.getTypeInfo());
//    }
//    if(sv instanceof BStatusEnum)
//    {
//      a.add(BEnumPoint.TYPE.getTypeInfo());
//      if(writable) a.add(BEnumWritable.TYPE.getTypeInfo());
//    }

    return a.trim();
  }

  // Call when adding new object based on this discovery leaf.  Initialize proxy.
  public void updateTarget(BComponent target)
  {
    BControlPoint cp = (BControlPoint) target;
    BSnDriverProxyExt pext = new BSnDriverProxyExt();

    //
    // TODO - initialize values in new point
    //

    cp.setFacets(getFacets());
    cp.setProxyExt(pext);

    cp.getStatusValue().setValueValue(getStatusValue().getValueValue());
  }

  /**
   * Return true if the specified component is an existing representation
   * of this discovery object.
   */
  public boolean isExisting(BComponent target)
  {
    if (!(target instanceof BControlPoint))
    {
      return false;
    }
    BControlPoint cp = (BControlPoint) target;
    BSnDriverProxyExt pext = (BSnDriverProxyExt) cp.getProxyExt();
    //
    // TODO - return true if specified component represents this leaf
    //

    return false;
  }
}
