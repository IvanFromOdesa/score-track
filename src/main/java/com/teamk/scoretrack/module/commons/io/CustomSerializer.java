package com.teamk.scoretrack.module.commons.io;

import com.fasterxml.jackson.databind.JsonSerializer;

public abstract class CustomSerializer<S> extends JsonSerializer<S> implements ISerializationClassAware<S> {

}
