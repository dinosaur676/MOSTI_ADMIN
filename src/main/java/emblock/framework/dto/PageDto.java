package emblock.framework.dto;

import java.util.ArrayList;
import java.util.List;


public class PageDto<T>  {
    private int totalCount;
    private List<T> items;
    private int totalPages;
    private int currentPage;

    public PageDto() {
        this.items = new ArrayList<>();
    }

    public static PageDto createEmpty() {
        PageDto<?> emptyPage = new PageDto<>();
        emptyPage.totalCount = 0;
        emptyPage.totalPages = 1;
        emptyPage.currentPage = 1;

        return emptyPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
