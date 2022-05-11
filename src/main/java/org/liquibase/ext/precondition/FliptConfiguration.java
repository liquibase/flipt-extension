package org.liquibase.ext.precondition;

import liquibase.configuration.AutoloadedConfigurations;
import liquibase.configuration.ConfigurationDefinition;

public class FliptConfiguration implements AutoloadedConfigurations {

    public static ConfigurationDefinition<String> FLIPT_URL;

    static {
        ConfigurationDefinition.Builder builder = new ConfigurationDefinition.Builder("liquibase.flipt");
        FLIPT_URL = builder.define("url", String.class)
                .addAliasKey("flipt.url")
                .setDescription("URL for Flipt API")
                .build();
    }
}
