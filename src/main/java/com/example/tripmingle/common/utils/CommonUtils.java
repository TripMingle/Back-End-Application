package com.example.tripmingle.common.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

@Component
public class CommonUtils {

    public String convertListToString(List<String> list){
        StringJoiner joiner = new StringJoiner(",");
        for (String s : list) {
            joiner.add(s);
        }
        return joiner.toString();
    }

    public List<String> convertStringToArray(String str) {
        if (str == null || str.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(str.split(","));
    }

}
