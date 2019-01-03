//
// Created by dev on 2019/1/3.
//

#ifndef FFMPEGDEMO5_JXJNIHANDLER_H
#define FFMPEGDEMO5_JXJNIHANDLER_H


#include "UserArguments.h"

class JXJNIHandler{
    ~JXJNIHandler(){
//        delete(arguments);
    }
public:
    void setup_video_state(int video_state);
    void setup_audio_state(int audio_state);
    void try_encode_over(UserArguments *pArguments);

    void backByte(UserArguments *pArguments, int i);

private:

private:
    int video_state;
    int audio_state;


};
    #endif //FFMPEGDEMO5_JXJNIHANDLER_H
