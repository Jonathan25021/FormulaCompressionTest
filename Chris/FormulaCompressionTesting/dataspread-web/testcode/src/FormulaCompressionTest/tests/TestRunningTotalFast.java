package FormulaCompressionTest.tests;

import org.zkoss.zss.model.CellRegion;
import org.zkoss.zss.model.sys.dependency.Ref;

import java.util.Random;

public class TestRunningTotalFast extends BaseTest {

    private final int rows;
    private int answer;

    public TestRunningTotalFast(final int rows) {
        super();
        this.rows = rows;
    }

    @Override
    public void init() {
        answer = 20;

        Random random = new Random(7);

        sheet.setDelayComputation(true);

        sheet.getCell(0, 0).setValue(random.nextInt(1000) + 100);
        sheet.getCell(0, 1).setFormulaValue("A1");

        for (int i = 1; i < rows; i++) {
            int num = random.nextInt(1000);
            sheet.getCell(i, 0).setValue(num);
            sheet.getCell(i, 1).setFormulaValue("A" + (i + 1) + " + B" + (i));
            answer += num;
        }

        // refreshDepTable();
        loadBatch();
        sheet.setDelayComputation(false);
    }

    @Override
    public void touchAll() {
        double result = 0.0;
        for (int i = rows - 1; i >= 0; i--) {
            Object v = sheet.getCell(i, 1).getValue();
            result += (double) v;
        }
        System.out.println(result);
    }

    @Override
    public boolean verify() {
        try {
            Object value_raw = sheet.getCell(rows - 1, 1).getValue();
            double value = (double) value_raw;
            return Math.abs(value - answer) <= 1e-6;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Ref getCellToUpdate() {
        return sheet.getCell(0, 0).getRef();
    }

    @Override
    public void updateCell() {
        sheet.getCell(0, 0).setValue(20);
    }

    @Override
    public CellRegion getRegion() {
        return new CellRegion(0, 0, rows - 1, 1);
    }

    @Override
    public String toString() {
        return "TestRunningTotalFast" + rows;
    }
}