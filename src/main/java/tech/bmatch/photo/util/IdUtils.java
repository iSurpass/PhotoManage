package tech.bmatch.photo.util;

import java.util.UUID;

public class IdUtils {

    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        return id.replaceAll("-","");
    }
}
