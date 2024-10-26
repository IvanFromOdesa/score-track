const express = require('express');
const sendJson = require('./util');
const router = express.Router();

const TEAMS = '/teams';
const PLAYERS = '/players';
const API_PATH = '../json/nbaapi';

router.get('/games', (req, res) => {
    sendJson(res, `${API_PATH}/games.json`);
});

// Could be refactored, but just okay for a mocking server
router.get(TEAMS, (req, res) => {
    const page = parseInt(req.query.p);
    let jsonPath = API_PATH + '/teams/teams_empty.json';
    if ((page || page === 0) && page <= 2 && page >= 0) {
        jsonPath = jsonPath.replace('empty', page.toString());
    }
    sendJson(res, jsonPath, 2500);
});

router.get(`${TEAMS}/:id/stats`, (req, res) => {
    const id = parseInt(req.params.id);
    let jsonPath = API_PATH + '/teams/stats/stats_empty.json';
    if ((id || id === 0) && id >= 0 && id < 45) {
        jsonPath = jsonPath.replace('empty', Math.floor(id / 15).toString());
    }
    sendJson(res, jsonPath);
});

router.get(`${PLAYERS}/top`, (req, res) => {
    sendJson(res, `${API_PATH}/players/top.json`, 2500);
});

router.get(`${PLAYERS}/:id`, (req, res) => {
    sendJson(res, `${API_PATH}/players/player.json`);
});

router.get(`${PLAYERS}/:id/stats`, (req, res) => {
    sendJson(res, `${API_PATH}/players/avgStatsPerSeason.json`, 2000);
});

module.exports = router;
