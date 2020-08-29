package com.benmohammad.rearkapp.utils;

public interface LeakTracing extends Instrumentation {

    void traceLeakage(Object reference);
}
