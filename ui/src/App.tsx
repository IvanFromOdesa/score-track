import React, {lazy} from 'react';
import './App.css';
import './main.scss';
import 'react-bootstrap-typeahead/css/Typeahead.css';
import 'react-bootstrap-typeahead/css/Typeahead.bs5.css';
import "reflect-metadata";
import {observer} from "mobx-react";
import {BrowserRouter, Navigate, Outlet, Route, Routes} from "react-router-dom";
import {useStoreContext} from "./common/base/stores/store.context";
import {API_NBA_BASE_PATH, HOME, PROFILE_PAGE, TEAM_PAGE, PLAYER_PAGE} from "./common/base/routes/routes";
import {APIS} from "./api/apis";

const Home = lazy(() => import(/* webpackChunkName: "home" */ "home"));
const Ad = lazy(() => import(/* webpackChunkName: "ad" */ "ad"));
const ProfilePage = lazy(() => import(/* webpackChunkName: "profilePage" */ "profile/page"));
const ApiNbaTeamPage = lazy(() => import(/* webpackChunkName: "apiNbaTeamPage" */ "./api/nbaapi/teams/page"));
const ApiNbaPlayerPage = lazy(() => import(/* webpackChunkName: "apiNbaPlayerPage" */ "./api/nbaapi/players/page"));

function App() {
    const { rootStore : { authStore, uiStore } } = useStoreContext();
    const authenticated = authStore.authenticated;

    return (
        <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route path={HOME} element={<AppWrapper />}>
                        <Route
                            path={HOME}
                            element={authenticated ? <Home /> : <Ad />}
                        />
                        <Route
                            path={PROFILE_PAGE}
                            element={authenticated ? <ProfilePage /> : <Navigate to="/" replace />}
                        />
                        <Route path={API_NBA_BASE_PATH}
                               element={authenticated && authStore.isApiCodeAvailable(APIS.API_NBA) && uiStore.isApiSportComponentMetadataAccessible(APIS.API_NBA, 'teams') ?
                                   <AppWrapper /> : <Navigate to="/" replace /> }>
                            <Route
                                path={TEAM_PAGE}
                                element={<ApiNbaTeamPage />}
                            />
                            <Route
                                path={PLAYER_PAGE}
                                element={<ApiNbaPlayerPage />}
                            />
                        </Route>
                    </Route>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

function AppWrapper() {
    return (
        <React.Suspense>
            <Outlet />
        </React.Suspense>
    )
}

export default observer(App);
