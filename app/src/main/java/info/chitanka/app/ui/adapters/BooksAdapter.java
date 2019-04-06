package info.chitanka.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.chitanka.app.Constants;
import info.chitanka.app.databinding.ListItemBookBinding;
import info.chitanka.app.mvp.models.Book;
import info.chitanka.app.ui.BookDetailsActivity;
import rx.subjects.PublishSubject;

/**
 * Created by nmp on 16-3-8.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private Context context;
    private List<Book> books = new ArrayList<>();
    private PublishSubject<Book> onWebClick = PublishSubject.create();

    public BooksAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBookBinding binding = ListItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);

        viewHolder.binding.tvWeb.setOnClickListener(v2 -> {
            if (viewHolder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                return;
            }

            Book book = books.get(viewHolder.getAdapterPosition());
            onWebClick.onNext(book);
        });

        return viewHolder;
    }

    public rx.Observable<Book> getOnWebClick() {
        return onWebClick.asObservable();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book, context);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        context = null;
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void addAll(List<Book> books) {
        this.books.addAll(books);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ListItemBookBinding binding;
        public ViewHolder(ListItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Book book, Context context) {
            binding.setBook(book);
            binding.cardView.setOnClickListener(view -> {
                Intent sendIntent = new Intent(context, BookDetailsActivity.class);
                sendIntent.putExtra(Constants.EXTRA_BOOK_ID, book.getId());
                context.startActivity(sendIntent);
            });
        }
    }
}
