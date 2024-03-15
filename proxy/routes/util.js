const path = require('path');

function sendJson(res, jsonPath, sleepDur) {
    function _send() {
        res.header("Content-Type", 'application/json');
        res.sendFile(path.join(__dirname, jsonPath));
    }

    if (sleepDur) {
        this.setTimeout(function () {
            _send();
        }, sleepDur);
    } else {
        _send();
    }
}

module.exports = sendJson;
