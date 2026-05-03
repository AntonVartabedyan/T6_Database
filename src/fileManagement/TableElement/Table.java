package fileManagement.TableElement;

import java.util.List;

public class Table {
    private String tableName;
    private int columnCount;
    private List<String> types;
    private List<String> headerValues;
    private List<List<String>> values;

    public Table(String tableName, int columnCount, List<String> types, List<String> headerValues, List<List<String>> values) {
        this.tableName = tableName;
        this.columnCount = columnCount;
        this.types = types;
        this.headerValues = headerValues;
        this.values = values;
    }

    public void setValues(List<List<String>> values) {
        this.values = values;
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(tableName).append('\n');
        sb.append(columnCount).append('\n');
        for (String type: types){
            sb.append(type).append(" ");
        }
        sb.append("\n");
        for (String header: headerValues){
            sb.append(header).append(" ");
        }
        sb.append("\n");
        for (List<String> row: values){
            for (String col: row){
                sb.append(col).append("; ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getRow(int rowNum){
        final StringBuilder sb = new StringBuilder();
        for(String col: values.get(rowNum)){
            sb.append(col).append("; ");
        }
        return sb.toString();
    }

    public String getTypesRow(){
        final StringBuilder sb = new StringBuilder();
        for(String col: types){
            sb.append(col).append(" ");
        }
        return sb.toString();
    }
}
