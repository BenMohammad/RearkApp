package com.benmohammad.rearkapp.utils;

import androidx.annotation.NonNull;

public interface ApplicationInstrumentation extends Instrumentation {

    @NonNull
    LeakTracing getLeakTracing();
}
