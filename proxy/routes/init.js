const express = require('express');
const sendJson = require('./util');
const generateAccessToken = require('../services/token');
const router = express.Router();

router.get('/init', (req, res) => {
    sendJson(res, '../json/init.json', 1000);
});

router.get('/token/refresh', (req, res) => {
    res.json(generateAccessToken());
});

module.exports = router;