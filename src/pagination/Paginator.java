package pagination;

import java.util.ArrayList;
import java.util.List;

public class Paginator {
    private int maxRowsBeforePageEnd = 9;
    private int currentPage = 1;
    private List<String> rows = new ArrayList<>();

    public Paginator(int maxRowsBeforePageEnd) {
        this.maxRowsBeforePageEnd = maxRowsBeforePageEnd;
    }

    public void setRows(List<String> rows) {
        this.rows = rows;
    }

    public String loadPage(int page){

        StringBuilder sb = new StringBuilder();
        if(maxRowsBeforePageEnd * (page-1) >= rows.size()) {
            return "End of table reached";
        }

        for (int i = maxRowsBeforePageEnd * (page-1); i < Math.min(maxRowsBeforePageEnd * page , rows.size()); i++){
            sb.append(rows.get(i)).append("\n");
        }
        sb.append("Type next for next page, or prev for previous page.");
        currentPage = page;
        return sb.toString();
    }

    public void clearPaginator(){
        currentPage = 1;
        rows = new ArrayList<>();
    }
    public int getCurrentPage() { return currentPage; }
}
