import {QueryClient} from "@tanstack/react-query";

export const TANSTACK_QUERY_CLIENT = new QueryClient();

// Query keys
export const SUPPORTED_APIS_KEY = "supported_apis";
export const GAMES_KEY = "games";
export const PROFILE_PAGE_KEY = "profile_page";
export const SPORTS_KEY = "sports";