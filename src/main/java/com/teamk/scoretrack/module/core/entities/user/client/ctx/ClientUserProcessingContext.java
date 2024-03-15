package com.teamk.scoretrack.module.core.entities.user.client.ctx;

import com.teamk.scoretrack.module.core.entities.user.base.ctx.UserProcessingContext;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.data.util.Pair;

public abstract class ClientUserProcessingContext extends UserProcessingContext {
    private final ViewershipCreationContext viewershipCreationContext;

    public ClientUserProcessingContext(AuthenticationBean authenticationBean, ViewershipCreationContext viewershipCreationContext) {
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
