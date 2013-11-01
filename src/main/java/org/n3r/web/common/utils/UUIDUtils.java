package org.n3r.web.common.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23)
                + uuid.substring(24);
    }

}
