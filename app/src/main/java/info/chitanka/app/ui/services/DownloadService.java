package info.chitanka.app.ui.services;

/**
 * Created by joro on 04.02.17.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.parceler.Parcels;

import java.io.File;
import java.util.List;

import info.chitanka.app.Constants;
import info.chitanka.app.R;
import info.chitanka.app.mvp.models.Download;
import timber.log.Timber;

public class DownloadService extends IntentService implements FetchListener {
    public static final String TAG = DownloadService.class.getSimpleName();

    private String url;
    private String fileName;
    private String folderPath;
    private String fullFilePath;

    public DownloadService() {
        super(TAG);
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    private Request request;
    private FetchConfiguration fetchConfiguration;

    @Override
    protected void onHandleIntent(Intent intent) {
        url = intent.getStringExtra(Constants.EXTRA_FILE_URL);
        fileName = intent.getStringExtra(Constants.EXTRA_FILE_NAME);
        folderPath = intent.getStringExtra(Constants.EXTRA_FOLDER_PATH);
        fetchConfiguration = new FetchConfiguration.Builder(this)
                .setDownloadConcurrentLimit(3)
                .build();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.downloading))
                .setContentText(getString(R.string.downloading_file))
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());

        startDownload();
    }

    private void startDownload() {
        File outputFile = new File(folderPath, fileName);
        String extension = url.substring(url.lastIndexOf("."));
        fullFilePath = outputFile.getAbsolutePath() + extension;

        Fetch fetch = Fetch.Impl.getInstance(fetchConfiguration);
        request = new Request(url, fullFilePath);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);

        fetch.addListener(this);
        fetch.enqueue(request, updatedRequest -> {
            //Request was successfully enqueued for download.
        }, error -> {
            Timber.e(error.getThrowable(), "Error downloading file");
        });

    }

    private void sendNotification(Download download) {
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(download.getProgress() + "%");
        notificationManager.notify(Constants.NOTIFICATION_ID_DOWNLOAD, notificationBuilder.build());
    }

    private void sendIntent(Download download) {
        Intent intent = new Intent(Constants.ACTION_MESSAGE_PROGRESS);
        intent.putExtra(Constants.EXTRA_DOWNLOAD, Parcels.wrap(download));
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete() {
        Download download = new Download();
        download.setProgress(100);
        download.setFilePath(fullFilePath);
        sendIntent(download);

        notificationManager.cancel(Constants.NOTIFICATION_ID_DOWNLOAD);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText(getString(R.string.file_downloaded));
        notificationManager.notify(Constants.NOTIFICATION_ID_DOWNLOAD, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

    @Override
    public void onAdded(com.tonyodev.fetch2.@NotNull Download download) {

    }

    @Override
    public void onCancelled(com.tonyodev.fetch2.@NotNull Download download) {

    }

    @Override
    public void onCompleted(com.tonyodev.fetch2.@NotNull Download download) {
        if(download.getId() == request.getId()) {
            onDownloadComplete();
        }
    }

    @Override
    public void onDeleted(com.tonyodev.fetch2.@NotNull Download download) {

    }

    @Override
    public void onDownloadBlockUpdated(com.tonyodev.fetch2.@NotNull Download download, @NotNull DownloadBlock downloadBlock, int i) {

    }

    @Override
    public void onError(com.tonyodev.fetch2.@NotNull Download download, @NotNull Error error, @Nullable Throwable throwable) {
        Timber.e(throwable);
    }

    @Override
    public void onPaused(com.tonyodev.fetch2.@NotNull Download download) {

    }

    @Override
    public void onProgress(com.tonyodev.fetch2.@NotNull Download download, long l, long l1) {
        if (request.getId() == download.getId()) {
            int progress = download.getProgress();
            if (progress < 100) {
                Download bookDownload = new Download();
                bookDownload.setProgress(progress);
                sendNotification(bookDownload);
            }
        }
    }

    @Override
    public void onQueued(com.tonyodev.fetch2.@NotNull Download download, boolean b) {

    }

    @Override
    public void onRemoved(com.tonyodev.fetch2.@NotNull Download download) {

    }

    @Override
    public void onResumed(com.tonyodev.fetch2.@NotNull Download download) {

    }

    @Override
    public void onStarted(com.tonyodev.fetch2.@NotNull Download download, @NotNull List<? extends DownloadBlock> list, int i) {

    }

    @Override
    public void onWaitingNetwork(com.tonyodev.fetch2.@NotNull Download download) {

    }
}
