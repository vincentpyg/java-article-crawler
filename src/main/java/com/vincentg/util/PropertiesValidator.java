package com.vincentg.util;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import org.jsoup.helper.Validate;

import java.util.Properties;

import static com.vincentg.common.AppConstants.*;

/**
 * Validate configuration properties file
 * @author vincentg
 */
public class PropertiesValidator implements IValueValidator<Properties> {

    @Override
    public void validate(String name, Properties value) throws ParameterException {
        try {
            Validate.notEmpty(value.getProperty(CONFIG_SOURCE_URL),CONFIG_SOURCE_URL+" is missing");
            Validate.notEmpty(value.getProperty(CONFIG_CRAWL_PATTERN),CONFIG_SOURCE_URL+" is missing");
            Validate.notEmpty(value.getProperty(CONFIG_AUTHOR_PATTERN),CONFIG_SOURCE_URL+" is missing");
            Validate.notEmpty(value.getProperty(CONFIG_HEADLINE_PATTERN),CONFIG_SOURCE_URL+" is missing");
            Validate.notEmpty(value.getProperty(CONFIG_CONTENT_PATTERN),CONFIG_SOURCE_URL+" is missing");
        }
        catch (IllegalArgumentException e) {
            throw new ParameterException("parameter "+name+" has invalid properties: "+e.getMessage());
        }
    }
}
