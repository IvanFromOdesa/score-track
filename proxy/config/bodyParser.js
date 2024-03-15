const bodyParser = require('body-parser');
const jsonParser = bodyParser.json();
const urlencodedParser = bodyParser.urlencoded({ extended: false });
const rawParser = bodyParser.raw();
const textParser = bodyParser.text();

module.exports = {jsonParser, urlencodedParser, rawParser, textParser};