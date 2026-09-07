package com.twilightchime.blog.utils;

import jakarta.annotation.Nonnull;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {

    @Nonnull
    public static String[] getNullPropertyNames(Object source) {

        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();

        for (PropertyDescriptor pd : pds) {
            if (beanWrapper.getPropertyValue(pd.getName()) == null) {
                nullPropertyNames.add(pd.getName());
            }
        }

        String[] result = new String[nullPropertyNames.size()];
        return nullPropertyNames.toArray(result);
    }
}
