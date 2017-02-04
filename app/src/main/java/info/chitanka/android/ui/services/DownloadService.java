package info.chitanka.android.ui.services;

/**
 * Created by joro on 04.02.17.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import info.chitanka.android.Constants;
import info.chitanka.android.R;
import info.chitanka.android.api.ChitankaApi;
import info.chitanka.android.api.ChitankaApiService;
import info.chitanka.android.mvp.models.Download;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DownloadService extends IntentService {

    private String bookFormat;
    private int bookId;

    public DownloadService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;

    @Override
    protected void onHandleIntent(Intent intent) {
        bookId = intent.getIntExtra(Constants.EXTRA_BOOK_ID, 0);
        bookFormat = intent.getStringExtra(Constants.EXTRA_BOOK_FORMAT);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle(getString(R.string.downloading))
                .setContentText(getString(R.string.downloading_file))
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());

        initDownload();

    }

    private void initDownload() {
        ChitankaApi retrofitInterface = ChitankaApiService.createChitankaApiService(new Gson());
        Call<ResponseBody> request = retrofitInterface.downloadBook(bookId, bookFormat);
        try {
            Response<ResponseBody> execute = request.execute();
            downloadFile(execute.body());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private void downloadFile(ResponseBody body) throws IOException {

        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "file.zip");
        OutputStream output = new FileOutputStream(outputFile);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;
        while ((count = bis.read(data)) != -1) {

            total += count;
            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

            Download download = new Download();
            download.setTotalFileSize(totalFileSize);

            if (currentTime > 1000 * timeCount) {

                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendNotification(download);
                timeCount++;
            }

            output.write(data, 0, count);
        }
        onDownloadComplete();
        output.flush();
        output.close();
        bis.close();

    }

    private void sendNotification(Download download) {
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(download.getCurrentFileSize() + "/" + totalFileSize + " MB");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void onDownloadComplete() {

        Download download = new Download();
        download.setProgress(100);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText(getString(R.string.file_downloaded));
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }
}
