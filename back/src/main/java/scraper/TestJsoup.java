package scraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestJsoup {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://www.google.com").get();
            System.out.println("✅ JSoup funciona correctamente!");
            System.out.println("Título de la página: " + doc.title());
        } catch (Exception e) {
            System.out.println("❌ Error con JSoup: " + e.getMessage());
        }
    }
}
