package com.teamk.scoretrack.module.commons.service.token;


public interface ITokenResolver<T, SOURCE> {
    T generateToken(SOURCE source);
}
