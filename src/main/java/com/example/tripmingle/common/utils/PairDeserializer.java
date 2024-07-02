package com.example.tripmingle.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class PairDeserializer extends StdDeserializer<Pair<Long, Double>> {

    public PairDeserializer() {
        this(null);
    }

    public PairDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Pair<Long, Double> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

        if (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            Long left = Long.parseLong(field.getKey());
            Double right = field.getValue().asDouble();
            return new ImmutablePair<>(left, right);
        }

        throw new IOException("Invalid JSON format for Pair");
    }
}


