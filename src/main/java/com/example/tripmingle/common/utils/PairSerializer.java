package com.example.tripmingle.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;

public class PairSerializer extends StdSerializer<Pair<Long, Double>> {

    public PairSerializer() {
        this(null);
    }

    public PairSerializer(Class<Pair<Long, Double>> t) {
        super(t);
    }

    @Override
    public void serialize(Pair<Long, Double> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(String.valueOf(value.getLeft()), value.getRight());
        gen.writeEndObject();
    }
}

