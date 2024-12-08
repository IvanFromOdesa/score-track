package com.teamk.scoretrack.module.commons.io;

public interface ISerializationClassAware<S> {
    Class<S> getSerializationClass();
}
