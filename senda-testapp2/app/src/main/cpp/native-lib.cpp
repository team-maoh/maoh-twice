#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_example_ryomasenda_senda_1testapp2_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from SENDA-2";
    return env->NewStringUTF(hello.c_str());
}
