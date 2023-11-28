package com.productdock.featureflags;

import com.productdock.featureflags.configcat.ConfigCatQualifier;
import com.productdock.featureflags.devcycle.DevCycleQualifier;
import com.productdock.featureflags.flagd.FlagdQualifier;
import com.productdock.featureflags.hooks.PDTrackerHook;
import dev.openfeature.sdk.MutableContext;
import dev.openfeature.sdk.OpenFeatureAPI;
import io.quarkus.arc.runtime.BeanContainer;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class PdAPI {

    @Inject()
    @FlagdQualifier
//    @ConfigCatQualifier
//    @DevCycleQualifier
    OpenFeatureAPI openFeatureAPI;

    @GET()
    @Path("/check/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkFeature(@PathParam("email") String email) {
        final var client = openFeatureAPI.getClient();
        client.addHooks(new PDTrackerHook());

        var ctx = new MutableContext();
        ctx.setTargetingKey("isfromproductdock");

        ctx.add("Email", email);
//        ctx.add("email", email); // For DevCycle

        if (client.getBooleanValue("isfromproductdock", false, ctx)) {
            return Response.ok(new FeatureFlagResponse("This feature is enabled! User is from ProductDock.")).build();
        }
        return Response.ok(new FeatureFlagResponse("This feature is disabled! User is not from ProductDock.")).build();
    }
}

record FeatureFlagResponse(String message) {
}