package com.vily.ffmpegdemo5.camera0;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.vily.ffmpegdemo5.R;
import com.vily.ffmpegdemo5.utils.FFmpegBridge;
import com.vily.ffmpegdemo5.utils.FileUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by zhangxd on 2018/9/5.
 */

public class RecordActivity extends AppCompatActivity {

    private static final String TAG = RecordActivity.class.getSimpleName();


    public static SurfaceHolder surfaceHolder;


    private FocusSurfaceView mSv_surface1;
    private FocusSurfaceView mSv_surface2;
    private ImageView mIv_change_flash;
    private ImageView mIv_change_camera;
    private RelativeLayout mRl_top;

    private String tag = "big";
    private int mWidth;
    private int mHeight;
    private SurfaceHolder mSurfaceHolder2;
    private Camera1Utils mCamera1Utils;

    //  320*240    640*480  720*480
    private static int VIDEO_With=640;
    private static int VIDEO_Height=480;
    private static int BIT_RATE=60000;
    private static int FRAME_RATE=25;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    public static  String flv_GanWu = FileUtils.getMainDir() +"/ganwu.flv";
    public static  String baseUrl = Environment.getExternalStorageState();


    private int breforCount=0;
    private int afterCount=0;

    private long mStartTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();
        initManager();
        initSurfaceHolder();
        initLietener();
    }


    private void initView() {
        mSv_surface1 = findViewById(R.id.sv_surface1);
        mSv_surface2 = findViewById(R.id.sv_surface2);
        mRl_top = findViewById(R.id.rl_top);
        mIv_change_flash = findViewById(R.id.iv_change_flash);
        mIv_change_camera = findViewById(R.id.iv_change_camera);


    }

    private void initManager() {


        mCamera1Utils = Camera1Utils.getInstance(RecordActivity.this,VIDEO_With,VIDEO_Height);
        mWidth = getWindowManager().getDefaultDisplay().getWidth();
        mHeight = getWindowManager().getDefaultDisplay().getHeight();

        FFmpegBridge.prepareJXFFmpegEncoder( baseUrl, "333",1, VIDEO_With, VIDEO_Height, VIDEO_With, VIDEO_Height, FRAME_RATE, BIT_RATE);
//        FFmpegBridge.test();


    }

    private void initSurfaceHolder() {
        surfaceHolder = mSv_surface1.getHolder();
        mSurfaceHolder2 = mSv_surface2.getHolder();


        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.i(TAG, "surfaceCreated: --------suface1");
                if("big".equals(tag)){
                    mCamera1Utils.initCamera(surfaceHolder);
//                    FFmpegUtil.getInstance().initVideo(flv_GanWu, VIDEO_With, VIDEO_Height);
                    mCamera1Utils.startPreview();

                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.i(TAG, "surfaceDestroyed: ------------2");
            }
        });


    }
    private void initLietener() {

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(afterCount>0){
                    Log.i(TAG, "run: ---------每秒："+afterCount+"byte"+"----"+afterCount*8/1024+"kb");
                }

                afterCount=0;

            }
        }, 0,1000);


        mIv_change_flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Camera1Utils.getInstance(RecordActivity.this, VIDEO_With, VIDEO_Height).changeFlash()) {
                    mIv_change_flash.setImageResource(R.mipmap.video_flash_open);
                } else {
                    mIv_change_flash.setImageResource(R.mipmap.video_flash_close);
                }
            }
        });

        mIv_change_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera1Utils.getInstance(RecordActivity.this, VIDEO_With, VIDEO_Height).switchCamera();
                mIv_change_flash.setImageResource(R.mipmap.video_flash_close);
            }
        });

        mCamera1Utils.setOnPreviewVedioAudioCallBack(new Camera1Utils.OnPreviewVedioAudioCallBack() {
            @Override
            public void onVedioCallBack(final byte[] bytes) {

                executor.execute(new Runnable() {
                    @Override
                    public void run() {


//                        FFmpegHandle.getInstance().onFrameCallback(bytes);

                    }
                });




            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();


        mCamera1Utils.stopCamera();

    }


    @Override
    protected void onResume() {
        super.onResume();

        mCamera1Utils.startPreview();

        mStartTime = System.currentTimeMillis();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera1Utils.destroyCamera();


//        FFmpegUtil.getInstance().close();
    }



}
