package com.nmp90.chitankainfo.api;

import com.nmp90.chitankainfo.mvp.models.Author;
import com.nmp90.chitankainfo.mvp.models.AuthorBooks;
import com.nmp90.chitankainfo.mvp.models.BookDetails;
import com.nmp90.chitankainfo.mvp.models.Categories;
import com.nmp90.chitankainfo.mvp.models.CategoryBooks;
import com.nmp90.chitankainfo.mvp.models.SearchBooks;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nmp on 16-3-15.
 */
public interface ChitankaApi {
    @GET("/books.json")
    Observable<Categories> getCategories();

    @GET("/books/category/{slug}.json/{page}")
    Observable<CategoryBooks> getBooksForCategory(@Path("slug") String slugName, @Path("page") int page);

    @GET("/books/search.json")
    Observable<SearchBooks> searchBooks(@Query("q") String title);

    @GET("/authors/search.json")
    Observable<List<Author>> searchAuthors(@Query("q") String name);

    @GET("/author/{slug}/books.json")
    Observable<AuthorBooks> getAuthorBooks(@Path("slug") String slug);

    @GET("/book/{id}.json")
    Observable<BookDetails> getBookDetails(@Path("id") int id);
}
