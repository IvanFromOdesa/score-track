package com.teamk.scoretrack.module.commons.mongo.convert;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

public interface MongoReadingConverter<T> extends Converter<Document, T> {
}
