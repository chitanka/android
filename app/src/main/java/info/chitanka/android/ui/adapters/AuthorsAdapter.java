package info.chitanka.android.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.mvp.models.Author;
import info.chitanka.android.mvp.models.SearchTerms;
import info.chitanka.android.ui.BooksActivity;

/**
 * Created by nmp on 16-3-13.
 */
public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.ViewHolder> {
    private final Context context;
    private final JSONObject countries;
    private List<Author> authors = new ArrayList<>();

    public AuthorsAdapter(Context context, List<Author> authors) {
        this.context = context;
        this.authors = authors;
        this.countries = loadJSONFromAsset();
    }

    public JSONObject loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_author, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Author author = authors.get(position);

        String countryAbbr = TextUtils.isEmpty(author.getCountry()) ? "" : author.getCountry().toUpperCase();
        if(!TextUtils.isEmpty(countryAbbr) && countries != null && countries.has(countryAbbr)) {
            try {
                holder.tvAuthorCountry.setText(countries.getString(countryAbbr));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        holder.tvAuthorName.setText(author.getName());
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BooksActivity.class);
            intent.putExtra(Constants.EXTRA_SEARCH_TERM, SearchTerms.AUTHOR.toString());
            intent.putExtra(Constants.EXTRA_TITLE, author.getName());
            intent.putExtra(Constants.EXTRA_SLUG, author.getSlug());
           context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    public void addAll(List<Author> authors) {
        this.authors.addAll(authors);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.container_authors)
        LinearLayout cardView;

        @Bind(R.id.tv_author_country)
        TextView tvAuthorCountry;

        @Bind(R.id.tv_author_name)
        TextView tvAuthorName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}