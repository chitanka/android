package info.chitanka.app.ui;

import android.content.SharedPreferences;

import com.annimon.stream.Optional;
import com.folioreader.FolioReader;
import com.folioreader.model.locators.ReadLocator;
import com.folioreader.util.ReadLocatorListener;
import com.google.gson.Gson;

public class BookReader implements ReadLocatorListener, FolioReader.OnClosedListener {

    private final FolioReader folioReader;
    private final SharedPreferences sharedPreferences;
    private final Gson gson;
    private String filePath;

    public BookReader(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
        folioReader = FolioReader.get()
                .setReadLocatorListener(this)
                .setOnClosedListener(this);
    }

    public void readBook(String path) {
        filePath = path;
        Optional<ReadLocator> readPosition = getLastReadPosition();
        readPosition.ifPresentOrElse(readPosition1 -> folioReader.setReadLocator(readPosition1).openBook(path), () -> folioReader.openBook(path));
    }

    private Optional<ReadLocator> getLastReadPosition() {
        String jsonString = sharedPreferences.getString(filePath, "");
        if (jsonString.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(gson.fromJson(jsonString, ReadLocator.class));
    }

    @Override
    public void onFolioReaderClosed() {
    }

    @Override
    public void saveReadLocator(ReadLocator readLocator) {
        String readPositionJson = readLocator.toJson();
        sharedPreferences.edit().putString(filePath, readPositionJson).apply();
    }
}
