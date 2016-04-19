package info.chitanka.android.api;

import info.chitanka.android.mvp.models.AuthorBooks;
import info.chitanka.android.mvp.models.Authors;
import info.chitanka.android.mvp.models.BookDetails;
import info.chitanka.android.mvp.models.Categories;
import info.chitanka.android.mvp.models.CategoryBooks;
import info.chitanka.android.mvp.models.SearchBooks;
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

    @GET("/author/{slug}/books.json")
    Observable<AuthorBooks> getAuthorBooks(@Path("slug") String slug);

    @GET("/book/{id}.json")
    Observable<BookDetails> getBookDetails(@Path("id") int id);

    @GET("/authors/first-name/-.json/{page}")
    Observable<Authors> getAuthors(@Path("page") int page, @Query("pageSize") int pageSize);
}
