package com.epam.training.context;

import com.epam.training.sentence.SyntaxHighlighter;
import com.epam.training.validation.impl.SentenceValidatorImpl;
import com.epam.training.word.IWordHighlighter;
import com.epam.training.word.impl.SimpleWordHighlighter;
import com.epam.training.sentence.impl.SyntaxHighlighterImpl;
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
        syntaxHighlighter.setSentenceValidatorImpl(new SentenceValidatorImpl());

        return syntaxHighlighter;
    }

    private ArrayList<IWordHighlighter> wordHighlighters() {
        ArrayList<IWordHighlighter> wordHighlighters = new ArrayList();
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

}
