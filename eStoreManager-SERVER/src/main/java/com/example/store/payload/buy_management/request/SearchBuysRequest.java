package com.example.store.payload.buy_management.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SearchBuysRequest {
    
    @NotNull
    @Min(0)
    private Long draw;

    @NotNull
    @Min(0)
    private Long start;

    @NotNull
    @Min(1)
    private Long length;

    @NotNull
    private Search search;

    public Long getDraw(){
        return draw;
    }

    public Long getStart(){
        return start;
    }

    public Long getLength(){
        return length;
    }

    public Search getSearch(){
        return search;
    }

}