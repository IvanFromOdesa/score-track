package com.teamk.scoretrack.module.core.entities.user.client.domain;

import com.teamk.scoretrack.module.commons.base.domain.Identifier;
import com.teamk.scoretrack.module.core.entities.SportAPI;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
public class ViewershipPlan extends Identifier {
    @CreationTimestamp
    private Instant createdAt;
    /**
     * Plan is active until this datetime. Can be renewed.
     * This also applies to custom list.
     */
    private Instant endDateTime;
    // TODO: Hibernate n + 1 problem
    @ElementCollection(targetClass = SportAPI.class)
    @JoinTable(name = "view_rights", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "api", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<SportAPI> customAvailableApis;
    /**
     * Users can select how they purchase api services: either via plans (predefined apis)
     * or by custom select. If user already has a plan and opts to purchase additional api services
     * that are not in the plan, then all apis in the plan transform to the list above + new selected api.
     * After transform, this becomes {@link PlannedViewership#RESELECTED}.
     * The other way around - converting custom apis to the plan is not available.
     */
    private PlannedViewership plannedViewership;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
    }

    public List<SportAPI> getCustomAvailableApis() {
        return customAvailableApis;
    }

    public void setCustomAvailableApis(List<SportAPI> customAvailableApis) {
        this.customAvailableApis = customAvailableApis;
    }

    public PlannedViewership getPlannedViewership() {
        return plannedViewership;
    }

    public void setPlannedViewership(PlannedViewership plannedViewership) {
        this.plannedViewership = plannedViewership;
    }

    public int[] getAvailableApiCodes() {
        return (this.plannedViewership == null || !this.plannedViewership.isValid()) ? customAvailableApis.stream().map(SportAPI::getKey).mapToInt(Integer::intValue).toArray() : this.plannedViewership.getApiCodes();
    }

    public boolean isActive() {
        return endDateTime.isAfter(Instant.now());
    }

    public static ViewershipPlan getDefault() {
        ViewershipPlan viewershipPlan = new ViewershipPlan();
        viewershipPlan.setPlannedViewership(PlannedViewership.PLAN_API_STANDARD);
        viewershipPlan.setEndDateTime(Instant.now().plus(PlannedViewership.PLAN_API_STANDARD.getDuration(), ChronoUnit.MILLIS));
        return viewershipPlan;
    }
}
