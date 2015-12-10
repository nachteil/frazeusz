package main.java.pl.edu.agh.ki.to2.parser.exceptions;

/**
 * Created by Adam on 29.11.2015.
 */
@SuppressWarnings("serial")
public class UnsupportedFileException extends Exception {
    public UnsupportedFileException(){
        System.out.println("File not supported");
    }
}
