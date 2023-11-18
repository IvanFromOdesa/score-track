package com.teamk.scoretrack.module.commons.token;


public interface ITokenResolver<T, SOURCE> {
    T generateToken(SOURCE source);
}
