package com.productdock.featureflags.configcat;

import dev.openfeature.contrib.providers.configcat.ConfigCatProvider;
import dev.openfeature.contrib.providers.configcat.ConfigCatProviderConfig;
import dev.openfeature.sdk.OpenFeatureAPI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ConfigCat {
    @ConfigProperty(name = "configcat.sdkkey")
    public String configCatKey;

    @Produces
    @ConfigCatQualifier
    public OpenFeatureAPI getApi() {
        final OpenFeatureAPI openFeatureAPI = OpenFeatureAPI.getInstance();
        var configCat = ConfigCatProviderConfig.builder()
                .sdkKey(configCatKey)
                .build();
        openFeatureAPI.setProviderAndWait(new ConfigCatProvider(configCat));
        return openFeatureAPI;
    }
}
