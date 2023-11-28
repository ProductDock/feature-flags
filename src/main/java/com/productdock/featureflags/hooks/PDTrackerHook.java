package com.productdock.featureflags.hooks;

import dev.openfeature.sdk.BooleanHook;
import dev.openfeature.sdk.FlagEvaluationDetails;
import dev.openfeature.sdk.HookContext;
import org.jboss.logging.Logger;

import java.util.Map;

public class PDTrackerHook implements BooleanHook {
    private static final Logger LOG = Logger.getLogger(PDTrackerHook.class);
    @Override
    public void after(HookContext<Boolean> ctx, FlagEvaluationDetails<Boolean> details, Map<String, Object> hints) {
        LOG.info(details.getFlagKey() + " : " + details.getValue());
    }
}
