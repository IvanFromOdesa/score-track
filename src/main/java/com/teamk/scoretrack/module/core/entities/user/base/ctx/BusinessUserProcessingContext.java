package com.teamk.scoretrack.module.core.entities.user.base.ctx;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.data.util.Pair;

public abstract class BusinessUserProcessingContext extends UserProcessingContext {
    private final ViewershipCreationContext viewershipCreationContext;

    public BusinessUserProcessingContext(AuthenticationBean authenticationBean, ViewershipCreationContext viewershipCreationContext) {
        super(authenticationBean);
        this.viewershipCreationContext = viewershipCreationContext;
    }

    public ViewershipCreationContext getViewershipCreationContext() {
        return viewershipCreationContext;
    }

    public static class ViewershipCreationContext {
        private int plannedViewershipCode;
        private Pair<Long, int[]> customApiPlan;

        public Pair<Long, int[]> getCustomApiPlan() {
            return customApiPlan;
        }

        public void setCustomApiPlan(Pair<Long, int[]> customApiPlan) {
            this.plannedViewershipCode = -1;
            this.customApiPlan = customApiPlan;
        }

        public int getPlannedViewershipCode() {
            return plannedViewershipCode;
        }

        public void setPlannedViewershipCode(int plannedViewershipCode) {
            this.customApiPlan = null;
            this.plannedViewershipCode = plannedViewershipCode;
        }
    }
}
