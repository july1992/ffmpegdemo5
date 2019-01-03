#include <jni.h>
#include <string>
#include <android/log.h>
#include "JXJNIHandler.h"
#include "base_include.h"
#include "UserArguments.h"
#include "JXYUVEncodeH264.h"






using namespace std;

JXYUVEncodeH264 *h264_encoder;

extern "C" JNIEXPORT jstring
JNICALL
Java_com_vily_ffmpegdemo5_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


#define VIDEO_FORMAT ".h264"
#define AUDIO_FORMAT ".aac"

extern "C"
JNIEXPORT void JNICALL
Java_com_vily_ffmpegdemo5_utils_FFmpegBridge_prepareJXFFmpegEncoder(JNIEnv
                                                                    *env,
                                                                    jclass type,
                                                                    jstring
                                                                    baseUrl_,
                                                                    jstring media_name_,
                                                                    jint
                                                                    v_custom_format,
                                                                    jint video_with,
                                                                    jint
                                                                    video_height,
                                                                    jint out_with, jint
                                                                    out_height,
                                                                    jint frameRate, jint
                                                                    bitRate) {

    jclass global_class = (jclass) env->NewGlobalRef(type);

    const char *baseUrl = env->GetStringUTFChars(baseUrl_, 0);
    const char *media_name = env->GetStringUTFChars(media_name_, 0);

    logi("-------------%5d", video_with)
    logi("-------------%5d", video_height)
    logi("-------------%5d", out_with)
    logi("-------------%5d", out_height)

    UserArguments *arguments = (UserArguments *) malloc(sizeof(UserArguments));
// TODO

    arguments->
            media_base_path = baseUrl;


    JXJNIHandler *jni_handler = new JXJNIHandler();
    jni_handler->
            setup_audio_state(START_STATE);    // 开始录制
    jni_handler->
            setup_video_state(START_STATE);


    size_t v_path_size = strlen(baseUrl) + strlen(VIDEO_FORMAT) + 1;
    arguments->
            video_path = (char *) malloc(v_path_size + 1);



    arguments->video_bit_rate = bitRate;
    arguments->frame_rate = frameRate;
    arguments->audio_bit_rate = 40000;
    arguments->audio_sample_rate = 44100;
    arguments->in_width = video_with;
    arguments->in_height = video_height;
    arguments->out_height = out_height;
    arguments->out_width = out_with;
    arguments->v_custom_format = v_custom_format;
    arguments->handler = jni_handler;
    arguments->env = env;
    arguments->java_class = global_class;
    arguments->env->GetJavaVM(&arguments->javaVM);


    h264_encoder = new JXYUVEncodeH264(arguments);

    env->ReleaseStringUTFChars(baseUrl_, baseUrl);
    env->ReleaseStringUTFChars(media_name_, media_name);

    return;

}

extern "C"
JNIEXPORT void JNICALL
Java_com_vily_ffmpegdemo5_utils_FFmpegBridge_test(JNIEnv
                                                  *env,
                                                  jclass type
) {

// TODO

    return;

}