package ar.edu.itba.paw.models;

import java.util.ArrayList;
import java.util.List;

public class PagedContent<T>{
    private final List<T> elements;
    private final int currentPage;
    private final int pageSize;
    private final int totalCount;
    public static <E> PagedContent<E> emptyPagedContent(){
        return new PagedContent<>(new ArrayList<>(),0,0,0);
    }
    public PagedContent(List<T> elements, int currentPage, int pageSize, int totalCount) {
        if(pageSize<0 || currentPage<0 || totalCount<0 || elements==null){
            throw new IllegalArgumentException();
        }
        this.elements = elements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public List<T> getElements() {
        return elements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }
    public int getStartNumber(){
        return currentPage*pageSize;
    }
    public int getEndNumber(){
        int start = getStartNumber();
        return Math.min(start+pageSize-1, totalCount-1);
    }

    public int getTotalPages(){
        return (int) Math.ceil( (double) totalCount /pageSize);
    }
}
