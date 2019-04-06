package info.chitanka.app.ui;

import android.content.SharedPreferences;

import com.annimon.stream.Optional;
import com.folioreader.FolioReader;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.util.ReadPositionListener;
import com.google.gson.Gson;

public class BookReader implements ReadPositionListener, FolioReader.OnClosedListener {

    private final FolioReader folioReader;
    private final SharedPreferences sharedPreferences;
    private final Gson gson;
    private String filePath;

    public BookReader(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
        folioReader = FolioReader.get()
                .setReadPositionListener(this)
                .setOnClosedListener(this);
    }

    public void readBook(String path) {
        filePath = path;
        Optional<ReadPosition> readPosition = getLastReadPosition();
        readPosition.ifPresentOrElse(readPosition1 -> folioReader.setReadPosition(readPosition1).openBook(path), () -> folioReader.openBook(path));
    }

    private Optional<ReadPosition> getLastReadPosition() {
        String jsonString = sharedPreferences.getString(filePath, "");
        if (jsonString.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(gson.fromJson(jsonString, ReadPositionImpl.class));
    }

    @Override
    public void saveReadPosition(ReadPosition readPosition) {
        String readPositionJson = readPosition.toJson();
        sharedPreferences.edit().putString(filePath, readPositionJson).apply();
    }

    @Override
    public void onFolioReaderClosed() {
    }
}
