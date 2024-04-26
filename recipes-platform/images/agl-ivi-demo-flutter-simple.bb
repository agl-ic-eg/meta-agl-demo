require agl-ivi-image-flutter.bb

SUMMARY = "AGL IVI demo simple Flutter image"

AGL_APPS_INSTALL += " \
    flutter-homescreen \
    flutter-dashboard \
    flutter-hvac \
    ondemandnavi \
    settings \
    mediaplayer \
    messaging \
    phone \
    radio \
"

IMAGE_INSTALL += " \
    qtquickcontrols2-agl \
    qtquickcontrols2-agl-style \
"
