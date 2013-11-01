package org.n3r.web.thymeleaf.expression;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.core.joor.Reflect;
import org.n3r.core.lang.RClassPath;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

public class SmallFunctionHelper {

    public static void addSmallFunctionTag(ThymeleafViewResolver viewResolver) {

        List<Class<?>> ftlFunctions = RClassPath.getClasses("org/n3r/web/thymeleaf/expression",
                new Predicate() {
                    @Override
                    public boolean apply(Object object) {
                        Class<?> clazz = (Class<?>) object;
                        return clazz.isAnnotationPresent(SmallFunctionTag.class);
                    }
                });

        Map<String, Object> staticVariables = viewResolver.getStaticVariables();
        if (MapUtils.isEmpty(staticVariables))
            staticVariables = Maps.newHashMap();

        for (Class<?> ftlFunctionCls : ftlFunctions) {
            SmallFunctionTag functionTag = ftlFunctionCls.getAnnotation(SmallFunctionTag.class);
            String name = StringUtils.defaultString(functionTag.value(), ftlFunctionCls.getSimpleName());
            staticVariables.put(name, Reflect.on(ftlFunctionCls).create().get());
        }
        viewResolver.setStaticVariables(staticVariables);
    }
}
