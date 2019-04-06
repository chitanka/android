package info.chitanka.app.mvp.models;

import org.parceler.Parcel;

/**
 * Created by joro on 04.02.17.
 */

@org.parceler.Parcel(Parcel.Serialization.BEAN)
public class Download {
    private int progress;
    private int currentFileSize;
    private int totalFileSize;
    private String filePath;

    public Download() {

    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(int currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public int getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(int totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
