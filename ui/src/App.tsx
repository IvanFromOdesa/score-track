import React, {lazy} from 'react';
import './App.css';
import './main.scss';
import 'react-bootstrap-typeahead/css/Typeahead.css';
import 'react-bootstrap-typeahead/css/Typeahead.bs5.css';
import "reflect-metadata";
import {observer} from "mobx-react";
import {BrowserRouter, Navigate, Outlet, Route, Routes} from "react-router-dom";
import {useStoreContext} from "./common/stores/store.context";
import {HOME, PROFILE_PAGE} from "./common/routes/routes";

const Home = lazy(() => import(/* webpackChunkName: "home" */ "home"));
const Ad = lazy(() => import(/* webpackChunkName: "ad" */ "ad"));
const ProfilePage = lazy(() => import(/* webpackChunkName: "profilePage" */ "profile/page"));

function App() {
    const { rootStore : { authStore } } = useStoreContext();
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
