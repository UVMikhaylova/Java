import model.GameRecord;
import service.CsvParserService;
import service.DatabaseService;
import util.ChartGenerator;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String csvPath = "games.csv";
        CsvParserService parser = new CsvParserService();
        DatabaseService db = new DatabaseService();

        try {
            System.out.println("1. Считывание CSV");
            List<GameRecord> games = parser.parse(csvPath);
            System.out.println("Загружено записей: " + games.size());

            System.out.println("\n2. Сохранение в SQLite");
            db.initAndSave(games);

            System.out.println("\n3. Результаты запросов:");

            Map<String, Double> avgSales = db.getAvgGlobalSalesByPlatform();
            System.out.println("Средние продажи по платформам");
            avgSales.forEach((k, v) -> System.out.printf("  %-15s: %.2f млн%n", k, v));

            System.out.println("\nИгра с самым высоким показателем продаж в Европе за 2000 год");
            System.out.println("  " + db.getTopEuGame2000());

            System.out.println("\nИгра, созданная в промежутке с 2000 по 2006 год с самым высоким показателем продаж в Японии из жанра спортивных игр");
            System.out.println("  " + db.getTopJpSports2000_2006());

            System.out.println("\n4. Построение графика");
            ChartGenerator.generateBarChart(avgSales, "platform_sales_chart");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}