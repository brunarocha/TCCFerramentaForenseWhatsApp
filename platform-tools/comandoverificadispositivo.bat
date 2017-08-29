cd %1

adb pull /system/build.prop %2

adb shell cd storage ; ls > log.txt