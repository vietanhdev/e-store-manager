package com.example.store.payload.supplier_management.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SearchSuppliersRequest {
    
    @NotNull
    @Min(0)
    private Integer draw;

    @NotNull
    @Min(0)
    private Integer start;

    @NotNull
    @Min(1)
    private Integer length;

    @NotNull
    private Search search;

    public Integer getDraw(){
        return draw;
    }

    public Integer getStart(){
        return start;
    }

    public Integer getLength(){
        return length;
    }

    public Search getSearch(){
        return search;
    }

}