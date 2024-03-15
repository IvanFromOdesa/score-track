function validateProfile() {

}

/**
 * Echoes back the request body, resetting profile img if uploaded
 * @param body
 * @returns {*}
 */
function processProfileData(body) {
    // Mock processing logic
    const uploadedProfilePic = body.profileImg;
    body.profileImg = {
        "url": uploadedProfilePic ? undefined : "https://media.api-sports.io/basketball/teams/2196.png",
        "accessStatus": {
            "code": uploadedProfilePic ? 2 : 1,
            "accessible": !uploadedProfilePic,
            "requiresReview": !!uploadedProfilePic,
            "nsfw": false,
            "undefined": false
        },
        "prompt": uploadedProfilePic ? "Your profile picture will be updated once it's reviewed." : undefined
    }
    return body;
}

module.exports = {
    validateProfile,
    processProfileData
};