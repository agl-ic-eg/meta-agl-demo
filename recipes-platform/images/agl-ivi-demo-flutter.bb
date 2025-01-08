require agl-ivi-image-flutter.bb

SUMMARY = "AGL IVI demo Flutter image"

FLUTTER_ICS_HOMESCREEN_CONF = "flutter-ics-homescreen-conf"
ONDEMANDNAVI_CONF = "ondemandnavi-conf"

# import default music data package if PREINSTALL_MUSIC is set to "1"
MUSICDATA ?= "${@oe.utils.conditional("PREINSTALL_MUSIC", "1", "pre-install-music-data", "", d)}"

AGL_APPS_INSTALL += " \
    flutter-ics-homescreen \
    ${FLUTTER_ICS_HOMESCREEN_CONF} \
    camera-gstreamer \
    window-management-client-grpc \
    agl-shell-activator \
    ondemandnavi \
    ${ONDEMANDNAVI_CONF} \
    ${MUSICDATA} \
"
