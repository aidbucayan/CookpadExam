package com.bucayan.adrian.cookpadexam.Model;

import java.util.List;

/**
 * @author Adrian Bucayan on 12/16/16.
 */

public class MediaResponse {

    private Pagination pagination;
    private Meta meta;
    private List<ImageData> data;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<ImageData> getData() {
        return data;
    }

    public void setData(List<ImageData> data) {
        this.data = data;
    }

}
