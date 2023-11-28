package com.productdock.featureflags.flagd;

import dev.openfeature.contrib.providers.flagd.FlagdProvider;
import dev.openfeature.sdk.OpenFeatureAPI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class FlagdAPI {

    @Produces
    @FlagdQualifier
    public OpenFeatureAPI getApi() {
        final OpenFeatureAPI openFeatureAPI = OpenFeatureAPI.getInstance();
        openFeatureAPI.setProvider(new FlagdProvider());
        return openFeatureAPI;
    }
}
