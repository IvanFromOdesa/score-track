const express = require('express');

const app = express();

const BASE = '/api/v1';
const API_NBA = '/nbaapi';

const initRoutes = require('./routes/init');
const nbaApiRoutes = require('./routes/nbaapi');
const baseRoutes = require('./routes/base');
const {urlencodedParser, jsonParser} = require("./config/bodyParser");

app.use(urlencodedParser);
app.use(jsonParser);

app.use('/api', initRoutes);

app.use(`${BASE}${API_NBA}`, nbaApiRoutes);
app.use(`${BASE}`, baseRoutes);

module.exports = app;