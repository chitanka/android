package com.nmp90.chitankainfo.api;

import com.nmp90.chitankainfo.mvp.models.Categories;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by nmp on 16-3-15.
 */
public interface ChitankaApi {
    @GET("/books.json")
    Observable<Categories> getCategories();
}
