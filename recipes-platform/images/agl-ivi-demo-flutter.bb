require agl-ivi-image-flutter.bb

SUMMARY = "AGL IVI demo Flutter image"

FLUTTER_ICS_HOMESCREEN_CONF = "flutter-ics-homescreen-conf"
ONDEMANDNAVI_CONF = "ondemandnavi-conf"

AGL_APPS_INSTALL += " \
    flutter-ics-homescreen \
    ${FLUTTER_ICS_HOMESCREEN_CONF} \
    camera-gstreamer \
    ondemandnavi \
    ${ONDEMANDNAVI_CONF} \
"
