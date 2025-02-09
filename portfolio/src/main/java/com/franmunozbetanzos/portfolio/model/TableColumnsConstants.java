package com.franmunozbetanzos.portfolio.model;

import lombok.experimental.UtilityClass;

/**
 * Constants for table names and columns.
 */
@UtilityClass
public class TableColumnsConstants {

    // Table names
    static final String CONTACT = "contact";
    static final String USERS = "users";

    // Column names

    // User columns
    static final String USERNAME = "username";
    static final String PASSWORD = "password";


    // Base Entity columns
    static final String CREATED_AT = "created_at";
    static final String UPDATED_AT = "updated_at";
    static final String ENABLED = "enabled";
}
