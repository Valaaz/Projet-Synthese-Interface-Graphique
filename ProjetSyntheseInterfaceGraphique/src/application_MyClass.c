#include "application_MyClass.h"
#include "util.h"

JNIEXPORT void JNICALL Java_application_MyClass_myPrint(JNIEnv *env, jobject obj, jstring s, jint i){
    const char *mystring = (*env)->GetStringUTFChars(env, s, NULL);
    printString(mystring);
    printInt(i);
}

JNIEXPORT void JNICALL Java_application_MyClass_findConvexHull
  (JNIEnv *env, jobject obj, jstring s1, jstring s2, jint i1, jint i2) {
    const char *infilename = (*env)->GetStringUTFChars(env, s1, NULL);
    const char *outfilename = (*env)->GetStringUTFChars(env, s2, NULL);
        printString(infilename);
        printString(outfilename);
        printInt(i1);
        printInt(i2);
}