package com.epam.training;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class AppMain {

    public static void main(String args[]) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SyntaxHighlighter bean = (SyntaxHighlighter) context.getBean("syntaxHighlighter");

        bean.highlightThis("I am going");
        context.close();

    }

}