package com.teamk.scoretrack.utils;

import com.teamk.scoretrack.module.core.entities.sport_api.SportAPI;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.core.entities.user.client.domain.PlannedViewership;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import com.teamk.scoretrack.module.core.entities.user.fan.domain.Fan;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public final class UserUtils {
    private static ViewershipPlan mockViewershipPlan() {
        ViewershipPlan plan = new ViewershipPlan();
        plan.setId(1L);
        Instant now = Instant.now();
        plan.setCreatedAt(now);
        plan.setEndDateTime(now.plus(1, ChronoUnit.MINUTES));
        return plan;
    }

    public static ViewershipPlan mockViewershipPlanWithPlannedViewership() {
        ViewershipPlan plan = mockViewershipPlan();
        plan.setPlannedViewership(PlannedViewership.PLAN_API_STANDARD);
        return plan;
    }

    public static ViewershipPlan mockViewershipPlanWithCustomAvailableApis() {
        ViewershipPlan plan = mockViewershipPlan();
        plan.setCustomAvailableApis(List.of(SportAPI.API_NBA));
        return plan;
    }

    public static Fan mockFan() {
        Fan fan = new Fan();

        fan.setId(1L);
        fan.setPreferredLang(Language.DEFAULT);
        fan.setLastSeen(Instant.now());
        fan.setViewershipPlan(UserUtils.mockViewershipPlanWithPlannedViewership());
        return fan;
    }
}
