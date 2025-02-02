package com.franmunozbetanzos.portfolio.constant;

public class ValidationConstants {

    public static final class Username {
        public static final int MIN_LENGTH = 3;
        public static final int MAX_LENGTH = 20;
        public static final String PATTERN = "^[a-zA-Z0-9._-]+$";
    }

    public static final class Password {
        public static final int MIN_LENGTH = 8;
        public static final int MAX_LENGTH = 50;
        public static final String PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
    }

    public static final class Email {
        public static final int MAX_LENGTH = 50;
    }
}
