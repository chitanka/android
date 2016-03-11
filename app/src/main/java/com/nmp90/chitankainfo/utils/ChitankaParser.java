package com.nmp90.chitankainfo.utils;

import com.nmp90.chitankainfo.mvp.models.Author;
import com.nmp90.chitankainfo.mvp.models.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by joro on 16-3-8.
 */
public class ChitankaParser {
    public Observable<List<Author>> searchAuthors(String q) {
        return Observable.create(subscriber -> {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://chitanka.info/search?q=" + q).get();
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }

            List<Author> authors = new ArrayList<>();
            Elements newsHeadlines = doc.select("dl[itemtype=http://schema.org/Person]");
            for(Element element : newsHeadlines) {
                Timber.d(element.select("a[itemProp=url]").first().absUrl("href"));
            }

        });
    }


    public Observable<List<Book>> searchBooks(String q) {
        return Observable.create(subscriber -> {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://chitanka.info/books/search?q=" + q).get();
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }

            List<Book> books = new ArrayList<Book>();
            Elements newsHeadlines = doc.select(".book-media");
            for(Element element : newsHeadlines) {
                String imageUrl = element.select("img").first().absUrl("src");
                String title = element.select(".media-body").select("a").first().select("i").first().text();
                String category = element.select("div.bookcat").select("a").first().text();
                String author = element.select("div.bookauthor").select("a").first().text();
                String downloadUrl = element.select("div.download-links").select("a.dl-epub").first().absUrl("href");

                books.add(new Book(title, author, category, imageUrl, "", downloadUrl));
            }

            subscriber.onNext(books);
            subscriber.onCompleted();
        });
    }
}

