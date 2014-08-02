package ua.samosfator.knu.abit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Faculty {
    private String name;
    private int code, year;

    public Faculty(int year, int code, String name) {
        this.year = year;
        this.code = code;
        this.name = name;
    }

    public String getURL() {
        String baseURL = "http://www.univ.kiev.ua/ua/abit/";
        return baseURL + year + "/Lists/1/" + code;
    }

    public int getCode() {
        return code;
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public static List<Faculty> getFaculties(int year) throws IOException {
        List<Faculty> faculties = new ArrayList<>();
        Document doc = Jsoup.connect("http://www.univ.kiev.ua/ua/abit/" + year + "/Lists/1").timeout(0).get();
        Elements facultyLinks = doc.select(".b-references__link");

        faculties.addAll(facultyLinks.stream()
                .map(facultyLink -> new Faculty(year, Integer.parseInt(facultyLink.attr("href").split("/")[6]), facultyLink.text()))
                .collect(Collectors.toList()));
        return faculties;
    }
}
