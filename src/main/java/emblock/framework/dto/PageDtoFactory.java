package emblock.framework.dto;

import java.util.List;

public class PageDtoFactory {
    static <T> PageDto<T> createByList(List<T> 목록, int pageNo) {
        if (목록 == null || 목록.size() == 0) return PageDto.createEmpty();

        int totalItems = 목록.size();
        int pageSize = 20;
        int totalPages = totalItems / pageSize;
        if (totalItems % pageSize > 0) {
            totalPages++;
        }

        if (totalPages < pageNo)
            pageNo = totalPages;

        PageDto<T> pageDto = new PageDto<>();
        pageDto.setTotalCount(totalItems);
        pageDto.setTotalPages(totalPages);
        pageDto.setCurrentPage(pageNo);
        pageDto.setItems(getPageList(목록, pageSize, pageNo));

        return pageDto;

    }

    static <T> List<T> getPageList(List<T> list, int pageSize, int selectedPage) {
        int totalCount = list.size();
        int pageNo = selectedPage - 1;
        int fromIndex = pageNo * pageSize;

        int toIndex = ((pageNo + 1) * pageSize);
        if (toIndex > totalCount){
            toIndex = totalCount;
        }
        return list.subList(fromIndex, toIndex);
    }
}
