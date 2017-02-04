package info.chitanka.android.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.chitanka.android.Constants;
import info.chitanka.android.databinding.ListItemBookBinding;
import info.chitanka.android.mvp.models.Book;
import info.chitanka.android.ui.BookDetailsActivity;
import info.chitanka.android.ui.services.DownloadService;
import rx.subjects.PublishSubject;

/**
 * Created by nmp on 16-3-8.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private Context context;
    private final FragmentManager fragmentManager;
    private List<Book> books = new ArrayList<>();
    private PublishSubject<Book> onWebClick = PublishSubject.create();

    public BooksAdapter(Context context, List<Book> books, FragmentManager fragmentManager) {
        this.context = context;
        this.books = books;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBookBinding binding = ListItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);

        viewHolder.binding.cardView.setOnClickListener(v -> {

        });

        viewHolder.binding.tvWeb.setOnClickListener(v2 -> {
            Book book = books.get(viewHolder.getAdapterPosition());
            onWebClick.onNext(book);
        });

        viewHolder.binding.tvRead.setOnClickListener(view12 -> {
            Book book = books.get(viewHolder.getAdapterPosition());
            Intent intent = new Intent(context, DownloadService.class);
            intent.putExtra(Constants.EXTRA_BOOK_FORMAT, "epub");
            intent.putExtra(Constants.EXTRA_BOOK_ID, book.getId());
            context.startService(intent);
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
