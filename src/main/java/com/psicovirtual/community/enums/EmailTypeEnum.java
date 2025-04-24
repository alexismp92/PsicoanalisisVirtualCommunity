package com.psicovirtual.community.enums;

public enum EmailTypeEnum {
    REG_ADMIN("REGISTRATION_ADMIN"),
    REG_USER("REGISTRATION_USER"),
    APPROVED_USER("APPROVED_USER"),
    REJECTED_USER("REJECTED_USER");

    EmailTypeEnum(String emailType) {}
}
