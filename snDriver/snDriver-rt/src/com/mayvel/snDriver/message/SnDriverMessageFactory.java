/*
 * Copyright 2025 Mayvel. All Rights Reserved.
 */

package com.mayvel.snDriver.message;

import com.tridium.ndriver.comm.IMessageFactory;
import com.tridium.ndriver.comm.LinkMessage;
import com.tridium.ndriver.comm.NMessage;

/**
 * SnDriverMessageFactory implementation of IMessageFactory.
 *
 * @author Mayvel on 15 May 2025
 */
public class SnDriverMessageFactory
  implements IMessageFactory
{
  public SnDriverMessageFactory()
  {
  }

  public NMessage makeMessage(LinkMessage lm)
    throws Exception
  {
    //
    // TODO - convert linkMessage driver specific NMessage
    return null;
  }
}
