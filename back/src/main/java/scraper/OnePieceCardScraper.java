package scraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class OnePieceCardScraper {
    private static final String BASE_URL = "https://en.onepiece-cardgame.com";
    private static final String CARD_LIST_URL = BASE_URL + "/cardlist/";
    private static final String RESULTS_DIR= "results";


    public static void main(String[] args) {
        try {
            List<Map<String, Object>> allCardsData = new ArrayList<>();
            List<Map<String, String>> seriesOptions = getSeriesOptions();
            for (Map<String, String> option : seriesOptions) {
                String seriesValue = option.get("value");
                System.out.println("🔍 Descargando datos de la serie: " + option.get("text"));
    
                String htmlContent = fetchSeriesData(seriesValue);
                if (htmlContent != null) {
                    List<Map<String, Object>> cardsData = extractCardData(htmlContent, seriesValue);
                    allCardsData.addAll(cardsData);
                }
            }
            saveResultsAsJson(allCardsData);
            System.out.println("✅ Scraper finalizado. Datos guardados en 'results/card_data.json'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Map<String, String>> getSeriesOptions() throws IOException{
        Document doc = Jsoup.connect(CARD_LIST_URL).data ("series", "").post();
        List <Map<String, String>> options  = new ArrayList<>();
        
        Element seriesSelect = doc.selectFirst ("select#series");
        if (seriesSelect !=null) {

            for(Element option :seriesSelect.select("option")){
                String value = option.attr("value").trim();
                String text = option.text().replaceAll("\"", "").trim();
                if (!value.isEmpty()&& value.matches("\\d+")) {
                    options.add(Map.of("value",value, "text",text));
                }
            }
        }
        return options;
    }
    
    private static String fetchSeriesData (String seriesValue) throws IOException{
        Document doc=Jsoup.connect(CARD_LIST_URL).data("series",seriesValue).post();
        
        return doc.html();
    }

    private static List<Map<String, Object>> extractCardData(String htmlContect, String seriesId) {
        Document doc = Jsoup.parse(htmlContect);
        List<Map<String, Object>> cards = new ArrayList<>();
        
        for (Element cardElement : doc.select("dl.modalCol")) {
            Map<String, Object> cardData = new HashMap<>();
            cardData.put("name", escapeQuotes(getText(cardElement, ".cardName")));
            cardData.put("id", getSpanText(cardElement, ".infoCol", 0));
            cardData.put("rarity", getSpanText(cardElement, ".infoCol", 1));
            cardData.put("type", getSpanText(cardElement, ".infoCol", 2));
            cardData.put("attribute", getText(cardElement, ".attribute i"));
            cardData.put("power", getNumber(cardElement, ".power"));
            cardData.put("counter", getNumber(cardElement, ".counter"));
            cardData.put("cost", getNumber(cardElement, ".cost")); 
            cardData.put("color", getText(cardElement, ".color"));
            cardData.put("card_type", getText(cardElement, ".feature"));
            cardData.put("effect", escapeQuotes(getText(cardElement, ".text")));
            cardData.put("series_id", seriesId);
            Element imagElement = cardElement.selectFirst(".frontCol img");
            if (imagElement != null) {
                String imageUrl = BASE_URL + imagElement.attr("data-src").replace("..", "");
                cardData.put("image_url", imageUrl);
                cardData.put("alternate_art", imageUrl.contains("_p") ? "true" : "false");
            }
            cards.add(cardData);
        }
        return cards;
    }
    private static String getText(Element element, String selector) {
        Element found = element.selectFirst(selector);
        return found != null ? found.text().trim() : "";
    }
    
    private static int getNumber(Element element, String selector) {
        // Extrae el texto del selector
        String text = getText(element, selector);
    
        // Elimina todo lo que no sea un dígito
        text = text.replaceAll("\\D", "");
    
        // Si el texto es vacío o "-" (lo que significa que no hay un valor numérico válido)
        if (text.equals("-") || text.isEmpty()) {
            text = "0";
        }
    
        // Convierte el texto a un número entero y retorna el valor
        return Integer.parseInt(text);
    }

    private static String getSpanText(Element element, String selector, int index) {
        Elements spans = element.select(selector + " span");
        return spans.size() > index ? spans.get(index).text().trim() : "";
    }
    
    private static String escapeQuotes(String value) {
        if (value != null) {
            return value.replace("\"", "\\\"");  // Escapar las comillas dobles
        }
        return value;
    }
    
    private static void saveResultsAsJson(List<Map<String, Object>> allCardsData) throws IOException {
        File resultsDir = new File(RESULTS_DIR);
        if (!resultsDir.exists()) resultsDir.mkdir();
    
        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("[\n");
    
        for (int i = 0; i < allCardsData.size(); i++) {
            Map<String, Object> card = allCardsData.get(i);
            jsonOutput.append("  {\n");
            int count = 0;
            for (Map.Entry<String, Object> entry : card.entrySet()) {
                jsonOutput.append("    \"").append(entry.getKey()).append("\": ");
                // Si el valor es un número, no lo ponemos entre comillas
                if (entry.getValue() instanceof Number) {
                    jsonOutput.append(entry.getValue());
                } else {
                    jsonOutput.append("\"").append(entry.getValue()).append("\"");
                }
                if (count < card.size() - 1) jsonOutput.append(",");
                jsonOutput.append("\n");
                count++;
            }
            jsonOutput.append("  }");
            if (i < allCardsData.size() - 1) jsonOutput.append(",");
            jsonOutput.append("\n");
        }
    
        jsonOutput.append("]");
    
        Files.write(Paths.get(RESULTS_DIR + "/card_data.json"), jsonOutput.toString().getBytes());
    }
}
    