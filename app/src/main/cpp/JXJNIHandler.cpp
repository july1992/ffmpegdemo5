//
// Created by dev on 2019/1/3.
//

#include "JXJNIHandler.h"


/** 改变视频录制状态
//* @param video_state
*/
void JXJNIHandler::setup_video_state(int video_state) {
    JXJNIHandler::video_state = video_state;
}

/**
 * 改变音频录制状态
 * @param audio_state
 */
void JXJNIHandler::setup_audio_state(int audio_state) {
    JXJNIHandler::audio_state = audio_state;
}

void JXJNIHandler::backByte(UserArguments *pArguments, int i) {

}
void JXJNIHandler::try_encode_over(UserArguments *pArguments) {

}