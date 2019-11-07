package com.example.lifecyclefragment.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marco on 8/7/18.
 */

public class BookContent {
    public static class Book {
        public Integer id;
        public String title;
        public String desc;

        public Book(Integer id, String title, String desc) {
            this.id = id;
            this.title = title;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public static List<Book> ITEMS = new ArrayList<>();
    public static Map<Integer, Book> ITEM_MAP = new HashMap<>();

    static {
        addItem(new Book(1, "Crazy JAVA", "A book names Crazy JAVA"));
        addItem(new Book(2, "Deep in Android", "A book names Deep in Android"));
        addItem(new Book(3, "C++ Primer", "A book names C++ Primer"));
    }

    private static void addItem(Book book) {
        ITEMS.add(book);
        ITEM_MAP.put(book.id, book);
    }
}
