package sheets;

import java.io.IOException;
import java.util.function.Function;
import com.github.jferard.fastods.TableRowImpl;
import com.github.jferard.fastods.Table;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFRow;
import creator.Creatable;

public class Rate implements Creatable {

  private static final String CREATE_STR = "$A$1 * B%d";

  @Override
  public void createExcelSheet(SXSSFSheet sheet, int rows, Function<Integer, Number> valueGenerator) {
    SXSSFRow row = sheet.createRow(0);
    row.createCell(0).setCellValue((double) valueGenerator.apply(0));
    row.createCell(1).setCellValue((double) valueGenerator.apply(0));
    row.createCell(2).setCellFormula(String.format(CREATE_STR, 1));
    for (int r = 1; r < rows; r++) {
      row = sheet.createRow(r);
      row.createCell(1).setCellValue((double) valueGenerator.apply(r));
      row.createCell(2).setCellFormula(String.format(CREATE_STR, r + 1));
    }
  }

  @Override
  public void createCalcSheet(Table sheet, int rows, Function<Integer, Number> valueGenerator) throws IOException {
    TableRowImpl row = sheet.getRow(0);
    row.getOrCreateCell(0).setFloatValue((double) valueGenerator.apply(0));
    row.getOrCreateCell(1).setFloatValue((double) valueGenerator.apply(0));
    row.getOrCreateCell(2).setFormula(String.format(CREATE_STR, 1));
    for (int r = 1; r < rows; r++) {
      row = sheet.getRow(r);
      row.getOrCreateCell(0).setFloatValue(valueGenerator.apply(r));
      row.getOrCreateCell(1).setFormula(String.format(CREATE_STR, r + 1));
    }
  }

}
