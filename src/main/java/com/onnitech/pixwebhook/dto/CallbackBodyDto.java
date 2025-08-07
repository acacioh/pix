package com.onnitech.pixwebhook.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CallbackBodyDto(
        String key,
        String endToEndId,
        String refundReason,
        String transactionId,
        String returnIdentification,
        String originalEndToEndId,
        String reportDate,
        String fraudType
) {
}
