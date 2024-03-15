package com.teamk.scoretrack.module.commons.token;

/**
 * Get a specific token from the specified input.
 * @apiNote consider implementing if the input is required
 * @param <T> token type
 * @param <SOURCE> input type
 */
public interface ITokenResolver<T, SOURCE> {
    T generateToken(SOURCE source);
}
