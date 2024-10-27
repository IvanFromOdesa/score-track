package com.teamk.scoretrack.module.core.entities.user.client.ctx;

import com.teamk.scoretrack.module.core.entities.user.base.ctx.UserProcessingContext;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.data.util.Pair;

import java.time.LocalDate;

public abstract class ClientUserProcessingContext extends UserProcessingContext {
    private final ViewershipCreationContext viewershipCreationContext;
    private final ProfileCreationContext profileCreationContext;

    public ClientUserProcessingContext(AuthenticationBean authenticationBean,
                                       ViewershipCreationContext viewershipCreationContext) {
        super(authenticationBean);
        this.viewershipCreationContext = viewershipCreationContext;
        this.profileCreationContext = new ProfileCreationContext();
    }

    public ClientUserProcessingContext(AuthenticationBean authenticationBean,
                                       ViewershipCreationContext viewershipCreationContext,
                                       ProfileCreationContext profileCreationContext) {
        super(authenticationBean);
        this.viewershipCreationContext = viewershipCreationContext;
        this.profileCreationContext = profileCreationContext;
    }

    public ViewershipCreationContext getViewershipCreationContext() {
        return viewershipCreationContext;
    }

    public ProfileCreationContext getProfileCreationContext() {
        return profileCreationContext;
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

    public static class ProfileCreationContext {
        private String firstName;
        private String lastName;
        private LocalDate dob;
        private String imageUrl;
        private String nickname;
        private String bio;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public LocalDate getDob() {
            return dob;
        }

        public void setDob(LocalDate dob) {
            this.dob = dob;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }
    }
}
