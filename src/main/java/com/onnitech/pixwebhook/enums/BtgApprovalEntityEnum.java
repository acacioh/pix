package com.onnitech.pixwebhook.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BtgApprovalEntityEnum {

    PIX_PAYMENT( "PixPayment") {
        @Override
        public boolean containsAtRequest(String body) {
            return body.contains("\"entity\":\"PixPayment\"") || body.contains("\"entity\": \"PixPayment\"");
        }
    },
    PIX_REVERSAL("PixReversal") {
        @Override
        public boolean containsAtRequest(String body) {
            return body.contains("\"entity\":\"PixReversal\"") || body.contains("\"entity\": \"PixReversal\"");
        }
    };

    private final String entity;

    public abstract boolean containsAtRequest(String body);

}
