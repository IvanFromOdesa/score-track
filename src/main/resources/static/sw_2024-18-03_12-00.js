/*const CACHE_NAME = 'req_cache';

async function handle(event) {
    try {
        const res = await fetch(event.request);
        const cache = await caches.open(CACHE_NAME);
        await cache.put(event.request, res.clone());

        return res;
    } catch (error) {
        return await caches.match(event.request);
    }
}

self.addEventListener('fetch', function(event) {
    event.respondWith(
        handle(event)
    );
})*/;