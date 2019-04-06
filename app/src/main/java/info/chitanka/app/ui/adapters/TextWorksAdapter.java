package info.chitanka.app.ui.adapters;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import info.chitanka.app.R;
import info.chitanka.app.databinding.ListItemTextworkBinding;
import info.chitanka.app.mvp.models.TextWork;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by nmp on 21.01.17.
 */

public class TextWorksAdapter extends RecyclerView.Adapter<TextWorksAdapter.ViewHolder> {
    private final Resources res;
    private List<TextWork> textWorks;
    private PublishSubject<TextWork> onWebClick = PublishSubject.create();

    public TextWorksAdapter(List<TextWork> textWorks, FragmentActivity activity) {
        this.textWorks = textWorks;
        this.res = activity.getResources();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemTextworkBinding binding = ListItemTextworkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);

        viewHolder.binding.tvWeb.setOnClickListener(view1 -> {
            if (viewHolder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                return;
            }

            TextWork textWork = textWorks.get(viewHolder.getAdapterPosition());
            onWebClick.onNext(textWork);
        });
        return viewHolder;
    }

    public Observable<TextWork> getOnWebClick() {
        return onWebClick.asObservable();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextWork textWork = textWorks.get(position);
        holder.bind(textWork, res);
    }

    @Override
    public int getItemCount() {
        return textWorks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ListItemTextworkBinding binding;

        public ViewHolder(ListItemTextworkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TextWork textWork, Resources res) {

            binding.setTextWork(textWork);
            binding.tvRating.setText(res.getString(R.string.item_text_work_rating, textWork.getRating(), textWork.getVotes()));
            if (textWork.getAuthors() != null && textWork.getAuthors().size() > 0) {
                binding.tvAuthorName.setText(res.getString(R.string.item_text_work_author, textWork.getAuthors().get(0).getName()));
            }
        }
    }
}
