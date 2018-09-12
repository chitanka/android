package info.chitanka.android.ui.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import butterknife.BindView;
import info.chitanka.android.R;
import info.chitanka.android.databinding.ListItemBookBinding;
import info.chitanka.android.mvp.models.Book;
import info.chitanka.android.mvp.models.NewBooksResult;
import info.chitanka.android.utils.DateUtils;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by nmp on 23.01.17.
 */

public class NewBooksAdapter extends AdvancedSectionedRecyclerViewAdapter<NewBooksAdapter.SectionViewHolder, BooksAdapter.ViewHolder> {

    private final Context context;
    LinkedTreeMap<String, List<NewBooksResult>> map;
    private PublishSubject<Book> onWebClick = PublishSubject.create();
    private PublishSubject<Book> onReadClick = PublishSubject.create();

    public NewBooksAdapter(LinkedTreeMap<String, List<NewBooksResult>> map, FragmentActivity activity) {
        this.map = map;
        this.context = activity;
    }

    @Override
    public int getGroupCount() {
        return map.keySet().size();
    }

    @Override
    public int getChildCount(int group) {
        List<NewBooksResult> textWorks = getBooks(group);
        return textWorks != null ? textWorks.size() : 0;
    }

    private List<NewBooksResult> getBooks(int keyPosition) {
        int i = 0;
        for (String key : map.keySet()) {
            if (i == keyPosition) {
                return map.get(key);
            }
            i++;
        }

        return null;
    }

    private String getDate(int keyPosition) {
        int i = 0;
        for (String key : map.keySet()) {
            if (i == keyPosition) {
                return DateUtils.getReadableDateWithTime(key, "yyy-MM-dd");
            }
            i++;
        }

        return "";
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_date_header, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public BooksAdapter.ViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        ListItemBookBinding binding = ListItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BooksAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder holder, int position, List<Object> payloads) {
        holder.tvHeader.setText(getDate(position));
    }

    public Observable<Book> getOnWebClick() {
        return onWebClick.asObservable();
    }

    public Observable<Book> getOnReadClick() {
        return onReadClick.asObservable();
    }

    @Override
    public void onBindChildViewHolder(BooksAdapter.ViewHolder holder, int belongingGroup, int position, List<Object> payloads) {
        NewBooksResult newTextWorksResults = getBooks(belongingGroup).get(position);
        Book book = newTextWorksResults.getBook();

        holder.binding.tvWeb.setOnClickListener(view1 -> {
            onWebClick.onNext(book);
        });

        holder.binding.tvRead.setOnClickListener(view -> {
            onReadClick.onNext(book);
        });

        holder.bind(book, context);
    }

    static class SectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_header)
        TextView tvHeader;

        public SectionViewHolder(View itemView) {
            super(itemView);
            tvHeader = (TextView) itemView.findViewById(R.id.tv_header);
        }
    }
}
