package com.vincentg.util;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

import java.io.*;
import java.util.Properties;

/**
 * Loads property file
 * @author vincentg
 */
public class PropertiesConverter implements IStringConverter<Properties> {

    @Override
    public Properties convert(String value) {

        Properties props = new Properties();
        try {
            props.load(new BufferedReader(new FileReader(value)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ParameterException("Unable to parse configuration file");
        }

        return props;
    }
}
