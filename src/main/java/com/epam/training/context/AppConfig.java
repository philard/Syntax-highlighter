package com.epam.training.context;

import com.epam.training.sentence.SyntaxHighlighter;
import com.epam.training.sentence.impl.SyntaxHighlighterImp;
import com.epam.training.validation.impl.SentenceValidatorImp;
import com.epam.training.word.WordHighlighter;
import com.epam.training.word.impl.ColorWordHighlighter;
import com.epam.training.word.impl.SimpleWordHighlighter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class AppConfig {

    @Bean(name="syntaxHighlighter")
    @Scope("prototype")
    @Description("a bean called syntaxHighlighter")
    public SyntaxHighlighter syntaxHighlighter() {
        SyntaxHighlighterImp syntaxHighlighter = new SyntaxHighlighterImp(new SentenceValidatorImp());
        syntaxHighlighter.setWordHighlighters(wordHighlighters());
        return syntaxHighlighter;
    }

    private Collection<WordHighlighter> wordHighlighters() {
        ArrayList<WordHighlighter> wordHighlighters = new ArrayList<>();
        wordHighlighters.add(getAmWordHighlighter());
        wordHighlighters.add(getToWordHighlighter());
        wordHighlighters.add(getInWordHighlighter());
        return wordHighlighters;
    }

    private SimpleWordHighlighter getAmWordHighlighter() {
        return new SimpleWordHighlighter("[bold]", "am", "[/bold]");
    }
    private SimpleWordHighlighter getToWordHighlighter() {
        return new SimpleWordHighlighter("[italic]", "to", "[/italic]");
    }
    private SimpleWordHighlighter getInWordHighlighter() {
        return new SimpleWordHighlighter("[underline]", "in", "[/underline]");
    }

    @Bean(name = "syntaxHighlighterWithColor")
    @Scope("prototype")
    public SyntaxHighlighter syntaxHighlighterWithColor() {
        SyntaxHighlighterImp syntaxHighlighterWithColor = new SyntaxHighlighterImp(new SentenceValidatorImp());
        syntaxHighlighterWithColor.setWordHighlighters(colorWordHighlighters());
        return syntaxHighlighterWithColor;
    }

    private Collection<WordHighlighter> colorWordHighlighters() {
        ArrayList<WordHighlighter> colorWordHighlighters = new ArrayList<>();
        colorWordHighlighters.add(getAmWordHighlighter());
        colorWordHighlighters.add(getToWordHighlighter());
        colorWordHighlighters.add(getInWordHighlighter());
        colorWordHighlighters.add(getJavaWordHighlighter());
        return colorWordHighlighters;
    }


    private ColorWordHighlighter getJavaWordHighlighter() {
        return new ColorWordHighlighter("[red]", "java", "[/red]");
    }
}
