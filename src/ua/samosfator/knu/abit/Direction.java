package ua.samosfator.knu.abit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Direction {
    private Faculty faculty;
    private String name;
    private int code, wave;

    public Direction(Faculty faculty, String name, int code) {
        this.faculty = faculty;
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getURL() {
        return "http://www.univ.kiev.ua/ua/abit/" + faculty.getYear() +
                "/Lists/1/" + faculty.getCode() + "/" + code;
    }

    public String getWaveURL(int wave) {
        this.wave = wave;
        return "http://www.univ.kiev.ua/ua/abit/" + faculty.getYear() + "/Waves/" +
                wave + "/Recommend/" + faculty.getCode() + "/" + code + "/" + wave;
    }

    public int getCode() {
        return code;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public int getWave() {
        return wave;
    }

    public static List<Direction> getDirections(Faculty faculty) throws IOException {
        List<Direction> directions = new ArrayList<>();
        Document doc = Jsoup.connect(faculty.getURL()).timeout(0).get();
        Elements dirs = doc.select(".list_spec");

        directions.addAll(dirs.stream().map(dir -> new Direction(
                faculty,
                dir.select("a").text(),
                Integer.parseInt(dir.select("a").attr("href").split("/")[7])
        )).collect(Collectors.toList()));
        return directions;
    }
}
