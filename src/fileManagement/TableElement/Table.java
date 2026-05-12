package fileManagement.TableElement;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String tableName;
    private int columnCount;
    private List<String> types;
    private List<String> headerValues;
    private List<List<String>> values;

    public Table(String tableName){
        this.tableName = tableName;
        this.columnCount = 0;
        this.types = new ArrayList<>();
        this.headerValues = new ArrayList<>();
        this.values = new ArrayList<>();
    }



    public Table(String tableName, int columnCount, List<String> types, List<String> headerValues, List<List<String>> values) {
        this.tableName = tableName;
        this.columnCount = columnCount;
        this.types = new ArrayList<>();
        this.types.addAll(types);
        this.headerValues = new ArrayList<>();
        this.headerValues.addAll(headerValues);
        this.values = new ArrayList<>();
        this.values.addAll(values);
    }

    public void addColumn(String columnName, String columnType){
        this.columnCount+=1;
        this.headerValues.add(columnName);
        this.types.add(columnType);

        for (List<String> value: values){
            value.add("NULL");
        }
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
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

    public void removeValue(int row){
        this.values.remove(row);
    }

    public void setValue(int rowIndex, int valueIndex, String valReplacement){
        this.values.get(rowIndex).set(valueIndex,valReplacement);
    }

    public void setRow(List<String> values){
        this.values.add(values);
    }

    public void setTableName (String tableName){
        this.tableName = tableName;
    }

    public void setValues(List<List<String>> values) {
        this.values = values;
    }

    public List<List<String>> getValues() {
        return values;
    }

    public List<String> getHeaderValues() {
        return headerValues;
    }

    public String getTableName() {
        return tableName;
    }

    public int getColumnCount() {
        return columnCount;
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

    public List<String> getTypesRowList(){
        List<String> types = new ArrayList<>();
        for(String col: this.types){
            types.add(col);
        }
        return types;
    }
}
