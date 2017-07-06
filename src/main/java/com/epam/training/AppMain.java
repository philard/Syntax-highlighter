package com.epam.training;

import com.epam.training.context.AppConfig;
import com.epam.training.sentence.SyntaxHighlighter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class AppMain {

    public static void main(String args[]) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SyntaxHighlighter sentenceValidatorImpl = (SyntaxHighlighter) context.getBean("syntaxHighlighter");

        String sentence = "I am going to join java mentoring program to learn cool stuff in fun way.";
        System.out.println(sentenceValidatorImpl.highlightThis(sentence));
        context.close();

    }

}