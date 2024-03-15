function generateAccessToken() {
    return {"value": "dummyvalue", "exp": (new Date().getTime() + 5 * 60000)};
}

module.exports = generateAccessToken;