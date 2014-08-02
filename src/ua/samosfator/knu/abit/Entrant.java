package ua.samosfator.knu.abit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Entrant {
    private Direction direction;
    private String name;
    private String pk;
    private String pch;
    private String originals;
    private double score;

    public Entrant(Element row, Direction direction) {
        this.name = row.select("td:eq(1)").first().text();
        this.score = Double.parseDouble(row.select("td:eq(2)").first().text());
        this.pk = row.select("td:eq(5)").first().text();
        this.pch = row.select("td:eq(6)").first().text();
        this.originals = row.select("td:eq(7)").first().text();
        this.direction = direction;
    }

    public Entrant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public String getPK() {
        return pk;
    }

    public String getPCH() {
        return pch;
    }

    public String getOriginals() {
        return originals;
    }

    public Direction getDirection() {
        return direction;
    }

    public static List<Entrant> getEntrants(Direction direction, int wave) throws IOException {
        List<Entrant> entrants = new ArrayList<>();
        List<Entrant> recommended = new ArrayList<>();
        List<Entrant> rating = new ArrayList<>();

        Document doc = Jsoup.connect(direction.getWaveURL(wave)).timeout(0).get();
        Elements people = doc.select("table tr td:eq(1)");
        recommended.addAll(people.stream().map(p -> new Entrant(p.text())).collect(Collectors.toList()));

        doc = Jsoup.connect(direction.getURL()).timeout(0).get();
        Elements rowsWithRatingEntrants = doc.select("table tbody tr").not(".pozakonkurs, .rating_MO, .rating, .quota");
        rating.addAll(rowsWithRatingEntrants.stream().map(row -> new Entrant(row, direction)).collect(Collectors.toList()));

        for (Entrant recEntr : recommended)
            entrants.addAll(rating.stream().filter(ratEntr -> recEntr.getName().equals(ratEntr.getName())).collect(Collectors.toList()));
        return entrants;
    }
}
