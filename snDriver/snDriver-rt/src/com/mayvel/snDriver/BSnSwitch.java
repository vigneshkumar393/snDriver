package com.mayvel.snDriver;

import com.mayvel.snDriver.utils.CustomLicenseGenerator;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

@NiagaraType

@NiagaraProperty(name = "stringValue",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY
)
@NiagaraProperty(
        name = "matchValue",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY
)

@NiagaraProperty(
        name = "conditionOut",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY | Flags.READONLY,
        facets = {
                @Facet(name = "BFacets.MULTI_LINE", value = "BBoolean.TRUE"),
        }
)

@NiagaraAction(
        name = "trigger")
@NiagaraAction(
        name = "testCall")
@NiagaraAction(
        name = "reset")
public class BSnSwitch extends BComponent {
  public int counter=0;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.BSnSwitch(1965594106)1.0$ @*/
/* Generated Fri Jun 13 15:20:14 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "stringValue"

  /**
   * Slot for the {@code stringValue} property.
   * @see #getStringValue
   * @see #setStringValue
   */
  public static final Property stringValue = newProperty(Flags.SUMMARY, "", null);

  /**
   * Get the {@code stringValue} property.
   * @see #stringValue
   */
  public String getStringValue() { return getString(stringValue); }

  /**
   * Set the {@code stringValue} property.
   * @see #stringValue
   */
  public void setStringValue(String v) { setString(stringValue, v, null); }

  //endregion Property "stringValue"

  //region Property "matchValue"

  /**
   * Slot for the {@code matchValue} property.
   * @see #getMatchValue
   * @see #setMatchValue
   */
  public static final Property matchValue = newProperty(Flags.SUMMARY, "", null);

  /**
   * Get the {@code matchValue} property.
   * @see #matchValue
   */
  public String getMatchValue() { return getString(matchValue); }

  /**
   * Set the {@code matchValue} property.
   * @see #matchValue
   */
  public void setMatchValue(String v) { setString(matchValue, v, null); }

  //endregion Property "matchValue"

  //region Property "conditionOut"

  /**
   * Slot for the {@code conditionOut} property.
   * @see #getConditionOut
   * @see #setConditionOut
   */
  public static final Property conditionOut = newProperty(Flags.SUMMARY | Flags.READONLY, "", BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code conditionOut} property.
   * @see #conditionOut
   */
  public String getConditionOut() { return getString(conditionOut); }

  /**
   * Set the {@code conditionOut} property.
   * @see #conditionOut
   */
  public void setConditionOut(String v) { setString(conditionOut, v, null); }

  //endregion Property "conditionOut"

  //region Action "trigger"

  /**
   * Slot for the {@code trigger} action.
   * @see #trigger()
   */
  public static final Action trigger = newAction(0, null);

  /**
   * Invoke the {@code trigger} action.
   * @see #trigger
   */
  public void trigger() { invoke(trigger, null, null); }

  //endregion Action "trigger"

  //region Action "testCall"

  /**
   * Slot for the {@code testCall} action.
   * @see #testCall()
   */
  public static final Action testCall = newAction(0, null);

  /**
   * Invoke the {@code testCall} action.
   * @see #testCall
   */
  public void testCall() { invoke(testCall, null, null); }

  //endregion Action "testCall"

  //region Action "reset"

  /**
   * Slot for the {@code reset} action.
   * @see #reset()
   */
  public static final Action reset = newAction(0, null);

  /**
   * Invoke the {@code reset} action.
   * @see #reset
   */
  public void reset() { invoke(reset, null, null); }

  //endregion Action "reset"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnSwitch.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
public void doTrigger() {
  counter++;
  setConditionOut("Triggered " +counter + " times.");
}

public void doTestCall(){
  counter++;
  if(CustomLicenseGenerator.isSnLimitValid("http")){
    setConditionOut("Triggered " +"|| "+CustomLicenseGenerator.isSnLimitValid("http")+ " times.");
  }else{
    setConditionOut("http limit reached");
  }
}

  public void doReset() {
    counter = 0;
    setStringValue("");
    setConditionOut("");
  }
  public void started() throws Exception {
    super.started();
  }


  @Override
  public void stopped() throws Exception {
    super.stopped();
  }

  @Override
  public void changed(Property property, Context context) {
    super.changed(property, context);
    if (property.equals(stringValue)
            || property.equals(matchValue)) {
      checkAndTriggerMatch();
    }
  }


  private void checkAndTriggerMatch() {
    String stValue = getStringValue();
    String mtValue = getMatchValue();

    if (stValue != null && !stValue.isEmpty()
            && mtValue != null && !mtValue.isEmpty()
            && stValue.equals(mtValue) ) {

      // Check if start or end time has changed
        invoke(trigger, null, null);
    }
  }
}
