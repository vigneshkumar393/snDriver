package com.mayvel.snDriver;

import com.mayvel.snDriver.utils.CustomLicenseGenerator;
import com.mayvel.snDriver.utils.Logger;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@NiagaraType
// Http properties
@NiagaraProperty(
        name = "url",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY
)
@NiagaraProperty(
        name = "username",
        type = "String",
        defaultValue = "",
        flags = 0
)
@NiagaraProperty(
        name = "password",
        type = "String",
        defaultValue = "",
        flags = 0
)
@NiagaraProperty(
        name = "requestBody",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY,
        facets = {
                @Facet(name = "BFacets.MULTI_LINE", value = "BBoolean.TRUE"),
        }
)
@NiagaraProperty(
        name = "httpOut",
        type = "String",
        defaultValue = "",
        flags = Flags.SUMMARY | Flags.READONLY,
        facets = {
                @Facet(name = "BFacets.MULTI_LINE", value = "BBoolean.TRUE"),
        }
)
@NiagaraProperty(
        name = "statusCode",
        type = "int",
        defaultValue = "BInteger.make(0)",
        flags = Flags.SUMMARY | Flags.READONLY
)
@NiagaraProperty(
        name = "httpMethod",
        type = "String",
        defaultValue = "GET",
        flags = Flags.SUMMARY
)
@NiagaraAction(name = "httpSend")
@NiagaraAction(name = "httpClearRecords")
public class BSnHttpClient extends BComponent {
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mayvel.snDriver.BSnHttpClient(1140226893)1.0$ @*/
/* Generated Tue May 20 10:01:18 IST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "url"

  /**
   * Slot for the {@code url} property.
   *  Http properties
   * @see #getUrl
   * @see #setUrl
   */
  public static final Property url = newProperty(Flags.SUMMARY, "", null);

  /**
   * Get the {@code url} property.
   *  Http properties
   * @see #url
   */
  public String getUrl() { return getString(url); }

  /**
   * Set the {@code url} property.
   *  Http properties
   * @see #url
   */
  public void setUrl(String v) { setString(url, v, null); }

  //endregion Property "url"

  //region Property "username"

  /**
   * Slot for the {@code username} property.
   * @see #getUsername
   * @see #setUsername
   */
  public static final Property username = newProperty(0, "", null);

  /**
   * Get the {@code username} property.
   * @see #username
   */
  public String getUsername() { return getString(username); }

  /**
   * Set the {@code username} property.
   * @see #username
   */
  public void setUsername(String v) { setString(username, v, null); }

  //endregion Property "username"

  //region Property "password"

  /**
   * Slot for the {@code password} property.
   * @see #getPassword
   * @see #setPassword
   */
  public static final Property password = newProperty(0, "", null);

  /**
   * Get the {@code password} property.
   * @see #password
   */
  public String getPassword() { return getString(password); }

  /**
   * Set the {@code password} property.
   * @see #password
   */
  public void setPassword(String v) { setString(password, v, null); }

  //endregion Property "password"

  //region Property "requestBody"

  /**
   * Slot for the {@code requestBody} property.
   * @see #getRequestBody
   * @see #setRequestBody
   */
  public static final Property requestBody = newProperty(Flags.SUMMARY, "", BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code requestBody} property.
   * @see #requestBody
   */
  public String getRequestBody() { return getString(requestBody); }

  /**
   * Set the {@code requestBody} property.
   * @see #requestBody
   */
  public void setRequestBody(String v) { setString(requestBody, v, null); }

  //endregion Property "requestBody"

  //region Property "httpOut"

  /**
   * Slot for the {@code httpOut} property.
   * @see #getHttpOut
   * @see #setHttpOut
   */
  public static final Property httpOut = newProperty(Flags.SUMMARY | Flags.READONLY, "", BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code httpOut} property.
   * @see #httpOut
   */
  public String getHttpOut() { return getString(httpOut); }

  /**
   * Set the {@code httpOut} property.
   * @see #httpOut
   */
  public void setHttpOut(String v) { setString(httpOut, v, null); }

  //endregion Property "httpOut"

  //region Property "statusCode"

  /**
   * Slot for the {@code statusCode} property.
   * @see #getStatusCode
   * @see #setStatusCode
   */
  public static final Property statusCode = newProperty(Flags.SUMMARY | Flags.READONLY, BInteger.make(0), null);

  /**
   * Get the {@code statusCode} property.
   * @see #statusCode
   */
  public int getStatusCode() { return getInt(statusCode); }

  /**
   * Set the {@code statusCode} property.
   * @see #statusCode
   */
  public void setStatusCode(int v) { setInt(statusCode, v, null); }

  //endregion Property "statusCode"

  //region Property "httpMethod"

  /**
   * Slot for the {@code httpMethod} property.
   * @see #getHttpMethod
   * @see #setHttpMethod
   */
  public static final Property httpMethod = newProperty(Flags.SUMMARY, "GET", null);

  /**
   * Get the {@code httpMethod} property.
   * @see #httpMethod
   */
  public String getHttpMethod() { return getString(httpMethod); }

  /**
   * Set the {@code httpMethod} property.
   * @see #httpMethod
   */
  public void setHttpMethod(String v) { setString(httpMethod, v, null); }

  //endregion Property "httpMethod"

  //region Action "httpSend"

  /**
   * Slot for the {@code httpSend} action.
   * @see #httpSend()
   */
  public static final Action httpSend = newAction(0, null);

  /**
   * Invoke the {@code httpSend} action.
   * @see #httpSend
   */
  public void httpSend() { invoke(httpSend, null, null); }

  //endregion Action "httpSend"

  //region Action "httpClearRecords"

  /**
   * Slot for the {@code httpClearRecords} action.
   * @see #httpClearRecords()
   */
  public static final Action httpClearRecords = newAction(0, null);

  /**
   * Invoke the {@code httpClearRecords} action.
   * @see #httpClearRecords
   */
  public void httpClearRecords() { invoke(httpClearRecords, null, null); }

  //endregion Action "httpClearRecords"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSnHttpClient.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  // Http methods

  public void doHttpSend() {
    new Thread(() -> {
      try {
        callHttpSend();  // <-- actual work
      } catch (Exception e) {
        e.printStackTrace();
      }
    }, "HttpSendThread").start();
  }

  public void callHttpSend() {
    String url = getUrl();
    String requestBody = getRequestBody();
    String httpMethod = getHttpMethod();

    try {
      URL apiUrl = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
      connection.setRequestMethod(httpMethod);

      connection.setRequestProperty("User-Agent", "MyHttpClient/1.0");
      connection.setRequestProperty("Accept", "application/json");
      connection.setRequestProperty("Content-Type", "application/json");

      String username = getUsername();
      String password = getPassword();
      if (!username.isEmpty() && !password.isEmpty()) {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
      }

      if ("POST".equalsIgnoreCase(httpMethod) || "PUT".equalsIgnoreCase(httpMethod)) {
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
          byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
          os.write(input);
        }
      }

      int statusCode = connection.getResponseCode();
      // Run the reading response logic in a separate thread
      new Thread(() -> {
        try {
          if (statusCode == HttpURLConnection.HTTP_FORBIDDEN) {
            setHttpOut("403 Forbidden: Access is denied.");
          } else {
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
              char[] buffer = new char[4096];
              int bytesRead;
              while ((bytesRead = in.read(buffer)) != -1) {
                response.append(buffer, 0, bytesRead);
              }
            }
            setHttpOut(response.toString());
          }
        } catch (IOException e) {
          e.printStackTrace();
          setHttpOut("Error reading response: " + e.getMessage());
        }
        setStatusCode(statusCode);
      }).start();

    } catch (IOException e) {
      e.printStackTrace();
      setHttpOut("Error: " + e.getMessage());
    }
  }

  public void doHttpClearRecords() {
    setString(httpOut, "", null);
    setInt(statusCode, 0, null);
  }

  @Override
  public void stopped() throws Exception {
    super.stopped();
  }

}
