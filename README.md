# Al Mohtaref — المحترف

مشروع Android Native لإدارة مركز صيانة أجهزة منزلية، عربي RTL، ومخطط ليعمل محليًا دون اعتماد على الإنترنت في العمليات الأساسية.

## الحالة الحالية

هذه الحزمة هي **مرحلة تأسيس المشروع والوثائق + هيكل واجهة أولي** وليست MVP مكتملًا.

- تم إنشاء إعدادات مشروع Kotlin/Compose وGradle Version Catalog.
- تم إعداد شاشة Dashboard تأسيسية باللغة العربية.
- لم يتم ربط الشاشة بقاعدة بيانات.
- لم يتم تنفيذ CRM أو أوامر الصيانة أو الحسابات أو المخزون أو النسخ الاحتياطي أو Layla.
- لم يتم بناء APK أو تشغيل اختبارات؛ يلزم انتظار أول تشغيل ناجح لـ GitHub Actions للتحقق من البناء.

## المتطلبات

- Android Studio حديث يدعم AGP 8.10.1.
- JDK 17 أو 21.
- Android SDK Platform 35.
- Gradle 8.13 أو إصدار متوافق.
- Gradle Wrapper غير مرفق في هذه المرحلة.

## هيكل المشروع

- `app/`: تطبيق Android وواجهة Jetpack Compose.
- `gradle/`: Version Catalog لإصدارات الاعتماديات.
- `.github/workflows/android-debug.yml`: بناء Debug APK على GitHub Actions.
- `docs/ARCHITECTURE.md`: قرارات المعمارية.
- `docs/DATABASE_DESIGN.md`: التصميم المنطقي لقاعدة البيانات.
- `docs/ROADMAP.md`: مراحل التنفيذ.
- `docs/TEST_REPORT.md`: حالة الاختبارات والقيود.
- `docs/AI_SETUP.md`: سياسة إعداد ليلى والخصوصية.
- `docs/USER_GUIDE.md`: دليل استخدام مبدئي.

## فتح المشروع محليًا

1. افتح مجلد المشروع من Android Studio.
2. نفّذ Gradle Sync بعد تنزيل الاعتماديات.
3. شغّل التطبيق على جهاز Android 8.0 أو أحدث.
4. لا تعتبر الشاشة الحالية إصدارًا تشغيليًا؛ هي نقطة بدء للواجهة.

## بناء APK من الهاتف عبر GitHub Actions

لا تحتاج إلى تثبيت Android Studio على الهاتف. افتح تبويب Actions في المستودع، واختر تشغيل **Android Debug APK** يدويًا إذا لم يبدأ التشغيل تلقائيًا. عند نجاح المهمة، افتح ملخص التشغيل ونزّل Artifact باسم `AlMohtaref-debug-apk`.

الـ workflow يجهز JDK وAndroid SDK وGradle على runner سحابي ثم ينفذ:

```bash
gradle :app:assembleDebug
```

المخرج المتوقع:
`app/build/outputs/apk/debug/app-debug.apk`

هذه آلية بناء فقط؛ لا تعني أن التطبيق الحالي مكتمل الوظائف أو أن البناء قد نجح بالفعل. يجب مراجعة أول تشغيل للـ workflow ومعالجة أي أخطاء توافق أو اعتماديات تظهر.

## تنبيه

النسخة الحالية Starter Scaffold وليست MVP مكتملًا. لا توجد حتى الآن قاعدة بيانات Room أو وحدات العملاء والصيانة والمالية والمخزون أو التكامل الفعلي مع مزودي الذكاء الاصطناعي.
