package util;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartGenerator {
    public static void generateBarChart(Map<String, Double> data, String fileName) throws IOException {
        CategoryChart chart = new CategoryChartBuilder()
                .width(900)
                .height(600)
                .title("Средние глобальные продажи по платформам (млн)")
                .xAxisTitle("Платформа")
                .yAxisTitle("Продажи")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setXAxisLabelRotation(45);

        List<String> categories = new ArrayList<>(data.keySet());
        List<Double> values = new ArrayList<>(data.values());

        chart.addSeries("Средние продажи", categories, values);

        BitmapEncoder.saveBitmap(chart, fileName, BitmapEncoder.BitmapFormat.PNG);
        System.out.println("Диаграмма сохранена: " + fileName + ".png");
    }
}