import {showBasicPopup} from "./popups.js";

const eventSource = new EventSource('/sse/session/alert/subscribe');

eventSource.addEventListener('session-expiration-alert', event => {
    const data = event.data;
    showBasicPopup(JSON.parse(data));
    eventSource.close();
});

eventSource.onmessage = event => {
    console.log("Fallback message received: ", event.data);
};

eventSource.onerror = () => {
    console.error("Connection lost. Reconnecting...");
};