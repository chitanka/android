package com.nmp90.chitankainfo.parser;

import com.nmp90.chitankainfo.mvp.models.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by joro on 16-3-8.
 */
public class ChitankaParser {
    public Observable<List<Book>> searchBooks(String q) {
        return Observable.create(new Observable.OnSubscribe<List<Book>>() {
            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {
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

                    books.add(new Book(title, author, category, imageUrl, ""));
                }

                subscriber.onNext(books);
                subscriber.onCompleted();
            }
        });

    }

}
