package com.teamk.scoretrack.module.commons.i18n;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class ExposedResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {
    /**
     * <a href="https://stackoverflow.com/a/27532814/18763596">Original source</a> Modified by Ivan Krylosov
     */
    private static final String PROPERTIES_SUFFIX = ".properties";
    private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    /**
     * Set this property in runtime to obtain a single bundle from a set.
     */
    private String targetFileName;

    @Override
    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
        boolean multipleBundlesPrefix = filename.startsWith(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX);
        if (multipleBundlesPrefix && isNullSingleFileName()) {
            return refreshClassPathProperties(filename, propHolder);
        } else if (!multipleBundlesPrefix) {
            return super.refreshProperties(filename, propHolder);
        } else {
            filename = filename.replaceFirst("\\*", "").replace("*", targetFileName);
            return super.refreshProperties(filename, propHolder);
        }
    }

    private PropertiesHolder refreshClassPathProperties(String filename, PropertiesHolder propHolder) {
        Properties properties = new Properties();
        long lastModified = -1;
        try {
            Resource[] resources = resolver.getResources(filename + PROPERTIES_SUFFIX);
            for (Resource resource : resources) {
                String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
                PropertiesHolder holder = super.refreshProperties(sourcePath, propHolder);
                properties.putAll(holder.getProperties());
                if (lastModified < resource.lastModified()) {
                    lastModified = resource.lastModified();
                }
            }
        } catch (IOException ignored) {

        }
        return new PropertiesHolder(properties, lastModified);
    }

    /**
     * <a href="https://stackoverflow.com/a/31761884/18763596">Source</a>
     */
    public Properties getMessages(Locale locale) {
        Properties properties = getMergedProperties(locale).getProperties();
        if (!isNullSingleFileName()) {
            targetFileName = "";
        }
        return properties;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public boolean isNullSingleFileName() {
        return targetFileName == null || targetFileName.isEmpty();
    }
}
