const express = require('express');
const sendJson = require('./util');
const router = express.Router();

router.get('/games', (req, res) => {
    sendJson(res, '../json/nbaapi/games.json');
});

module.exports = router;
