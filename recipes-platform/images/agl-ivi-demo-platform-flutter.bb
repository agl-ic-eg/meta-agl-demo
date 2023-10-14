require agl-ivi-demo-base-flutter.bb

DESCRIPTION = "AGL Flutter Demo Platform image"

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

