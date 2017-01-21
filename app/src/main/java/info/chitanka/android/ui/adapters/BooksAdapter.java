package info.chitanka.android.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.mvp.models.Book;
import info.chitanka.android.ui.BookDetailsActivity;
import info.chitanka.android.ui.dialogs.DownloadDialog;

/**
 * Created by nmp on 16-3-8.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private Context context;
    private final FragmentManager fragmentManager;
    private List<Book> books = new ArrayList<>();

    public BooksAdapter(Context context, List<Book> books, FragmentManager fragmentManager) {
        this.context = context;
        this.books = books;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvBookName.setText(book.getTitle());
        holder.tvDescription.setText((book.getAnnotation() != null ? book.getAnnotation() : ""));
        holder.tvBookCategory.setText(book.getCategory().getName());
        holder.tvBookAuthor.setText(book.getTitleAuthor());
        Glide.with(context).load(book.getCover()).fitCenter().crossFade().placeholder(R.drawable.ic_no_cover).into(holder.ivCover);

        holder.tvShare.setOnClickListener((view) -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, String.format(context.getString(R.string.share_text), book.getTitle(), book.getTitleAuthor()));
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        });

        holder.tvDownload.setOnClickListener((view) -> {
            DownloadDialog.newInstance(book).show(fragmentManager, DownloadDialog.TAG);
        });

        holder.cardView.setOnClickListener(v -> {
            Intent sendIntent = new Intent(context, BookDetailsActivity.class);
            sendIntent.putExtra(Constants.EXTRA_BOOK_ID, book.getId());
            context.startActivity(sendIntent);
        });
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.card_view)
        CardView cardView;

        @Bind(R.id.tv_name)
        TextView tvBookName;

        @Bind(R.id.tv_category)
        TextView tvBookCategory;

        @Bind(R.id.tv_author)
        TextView tvBookAuthor;

        @Bind(R.id.iv_cover)
        ImageView ivCover;

        @Bind(R.id.tv_share)
        TextView tvShare;

        @Bind(R.id.tv_description)
        TextView tvDescription;

        @Bind(R.id.tv_download)
        TextView tvDownload;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
