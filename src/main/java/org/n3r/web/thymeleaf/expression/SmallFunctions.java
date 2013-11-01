package org.n3r.web.thymeleaf.expression;

import org.apache.commons.lang.StringUtils;
import org.n3r.config.Config;
import org.springframework.stereotype.Component;

@Component
@SmallFunctionTag("small")
public class SmallFunctions {

    /**
     * 为传入资源路径自动增加版本号.
     * @param resPath 资源路径
     * @return 增加版本号的路径
     */
    public static String res(String resPath) {
        StringBuffer res = new StringBuffer(StringUtils.removeEnd(Config.getStr("BaseResPath"), "/"))
                .append("/")
                .append(StringUtils.removeStart(resPath, "/"))
                .append("?resVer=").append(Config.getStr("ResVer"));
        return res.toString();
    }
}
