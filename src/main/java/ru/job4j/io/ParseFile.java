package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return getContentByPredicate(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContentByPredicate(data -> data < 0x80);
    }

    private String getContentByPredicate(Predicate<Integer> filter) throws IOException {
        StringBuffer output = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = reader.read()) > 0) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
}