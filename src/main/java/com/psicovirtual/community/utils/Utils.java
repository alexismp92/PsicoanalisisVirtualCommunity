package com.psicovirtual.community.utils;

import com.psicovirtual.community.exception.CommunityException;

import java.util.UUID;

import static com.psicovirtual.community.utils.Constants.DOT;

public class Utils {

    public static String generateUUUID(){
        return UUID.randomUUID().toString();
    }

    public static String getExtension(String filename) throws CommunityException {
        var lastDotIndex = filename.lastIndexOf(DOT);

        if (lastDotIndex <= 0){
            throw new CommunityException("Invalid file extension");
        }
        return filename.substring(lastDotIndex + 1);
    }
}
