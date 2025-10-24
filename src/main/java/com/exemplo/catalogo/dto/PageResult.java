package com.exemplo.catalogo.dto;

import java.util.List;

public class PageResult<T> {
    public java.util.List<T> content;
    public int page;
    public int size;
    public long total;

    public PageResult(java.util.List<T> content, int page, int size, long total) {
        this.content = content; this.page = page; this.size = size; this.total = total;
    }
}
