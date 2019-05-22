package info.chitanka.app.mvp.presenters.my;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import info.chitanka.app.mvp.presenters.BasePresenter;
import info.chitanka.app.mvp.views.MyLibraryView;
import info.chitanka.app.ui.BookReader;
import info.chitanka.app.utils.FileUtils;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by joro on 23.01.17.
 */

public class MyLibraryPresenterImpl extends BasePresenter<MyLibraryView> implements MyLibraryPresenter {

    private static final String EPUB = "epub";

    private final BookReader bookReader;
    private CompositeSubscription compositeSubscription;

    public MyLibraryPresenterImpl(BookReader bookReader) {
        this.bookReader = bookReader;
    }

    @Override
    public void startPresenting() {
        compositeSubscription = new CompositeSubscription();
        getView().requestPermissionFromUser();
    }

    @Override
    public void stopPresenting() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void setView(MyLibraryView view) {
        this.view = new WeakReference<MyLibraryView>(view);
    }

    @Override
    public void readFiles() {
        if (viewExists()) {
            Subscription subscription = Single.fromCallable(() -> Arrays.asList(FileUtils.listChitankaFiles()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(files -> getView().displayFilesList(files));
            compositeSubscription.add(subscription);
        }
    }

    @Override
    public void readBook(String path) {
        if(path.contains(EPUB)) {
            bookReader.readBook(path);
        } else {
            getView().readFile(path);
        }
    }
}
