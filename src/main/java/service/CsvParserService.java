package service;
import com.opencsv.bean.CsvToBeanBuilder;
import model.GameRecord;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvParserService {
    public List<GameRecord> parse(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return new CsvToBeanBuilder<GameRecord>(reader)
                    .withType(GameRecord.class)
                    .build()
                    .parse();
        }
    }
}