package com.epam.training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Created by philip_john_ardley on 7/17/17.
 */
public class AppMainTest {


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() throws FileNotFoundException {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void out() {
        System.out.print("System.setOut test");
        assertEquals("System.setOut test", outContent.toString());
    }

    @Test
    public void err() {
        System.err.print("System.setErr test");
        assertEquals("System.setErr test", errContent.toString());
    }

    @Test
    public void shouldGiveAResultGivenNoInputArguments() throws Exception {
        AppMain.main(new String[] {});
        String expected = "I [bold] am [/bold] going [italic] [yellow] to [/yellow] [/italic] join [red] java [/red] mentoring program [italic] [yellow] to [/yellow] [/italic] learn cool stuff [underline] in [/underline] fun way.\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void shouldInterpretCommandLineInterface() {
        String input = "am-gray,to-italic,in-underline,to-yellow,java-red";
        String expected = "I [gray] am [/gray] going [italic] [yellow] to [/yellow] [/italic] join [red] java [/red] mentoring program [italic] [yellow] to [/yellow] [/italic] learn cool stuff [underline] in [/underline] fun way.\n";
        AppMain.main(new String[] {input});
        assertEquals(expected, outContent.toString());
    }

}