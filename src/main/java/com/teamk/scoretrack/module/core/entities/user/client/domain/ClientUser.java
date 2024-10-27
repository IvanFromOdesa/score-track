package com.teamk.scoretrack.module.core.entities.user.client.domain;

import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToOne;

/**
 * Updated on 28.11.2023 - use a real table with the shared properties
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedEntityGraph(
        name = ClientUser.CLIENT_EAGER,
        attributeNodes = {
                @NamedAttributeNode(value = "profile", subgraph = "profile-subgraph"),
                @NamedAttributeNode(value = "viewershipPlan", subgraph = "viewershipPlan-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "profile-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("sportPreference"),
                                @NamedAttributeNode("socials"),
                                @NamedAttributeNode("profileImg"),
                                @NamedAttributeNode("externalProfileImg")
                        }
                ),
                @NamedSubgraph(
                        name = "viewershipPlan-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("customAvailableApis")
                        }
                )
        }
)
public class ClientUser extends User {
    public static final String CLIENT_EAGER = "client-user-eager";

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
    /**
     * This one loads eagerly because of {@link #getAvailableApiCodes()}
     */
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
