export class CallbackInjector<T> {
    _DEFAULT: T;
    _CALLBACK: () => T;

    constructor(_DEFAULT: T) {
        this._DEFAULT = _DEFAULT;
        this._CALLBACK = () => _DEFAULT;
    }

    get default(): T {
        return this._DEFAULT;
    }

    set callback(_CALLBACK: () => T) {
        this._CALLBACK = _CALLBACK;
    }

    get callback(): T {
        return this._CALLBACK();
    }
}

export const ACCESS_TOKEN = new CallbackInjector({value: '', exp: -1});
export const RECAPTCHA_KEY = new CallbackInjector("");