package info.chitanka.app.mvp.views;

import java.util.List;

import info.chitanka.app.mvp.models.TextWork;

/**
 * Created by nmp on 21.01.17.
 */

public interface TextWorksView extends BaseView {
    void presentTextWorks(List<TextWork> texts);
}
