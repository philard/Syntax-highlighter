package com.epam.training;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.ArrayList;

@Configuration
public class AppConfig {

    @Bean(name="syntaxHighlighter")
    @Description("a bean called syntaxHighlighter")
    public SyntaxHighlighter syntaxHighlighter() {
        SyntaxHighlighterImpl syntaxHighlighter = new SyntaxHighlighterImpl();
        syntaxHighlighter.setWordHighlighters(wordHighlighters());
        syntaxHighlighter.setSentenceValidator(new SentenceValidator());

        return syntaxHighlighter;
    }

    private ArrayList<IWordHighlighter> wordHighlighters() {
        ArrayList<IWordHighlighter> wordHighlighters = new ArrayList();
        wordHighlighters.add(getSimpleWordHighlighter());
        return wordHighlighters;
    }

    private SimpleWordHighlighter getSimpleWordHighlighter() {
        return new SimpleWordHighlighter(" [bold]", " am ", "[/bold] ");
    }

}
