//
// Created by dev on 2019/1/3.
//

#ifndef FFMPEGDEMO5_JXYUVENCODEH264_H
#define FFMPEGDEMO5_JXYUVENCODEH264_H


#include "base_include.h"

#include "UserArguments.h"


using namespace std;


/**
 * yuv编码h264
 */
class JXYUVEncodeH264 {
public:
    JXYUVEncodeH264(UserArguments* arg);
public:
    int initVideoEncoder();

    static void* startEncode(void * obj);

    int startSendOneFrame(uint8_t *buf);

    void user_end();

    void release();

    int encodeEnd();

    void custom_filter(const JXYUVEncodeH264 *h264_encoder, const uint8_t *picture_buf,
                       int in_y_size,
                       int format);
    ~JXYUVEncodeH264() {
    }
private:
    int flush_encoder(AVFormatContext *fmt_ctx, unsigned int stream_index);

private:
    UserArguments *arguments;
    int is_end = 0;
    int is_release = 0;
//    threadsafe_queue<uint8_t *> frame_queue;
    AVFormatContext *pFormatCtx;   // 视频的原数据（metadata）信息可以通过AVDictionary获取
    AVOutputFormat *fmt;
    AVStream *video_st;
    AVCodecContext *pCodecCtx;

    AVCodec *pCodec;   // 解码器
    //  存储压缩数据（视频对应H.264等码流数据，音频对应AAC/MP3等码流数据）
    AVPacket pkt;      // 视频的原数据（metadata）信息可以通过AVDictionary获取
    //  存储非压缩的数据（视频对应RGB/YUV像素数据，音频对应PCM采样数据）。AVFrame必须通过av_frame_alloc进行分配，通过av_frame_free释放。
    AVFrame *pFrame;
    int picture_size;
    int out_y_size;
    int framecnt = 0;
    int frame_count = 0;

};

#endif //FFMPEGDEMO5_JXYUVENCODEH264_H
