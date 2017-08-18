package com.anythingintellect.hackernews.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimestampDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        String timeStamp = parser.getText().trim();
        return new Date(TimeUnit.SECONDS.toMillis(Long.valueOf(timeStamp)));
    }
}
