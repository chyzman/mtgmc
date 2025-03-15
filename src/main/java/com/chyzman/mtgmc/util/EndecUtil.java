package com.chyzman.mtgmc.util;

import io.wispforest.endec.Endec;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.apache.commons.lang3.mutable.MutableObject;

public class EndecUtil {
    public static <T> StructEndec<T> structifyEndec(String fieldName, Endec<T> endec) {
        return wrappedEndec(fieldName, endec).xmap(MutableObject::getValue, MutableObject::new);
    }

    public static <T> StructEndec<MutableObject<T>> wrappedEndec(String fieldName, Endec<T> endec) {
        return StructEndecBuilder.of(endec.fieldOf(fieldName, MutableObject::getValue), MutableObject::new);
    }
}
