require agl-ivi-image-flutter.bb

SUMMARY = "AGL IVI demo Flutter image"

AGL_APPS_INSTALL += " \
    flutter-ics-homescreen \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "flutter-ics-homescreen-conf-kvm-demo", "flutter-ics-homescreen-conf", d)} \
    camera-gstreamer \
    ondemandnavi \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "ondemandnavi-conf-kvm-demo", "ondemandnavi-conf", d)} \
"
