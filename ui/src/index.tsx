import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {StoreProvider} from "./common/base/stores/store";
import {QueryClientProvider} from "@tanstack/react-query";
import {TANSTACK_QUERY_CLIENT} from "./common/base/config/tanstack.query.client";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
      <StoreProvider>
          <QueryClientProvider client={TANSTACK_QUERY_CLIENT}>
              <App />
          </QueryClientProvider>
      </StoreProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
