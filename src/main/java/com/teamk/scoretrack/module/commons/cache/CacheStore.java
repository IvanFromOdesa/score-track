package com.teamk.scoretrack.module.commons.cache;

public class CacheStore {
    public static final String UUID_CACHE_STORE = "uuidTokens";
    public static final String OTP_CACHE_STORE = "recoveryTokens";
    public static final String AUTH_CACHE_STORE = "authBeans";
    public static final String AUTH_HISTORY_CACHE_STORE = "authHistoryCache";
    public static final String GEO_RESPONSE_CACHE_STORE = "geoResponse";
    public static final String MAIL_CACHE_STORE = "emails";
    public static final String BAD_CREDENTIALS_FAILURE_CACHE_STORE = "badCredsFailure";
    public static final String IP_AUTH_FAILURE_CACHE_STORE = "ipAuthFailure";
    public static final String CRYPTO_TOKENS = "cryptoTokens";
    public static final String API_RATE_LIMITER = "apiRateLimiter";
    public static final String SESSION_EXPIRATION_ALERT = "sessionExpirationAlert";

    /**
     * NBA API
     */
    public static class ApiNba {
        private static final String PREFIX = "apiNba-";
        public static final String TEAM_DATA = PREFIX + "teamData";
        public static final String TEAM_STATS = PREFIX + "teamStats";
        public static final String TEAM_STATS_AVG_BY_SEASON = PREFIX + "teamStatsAvgBySeason";
        public static final String TOP_PLAYERS_BY_SEASON = PREFIX + "topPlayersBySeason";
    }
}
