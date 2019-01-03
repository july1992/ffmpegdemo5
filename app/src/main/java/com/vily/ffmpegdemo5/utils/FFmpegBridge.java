package com.vily.ffmpegdemo5.utils;

/**
 * Created by jianxi on 2017/5/12.
 * https://github.com/mabeijianxi
 * mabeijianxi@gmail.com
 */

public class FFmpegBridge {

    static {
        System.loadLibrary("avutil");
        System.loadLibrary("fdk-aac");
        System.loadLibrary("avcodec");
        System.loadLibrary("avformat");
        System.loadLibrary("swscale");
        System.loadLibrary("swresample");
        System.loadLibrary("avfilter");
        System.loadLibrary("native-lib");
    }

    public static native void prepareJXFFmpegEncoder(String baseUrl, String media_name, int v_custom_format, int video_with, int video_height, int out_with, int out_height, int frameRate, int bitRate);

    public static native void test() ;
}