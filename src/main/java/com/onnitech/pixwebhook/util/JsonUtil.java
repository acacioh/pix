package com.onnitech.pixwebhook.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class JsonUtil {

    private JsonUtil() {
    }

    private static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final DateTimeFormatter LOCAL_DATE_FORMAT_ISO8601 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT_ISO8601 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static final ObjectMapper MAPPER = createDefaultObjectMapper();

    private static ObjectMapper createDefaultObjectMapper() {

        var jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
        //LocalDateTime
        jackson2ObjectMapperBuilder.serializers(new LocalDateTimeSerializer(LOCAL_DATE_TIME_FORMAT_ISO8601));
        jackson2ObjectMapperBuilder.deserializers(new LocalDateTimeDeserializer(LOCAL_DATE_TIME_FORMAT_ISO8601));

        //LocalDate
        jackson2ObjectMapperBuilder.serializers(new LocalDateSerializer(LOCAL_DATE_FORMAT_ISO8601));
        jackson2ObjectMapperBuilder.deserializers(new LocalDateDeserializer(LOCAL_DATE_FORMAT_ISO8601));

        //Date
        jackson2ObjectMapperBuilder.dateFormat(new SimpleDateFormat(DATE_FORMAT_STR_ISO8601));

        jackson2ObjectMapperBuilder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        jackson2ObjectMapperBuilder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return jackson2ObjectMapperBuilder.build();
    }

    public static String writeValueAsString(Object object) {
        try {
            return MAPPER.writeValueAsString(object).replace("\\", "");
        } catch (Exception e) {
            PixWebhookUtil.errorLogger(e.getMessage());
            return null;
        }
    }

    public static <C> C readValue(String json, Class<C> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            PixWebhookUtil.errorLogger(e.getMessage());
            return null;
        }
    }

}
