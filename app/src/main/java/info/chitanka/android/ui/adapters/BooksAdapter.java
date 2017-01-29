package info.chitanka.android.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.folioreader.activity.FolioActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.mvp.models.Book;
import info.chitanka.android.ui.BookDetailsActivity;
import info.chitanka.android.ui.dialogs.DownloadDialog;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvDownload.setOnClickListener(view1 -> {
            Book book = books.get(viewHolder.getAdapterPosition());
            DownloadDialog.newInstance(book.getTitle(), book.getDownloadUrl(), book.getFormats()).show(fragmentManager, DownloadDialog.TAG);
        });

        viewHolder.cardView.setOnClickListener(v -> {
            Book book = books.get(viewHolder.getAdapterPosition());
            Intent sendIntent = new Intent(context, BookDetailsActivity.class);
            sendIntent.putExtra(Constants.EXTRA_BOOK_ID, book.getId());
            context.startActivity(sendIntent);
        });

        viewHolder.tvWeb.setOnClickListener(v2 -> {
            Book book = books.get(viewHolder.getAdapterPosition());
            onWebClick.onNext(book);
        });

        viewHolder.tvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FolioActivity.class);
                intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_TYPE, FolioActivity.EpubSourceType.SD_CARD);
                intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_PATH, "/storage/emulated/0/Download/test.epub");
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }

    public rx.Observable<Book> getOnWebClick() {
        return onWebClick.asObservable();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(context, book);
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

        @Bind(R.id.tv_web)
        TextView tvWeb;

        @Bind(R.id.tv_description)
        TextView tvDescription;

        @Bind(R.id.tv_download)
        TextView tvDownload;

        @Bind(R.id.tv_read)
        TextView tvRead;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Context context, Book book) {
            tvBookName.setText(book.getTitle());
            tvDescription.setText((book.getAnnotation() != null ? book.getAnnotation() : ""));
            if (book.getCategory() != null && !TextUtils.isEmpty(book.getCategory().getName())) {
                tvBookCategory.setText(book.getCategory().getName());
            }
            tvBookAuthor.setText(book.getTitleAuthor());

            if (book.getFormats() == null || book.getFormats().size() == 0) {
                tvDownload.setVisibility(View.GONE);
            } else {
                tvDownload.setVisibility(View.VISIBLE);
            }
            Glide.with(context).load(book.getCover()).fitCenter().crossFade().placeholder(R.drawable.ic_no_cover).into(ivCover);
        }
    }
}
