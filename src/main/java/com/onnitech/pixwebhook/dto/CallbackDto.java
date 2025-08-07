package com.onnitech.pixwebhook.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CallbackDto(
        String pactualId,
        String entity,
        String endToEndId,
        String status,
        String returnIdentification,
        String originalEndToEndId,
        CallbackBodyDto body
) {
}
