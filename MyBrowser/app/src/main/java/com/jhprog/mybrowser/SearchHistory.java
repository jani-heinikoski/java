package com.jhprog.mybrowser;

import java.util.ArrayList;

public class SearchHistory {
    private String currentURL;
    private ArrayList<String> history;
    private int cursor;

    public SearchHistory() {
        currentURL = "";
        cursor = 0;
        history = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            history.add("");
        }
    }

    public String previous() {
        String p;

        if (hasPrevious()) {
            p = history.get(cursor - 1);
            currentURL = p;
            cursor--;
        } else {
            return null;
        }

        return p;
    }

    public String next() {
        String n;

        if (hasNext()) {
            n = history.get(cursor + 1);
            currentURL = n;
            cursor++;
        } else {
            return null;
        }

        return n;
    }

    public void add(String url) {
        if (url != null && !url.trim().isEmpty()) {
            if (cursor < (history.size() - 1)) {
                int j = history.size();
                for (int i = (cursor+1); i < j; i++) {
                    history.set(i, "");
                }
            }

            if (history.get(history.size() - 1) != "") {
                history.remove(0);
                history.add(url);
                cursor = history.size() - 1;
            } else {
                for (int i = 0; i < history.size(); i++) {
                    if (history.get(i).equals("")) {
                        history.set(i, url);
                        cursor = i;
                        break;
                    }
                }
            }
            currentURL = url;
        }
    }

    public boolean hasPrevious() {
        try {
            if (history.get(cursor - 1) != "") {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasNext() {
        try {
            if (history.get(cursor + 1) != "") {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentURL() {
        return currentURL;
    }

    /*
    public void printList() {
        System.out.println("----------------------LOGGER:-----------------------");
        for (String s : history) {
            System.out.println("LOGGER: " + s);
        }
        System.out.println("LOGGER: cursor: " + cursor + " item: " + currentURL + " size " + history.size());
        System.out.println("---------------------LOGGER:------------------------");
    }

     */
}
