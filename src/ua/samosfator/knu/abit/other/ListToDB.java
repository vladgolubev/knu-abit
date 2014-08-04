package ua.samosfator.knu.abit.other;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Parse chosen entrants lists and put them in a sqlite database. Empty knu-abit.db in classpath is required
 */
public class ListToDB {
    public static void main(String[] args) throws Exception {
        String statement = "";
        HashSet<String> fac = new HashSet<>();
        HashSet<String> links = new HashSet<>();
        fac.add("http://www.univ.kiev.ua/ua/abit/2014/Lists/1/933");
        fac.add("http://www.univ.kiev.ua/ua/abit/2014/Lists/1/13659");

        for (String link : fac) {
            Document doc = Jsoup.connect(link).get();
            Elements anchor = doc.select("ul").last().select("a");
            links.addAll(anchor.stream().map(a -> "http://www.univ.kiev.ua" + a.attr("href")).collect(Collectors.toList()));
        }

        for (String link : links) {
            String tableName = "knu" + link.substring(link.length() - 6);
            Document doc = Jsoup.connect(link).get();
            Element table = doc.select("table").first();
            for (Element tr : table.select("tbody tr")) {
                if (!tr.className().equals("rating")) {
                    tr.remove();
                } else {
                    tr.remove();
                    break;
                }
            }
            int i = 0;
            for (Element tr : table.select("tbody tr")) {
                String name = tr.select("td").eq(1).text();
                double score = Double.parseDouble(tr.select("td").eq(2).text());
                boolean docs = !tr.select("td").eq(7).text().equals("—");

                statement += "INSERT OR REPLACE INTO " + tableName + " (name, score, docs) " + "VALUES (\"" +
                        name + "\" , " + score + " , \"" + docs + "\"); \n";
                System.out.println(tableName + ": " + i++);
            }
            i = 0;
            putToDatabase(statement, tableName);
        }

    }

    private static void putToDatabase(String statement, String tableName) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:knu-abit.db");
        c.setAutoCommit(false);
        Statement stmt = c.createStatement();

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName +
                " ( name TEXT PRIMARY KEY, score double NOT NULL, " +
                "docs boolean NOT NULL )");

        stmt.executeUpdate(statement);

        stmt.close();
        c.commit();
        c.close();
    }
}
