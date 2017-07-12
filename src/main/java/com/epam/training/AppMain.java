package com.epam.training;

import com.epam.training.context.AppConfig;
import com.epam.training.sentence.data.KeywordEffectPair;
import com.epam.training.sentence.impl.DynamicSyntaxHighlighterImp;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class AppMain {

    public static void main(String args[]) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DynamicSyntaxHighlighterImp dynamicSyntaxHighlighter = (DynamicSyntaxHighlighterImp) context.getBean("dynamicSyntaxHighlighter");
        String serializedConfig = "am-bold,to-italic,in-underline,to-yellow,java-red";
        Collection<KeywordEffectPair> keywordEffectPairCollection = buildHighlightConfig(serializedConfig);
        dynamicSyntaxHighlighter.setHighlightConfig(keywordEffectPairCollection);
        
        String sentence = "I am going to join java mentoring program to learn cool stuff in fun way.";
        System.out.println(dynamicSyntaxHighlighter.highlightThis(sentence));
        context.close();

    }


    private static Collection<KeywordEffectPair> buildHighlightConfig(String serializedConfig) {
        ArrayList<KeywordEffectPair> config = new ArrayList<>();

        Iterator<String> words = Arrays.asList(serializedConfig.split("(-|,\\s?)")).iterator();
        while(words.hasNext()) {
            config.add(new KeywordEffectPair(words.next(), words.next()));
        }
        return config;
    }

}