package com.epam.training;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Philip_John_Ardley on 13-Mar-17.
 */
class SimpleWordHighlighter implements  IWordHighlighter{

    private String prefix;

    private String word;

    private String postfix;

    public SimpleWordHighlighter(String prefix, String word, String postfix) {
        this.prefix = prefix;
        this.word = word;
        this.postfix = postfix;
    }

    public String highlightThis(String sentence) {

//        Pattern = Pattern.compile("").matcher
        String highlightedWord = prefix + word + postfix;
        sentence = sentence.replaceAll("(?<=\\s)" + word + "(?=\\s|\\.)", highlightedWord);

        return sentence;
    }
}
