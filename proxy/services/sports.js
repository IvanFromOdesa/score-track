const AVAILABLE_SPORTS = [
    {
        "code": 0,
        "name": "Basketball"
    },
    {
        "code": 1,
        "name": "Football"
    },
    {
        "code": 2,
        "name": "Rugby"
    }
]

function findSports(name) {
    return AVAILABLE_SPORTS.filter(sport => sport.name.toLowerCase().includes(name.toLowerCase()));
}

module.exports = findSports;