const express = require('express');
const sendJson = require('./util');
const findSports = require("../services/sports");
const {processProfileData, validateProfile} = require("../services/profile");

const router = express.Router();

router.get('/apis', (req, res) => {
    sendJson(res, '../json/apis.json');
});

router.get('/profile/init', (req, res) => {
    sendJson(res, '../json/profile.json');
});

router.post('/profile/update', (req, res) => {
    const body = req.body;
    // TODO: mock validation
    validateProfile(body);
    res.send({"data": processProfileData(body)});
})

router.get('/search/sports', (req, res) => {
    const search = req.query.q;
    search == null ? res.send([]) : res.send(findSports(search));
});

module.exports = router;