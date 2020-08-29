package com.benmohammad.rearkapp.pojo;

import android.telephony.SmsManager;
import android.transition.ChangeTransform;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static io.reark.reark.utils.Preconditions.checkNotNull;

public abstract class OverwritablePojo<T extends OverwritablePojo<T>> {

    private static final String TAG = OverwritablePojo.class.getSimpleName();

    @NonNull
    protected abstract Class<T> getTypeParameterClass();

    @NonNull
    @SuppressWarnings("unchecked")
    public T overWrite(@NonNull final T other) {
        checkNotNull(other, "Can't overwrite with null value");
        if(equals(other)) {
            return (T) this;
        }

        for(Field field : getTypeParameterClass().getDeclaredFields()) {
            final int modifiers = field.getModifiers();

            if(hasIllegalAccessModifiers(modifiers)) {
                continue;
            }

            if(!Modifier.isPublic(modifiers) || Modifier.isFinal(modifiers)) {
                field.setAccessible(true);
            }

            try {
                if(!isEmpty(field, other)) {
                    field.set(this, field.get(other));
                }
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Failed set at : " + field.getName(), e);
            }
        }
        return (T) this;
    }

    protected boolean hasIllegalAccessModifiers(int modifiers) {
        return Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers);
    }

    protected boolean isEmpty(@NonNull final Field field, @NonNull final OverwritablePojo<T> pojo) {
        try {
            Object value = field.get(pojo);
            if(value == null) {
                return true;
            } else if(value instanceof String) {
                return isEmpty((String) value);
            } else if(value instanceof Boolean) {
                return false;
            } else if(value instanceof Long) {
                return isEmpty((Long) value);
            } else if(value instanceof Integer) {
                return isEmpty((Integer) value);
            } else if(value instanceof Double) {
                return isEmpty((Double) value);
            } else if(value instanceof Float) {
                return isEmpty((Float) value);
            } else if(value instanceof Short) {
                return isEmpty((Short) value);
            } else if(value instanceof Byte) {
                return isEmpty((Byte) value);
            } else if(value  instanceof Character) {
                return isEmpty((Character) value);
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Failed to get at: " + field.getName(), e);
        }

        Log.e(TAG, "Unknown field type: " + field.getName());
        return true;
    }

    protected boolean isEmpty(String value) {
        return false;
    }

    protected boolean isEmpty(long value) {
        return false;
    }

    protected boolean isEmpty(int value) {
        return false;
    }

    protected boolean isEmpty(double value) {
        return false;
    }

    protected boolean isEmpty(float value) {
        return false;
    }

    protected boolean isEmpty(short value) {
        return false;
    }

    protected boolean isEmpty(byte value) {
        return false;
    }

    protected boolean isEmpty(char value) {
        return false;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return getTypeParameterClass().isInstance(obj);
    }
}
