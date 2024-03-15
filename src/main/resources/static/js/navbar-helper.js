const setLanguagePreference = async (lang, recaptcha_key) => {
    grecaptcha.ready(function() {
        const action = `/preferences/lang/${lang}`;
        grecaptcha.execute(recaptcha_key, { action: action }).then(async function(token) {
            const res = await fetch(`${action}?g-recaptcha-response=${token}`, {
                method: 'POST'
            });
            if (res.ok) {
                location.reload();
            }
        });
    });
}