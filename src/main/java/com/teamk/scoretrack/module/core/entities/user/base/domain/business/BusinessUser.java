package com.teamk.scoretrack.module.core.entities.user.base.domain.business;

import com.teamk.scoretrack.module.core.entities.user.base.domain.Profile;
import com.teamk.scoretrack.module.core.entities.user.base.domain.ViewershipPlan;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;

@MappedSuperclass
public abstract class BusinessUser extends User {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "view_plan", referencedColumnName = "id")
    private ViewershipPlan viewershipPlan;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ViewershipPlan getViewershipPlan() {
        return viewershipPlan;
    }

    public void setViewershipPlan(ViewershipPlan viewershipPlan) {
        this.viewershipPlan = viewershipPlan;
    }
}
