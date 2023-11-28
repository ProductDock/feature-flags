package com.productdock.featureflags.devcycle;

import com.devcycle.sdk.server.local.api.DevCycleLocalClient;
import com.devcycle.sdk.server.local.model.DevCycleLocalOptions;
import dev.openfeature.sdk.OpenFeatureAPI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class DevCycle {
    @ConfigProperty(name = "devcycle.sdkkey")
    public String devCycleKey;

    @Produces
    @DevCycleQualifier
    public OpenFeatureAPI getApi() {
        final OpenFeatureAPI openFeatureAPI = OpenFeatureAPI.getInstance();
        DevCycleLocalOptions options = DevCycleLocalOptions.builder().build();
        DevCycleLocalClient devCycleClient = new DevCycleLocalClient(devCycleKey, options);

        // This is wild. Should be handled by the client.
        // https://github.com/DevCycleHQ/java-server-sdk/pull/111/files/4e21dc9a8f7d5d4d063528b355fc5c6125d9c78b#r1381707824
        for (int i = 0; i < 10; i++) {
            if (devCycleClient.isInitialized()) {
                break;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        openFeatureAPI.setProvider(devCycleClient.getOpenFeatureProvider());
        return openFeatureAPI;
    }
}
