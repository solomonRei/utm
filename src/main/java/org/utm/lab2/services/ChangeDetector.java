package org.utm.lab2.services;

import java.util.HashMap;
import java.util.Map;

abstract public class ChangeDetector {

    protected Map<String, Long> lastModifiedMap = new HashMap<>();

    public abstract void detectChanges();
}
