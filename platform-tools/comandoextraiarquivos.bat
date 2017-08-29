cd %1

adb shell cd %3 ; mkdir whatsappbanco

adb shell su -c 'dd if=/data/data/com.whatsapp/databases/msgstore.db of=/%3/whatsappbanco/msgstore.db' 
adb shell su -c 'dd if=/data/data/com.whatsapp/databases/wa.db of=/%3/whatsappbanco/wa.db'
adb shell su -c 'dd if=/system/build.prop of=/%3/whatsappbanco/build.prop'

adb pull /%3/whatsappbanco/msgstore.db %2/msgstore.db
adb pull /%3/whatsappbanco/wa.db %2/wa.db
adb pull /%3/whatsappbanco/build.prop %2/build.prop

adb shell cd %3; rm -rf whatsappbanco

