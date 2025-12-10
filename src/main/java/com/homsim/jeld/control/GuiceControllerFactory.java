package com.homsim.jeld.control;

import com.google.inject.Injector;
import javafx.util.Callback;

public record GuiceControllerFactory(Injector injector) implements Callback<Class<?>, Object> {

    @Override
    public Object call(Class<?> type) {
        return injector.getInstance(type);
    }
}