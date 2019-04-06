package info.chitanka.app.ui.services;

/**
 * Created by joro on 04.02.17.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.google.gson.Gson;

import org.parceler.Parcels;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import info.chitanka.app.Constants;
import info.chitanka.app.R;
import info.chitanka.app.api.ChitankaApi;
import info.chitanka.app.api.ChitankaApiService;
import info.chitanka.app.mvp.models.Download;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DownloadService extends IntentService {
    public static final String TAG = DownloadService.class.getSimpleName();

    private String url;
    private String fileName;
    private String folderPath;

    public DownloadService() {
        super(TAG);
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;

    @Override
    protected void onHandleIntent(Intent intent) {
        url = intent.getStringExtra(Constants.EXTRA_FILE_URL);
        fileName = intent.getStringExtra(Constants.EXTRA_FILE_NAME);
        folderPath = intent.getStringExtra(Constants.EXTRA_FOLDER_PATH);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.downloading))
                .setContentText(getString(R.string.downloading_file))
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());

        initDownload();

    }

    private void initDownload() {
        ChitankaApi retrofitInterface = ChitankaApiService.createChitankaApiService(new Gson());
        Call<ResponseBody> request = retrofitInterface.downloadFile(url);
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
        File outputFile = new File(folderPath, fileName);
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
        notificationManager.notify(Constants.NOTIFICATION_ID_DOWNLOAD, notificationBuilder.build());
    }

    private void sendIntent(Download download){
        Intent intent = new Intent(Constants.ACTION_MESSAGE_PROGRESS);
        intent.putExtra(Constants.EXTRA_DOWNLOAD, Parcels.wrap(download));
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete() {

        Download download = new Download();
        download.setProgress(100);
        download.setFilePath(new File(folderPath, fileName).getAbsolutePath());
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
}
