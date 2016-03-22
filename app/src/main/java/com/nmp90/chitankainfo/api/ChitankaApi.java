package com.nmp90.chitankainfo.api;

import com.nmp90.chitankainfo.mvp.models.Author;
import com.nmp90.chitankainfo.mvp.models.AuthorBooks;
import com.nmp90.chitankainfo.mvp.models.Categories;
import com.nmp90.chitankainfo.mvp.models.CategoryBooks;
import com.nmp90.chitankainfo.mvp.models.SearchBooks;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by nmp on 16-3-15.
 */
public interface ChitankaApi {
    @GET("/books.json")
    Observable<Categories> getCategories();

    @GET("/books/category/{slug}.json/{page}")
    Observable<CategoryBooks> getBooksForCategory(@Path("slug") String slugName, @Path("page") int page);

    @GET("/books/search.json?q={title}")
    Observable<SearchBooks> searchBooks(@Path("title") String title);

    @GET("/authors/search.json?q={name}")
    Observable<List<Author>> searchAuthors(@Path("name") String name);

    @GET("/author/{slug}/books.json")
    Observable<AuthorBooks> getAuthorBooks(@Path("slug") String slug);

}
