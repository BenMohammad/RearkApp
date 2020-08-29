package com.benmohammad.rearkapp.utils;

import androidx.annotation.NonNull;

import static io.reark.reark.utils.Preconditions.get;

public class DebugApplicationInstrumentation implements ApplicationInstrumentation {

    @NonNull
    private final LeakTracing leakTracing;

    @NonNull
    private final Instrumentation instrumentation;

    public DebugApplicationInstrumentation(@NonNull LeakTracing leakTracing,
                                           @NonNull Instrumentation networkInstrumentation) {
        this.leakTracing = leakTracing;
        this.instrumentation = get(networkInstrumentation);
    }

    @Override
    public void init() {
        leakTracing.init();
        instrumentation.init();
    }

    @Override
    @NonNull
    public LeakTracing getLeakTracing() {
        return leakTracing;
    }
}
