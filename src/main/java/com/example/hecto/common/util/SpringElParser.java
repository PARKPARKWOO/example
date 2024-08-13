package com.example.hecto.common.util;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpringElParser {
    public static Object getDynamicValue(String[] parameterName, Object[] args, String key) {
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterName.length; i++) {
            context.setVariable(parameterName[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, Object.class);
    }
}
