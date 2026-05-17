package model;
import com.opencsv.bean.CsvBindByName;

public class GameRecord {
    @CsvBindByName(column = "Name") private String name;
    @CsvBindByName(column = "Platform") private String platform;
    @CsvBindByName(column = "Year") private String year;
    @CsvBindByName(column = "Genre") private String genre;
    @CsvBindByName(column = "Publisher") private String publisher;
    @CsvBindByName(column = "NA_Sales") private double naSales;
    @CsvBindByName(column = "EU_Sales") private double euSales;
    @CsvBindByName(column = "JP_Sales") private double jpSales;
    @CsvBindByName(column = "Other_Sales") private double otherSales;
    @CsvBindByName(column = "Global_Sales") private double globalSales;

    public String getName() { return name; }
    public String getPlatform() { return platform; }
    public String getYear() { return year; }
    public String getGenre() { return genre; }
    public String getPublisher() { return publisher; }
    public double getNaSales() { return naSales; }
    public double getEuSales() { return euSales; }
    public double getJpSales() { return jpSales; }
    public double getOtherSales() { return otherSales; }
    public double getGlobalSales() { return globalSales; }
}