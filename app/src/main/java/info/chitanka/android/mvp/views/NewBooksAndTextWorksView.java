package info.chitanka.android.mvp.views;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import info.chitanka.android.mvp.models.NewBooksResult;
import info.chitanka.android.mvp.models.NewTextWorksResult;

/**
 * Created by joro on 23.01.17.
 */

public interface NewBooksAndTextWorksView extends BaseView {
    void presentNewBooksAndTextWorks(LinkedTreeMap<String, List<NewBooksResult>> books, LinkedTreeMap<String, List<NewTextWorksResult>> textWorks);
}
