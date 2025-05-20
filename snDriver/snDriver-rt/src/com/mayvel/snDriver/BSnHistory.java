package com.mayvel.snDriver;

import com.mayvel.snDriver.subscribers.AlarmSubscriber;
import com.mayvel.snDriver.utils.HttpHistoryPost;
import com.mayvel.snDriver.utils.Logger;
import com.tridium.json.JSONObject;

import javax.baja.history.*;
import javax.baja.history.db.BHistoryDatabase;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.io.IOException;

@NiagaraType

@NiagaraProperty(name = "historySource",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY
)
@NiagaraProperty(
        name = "historyOut",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY | Flags.READONLY,
        facets = {
                @Facet(name = "BFacets.MULTI_LINE", value = "BBoolean.TRUE"),
        }
)
public class BSnHistory extends BComponent {
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.BSnHistory(1025315689)1.0$ @*/
/* Generated Tue May 20 10:01:18 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "historySource"

  /**
   * Slot for the {@code historySource} property.
   * @see #getHistorySource
   * @see #setHistorySource
   */
  public static final Property historySource = newProperty(Flags.SUMMARY, "", null);

  /**
   * Get the {@code historySource} property.
   * @see #historySource
   */
  public String getHistorySource() { return getString(historySource); }

  /**
   * Set the {@code historySource} property.
   * @see #historySource
   */
  public void setHistorySource(String v) { setString(historySource, v, null); }

  //endregion Property "historySource"

  //region Property "historyOut"

  /**
   * Slot for the {@code historyOut} property.
   * @see #getHistoryOut
   * @see #setHistoryOut
   */
  public static final Property historyOut = newProperty(Flags.SUMMARY | Flags.READONLY, "", BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code historyOut} property.
   * @see #historyOut
   */
  public String getHistoryOut() { return getString(historyOut); }

  /**
   * Set the {@code historyOut} property.
   * @see #historyOut
   */
  public void setHistoryOut(String v) { setString(historyOut, v, null); }

  //endregion Property "historyOut"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnHistory.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public void started() throws Exception {
    super.started();

    try {
      checkHistoryUpdates();
    } catch (Exception e) {
      e.printStackTrace();
    }
    startTriggerStateThreads();
  }

  private void checkHistoryUpdates() {
    try {
      Logger.Log("1 Resolving HttpClientRequestHistory...");
      BHistoryService historyService = (BHistoryService) Sys.getService(BHistoryService.TYPE);

      if (historyService == null) {
        Logger.Error("BHistoryService not available.");
        return;
      }

      BHistoryDatabase db = historyService.getDatabase();

      if (db != null) {

        db.addHistoryEventListener(new HistoryEventListener() {
          @Override
          public void historyEvent(BHistoryEvent bHistoryEvent) {
              String path = getHistorySource();
              BOrd ord = BOrd.make(path);

              BIHistory history = (BIHistory) ord.resolve().get();
              Logger.Log("2  History resolved successfully");
              BHistoryConfig config = history.getConfig();

              BHistoryId historyId = config.getId();

              if (!bHistoryEvent.getHistoryId().equals(historyId) || getHistorySource().isEmpty()) return;
              Logger.Log("3  History resolved successfully");
              String rawRecord = bHistoryEvent.getRecordSet().getLastRecord().toString();
              try {
                setHistoryOut(rawRecord);
                Logger.Log("5 Table fetched: " + rawRecord);
                Logger.Log("6 Table fetched: " + bHistoryEvent.getRecordSet().getLastRecord());
                Logger.Log("7 getName: " + bHistoryEvent.getRecordSet().getLastRecord().getName());
                Logger.Log("8 getType: " + bHistoryEvent.getRecordSet().getLastRecord().getType());
                Logger.Log("9 getType: " + bHistoryEvent.getRecordSet().getLastRecord().getSchema());

                BHistoryRecord record = bHistoryEvent.getRecordSet().getLastRecord();
                BHistorySchema schema = record.getSchema();
                String schemaStr = schema.toString();
                Logger.Log("9 Schema: " + schemaStr);
                // Create a JSON object to store the schema and values
                JSONObject jsonObject = new JSONObject();

                // Split the schema to get field names
                String[] fields = schemaStr.split(";");
                for (String field : fields) {
                  String[] keyValue = field.split(",", 2);
                  if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String type = keyValue[1].trim();

                    try {
                      BValue value = record.get(key);
                      if (value != null) {
                        jsonObject.put(key, value.toString());  // Store key-value pair in JSON object
                        Logger.Log("10 Field: " + key + " => Type: " + type + " => Value: " + value.toString());
                      } else {
                        jsonObject.put(key, "null");  // If value is null, store "null"
                        Logger.Log("10 Field: " + key + " => Type: " + type + " => Value: null");
                      }
                    } catch (Exception e) {
                      Logger.Log("Error reading value for field '" + key + "': " + e.getMessage());
                    }
                  } else {
                    Logger.Log("Malformed schema field: " + field);
                  }
                }

                // Convert the JSON object to string and set it as the result
                setHistoryOut(jsonObject.toString(2));  // Pretty print with indentation level 2
                Logger.Log("11 Final JSON Object: " + jsonObject.toString(2));  // Log the final JSON object
              } catch (Exception e) {
                setHistoryOut("Error parsing history event: \" + e.getMessage()");
                Logger.Log("Error parsing history event: " + e.getMessage());
              }
          }
        });
      } else {
        setHistoryOut("db not available");
        Logger.Error("db not available");
      }
    } catch (Exception e) {
      setHistoryOut("History read failed: " + e.getMessage());
      Logger.Error("History read failed: " + e.getMessage());
    }
  }

  @Override
  public void stopped() throws Exception {
    super.stopped();
    try {
      HttpHistoryPost.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void startTriggerStateThreads() {
    try {
      HttpHistoryPost.start();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
