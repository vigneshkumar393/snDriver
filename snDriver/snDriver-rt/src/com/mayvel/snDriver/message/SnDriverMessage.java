/*
 * Copyright 2025 Mayvel. All Rights Reserved.
 */

package com.mayvel.snDriver.message;

import java.io.*;

import com.tridium.ndriver.comm.*;
import com.tridium.ndriver.datatypes.*;
import com.tridium.ndriver.io.TypedInputStream;

/**
 * SnDriverMessage is super class for all snDriver messages
 *
 * @author Mayvel on 15 May 2025
 */
public class SnDriverMessage
  extends NMessage
{
  public SnDriverMessage(BAddress address)
  {
    super(address);
  }
  // Override for outgoing messages
//  public boolean toOutputStream(OutputStream out)
//    throws Exception
//  {
//    // Use typed stream for more readable code.
//    TypedOutputStream to = new TypedOutputStream();
//
//    to.toOutputStream(out);
//    return false;
//  }

  //   Override for incoming messages
//  public void fromInputStream(InputStream in)
//    throws Exception
//  {
//    // Use typed stream for more readable code.
//    // Note: messageFactory must have created TypedInputStream
//    TypedInputStream ti = (TypedInputStream)in;
//  }

  //   Typical overrides
//  public Object getTag() { return nullTag; }
//  public boolean isResponse() { return false; }
//  public boolean isFragmentable() { return false; }
//  public int getResponseTimeOut() { return 2500; }

//  public String toTraceString()
//  {
//    return "??";
//  }

}
