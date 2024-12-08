package com.teamk.scoretrack.module.core.entities.user.client.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.teamk.scoretrack.module.commons.io.CustomTypeSerializer;
import com.teamk.scoretrack.module.core.entities.sport_api.SportAPI;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ViewershipPlanSerializer extends CustomTypeSerializer<ViewershipPlan> {
    public ViewershipPlanSerializer() {
        super(ViewershipPlan.class);
    }

    protected void writeToJson(ViewershipPlan plan, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumberField("id", plan.getId());

        if (plan.getCreatedAt() != null) {
            gen.writeNumberField("createdAt", plan.getCreatedAt().toEpochMilli());
        }

        if (plan.getEndDateTime() != null) {
            gen.writeNumberField("endDateTime", plan.getEndDateTime().toEpochMilli());
        }

        List<SportAPI> customAvailableApis = plan.getCustomAvailableApis();

        if (customAvailableApis != null && !customAvailableApis.isEmpty()) {

            gen.writeArrayFieldStart("customAvailableApis");

            for (SportAPI api: customAvailableApis) {
                gen.writeObject(api);
            }

            gen.writeEndArray();
        } else {
            gen.writeArrayFieldStart("customAvailableApis");
            gen.writeEndArray();
        }

        if (plan.getPlannedViewership() != null) {
            gen.writeObjectField("plannedViewership", plan.getPlannedViewership());
        }
    }

    @Override
    public Class<ViewershipPlan> getSerializationClass() {
        return ViewershipPlan.class;
    }
}