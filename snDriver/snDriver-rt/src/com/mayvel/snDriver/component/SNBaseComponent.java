package com.mayvel.snDriver.component;

import javax.baja.sys.Sys;
import javax.baja.sys.Type;

public interface SNBaseComponent {
    Type TYPE = Sys.loadType(SNBaseComponent.class);

    boolean isValidLicense();
}