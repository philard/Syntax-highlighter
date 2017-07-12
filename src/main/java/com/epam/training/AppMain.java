package com.epam.training;

import com.epam.training.context.AppConfig;
import com.epam.training.sentence.SyntaxHighlighter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;

public class AppMain {

    public static void main(String args[]) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DynamicSyntaxHighlighter dynamicSyntaxHighlighter = (SyntaxHighlighter) context.getBean("dynamicSyntaxHighlighter");
        String serializedConfig = "am-bold,to-italic,in-underline,to-yellow,java-red";
        Collection<KeywordEffectPair> keywordEffectPairCollection = buildHighlightConfig(serializedConfig);
        dynamicSyntaxHighlighter.setHighlightConfig(keywordEffectPairCollection);
        
        String sentence = "I am going to join java mentoring program to learn cool stuff in fun way.";
        System.out.println(sentenceValidatorImpl.highlightThis(sentence));
        context.close();

    }


    private Collection<KeywordEffectPair> buildHighlightConfig(String serializedConfig) {
        ArrayList<KeywordEffectPair> pairs = new ArrayList<>();

        String[] configArray = serializedConfig.split("(-|,\\s?)");
        for (int i = 0; i < configArray.length; i++) {
            highlightConfigs.add(new KeywordEffectPair(configArray.next(), configArray.next()));
        }
    }

}