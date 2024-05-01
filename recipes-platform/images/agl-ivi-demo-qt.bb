require agl-ivi-image.bb

SUMMARY = "AGL IVI demo Qt image"

AGL_APPS_INSTALL += " \
    dashboard \
    hvac \
    ondemandnavi \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "ondemandnavi-conf-kvm-demo", "ondemandnavi-conf", d)} \
    settings \
    mediaplayer \
    messaging \
    phone \
    radio \
    window-management-client-grpc \
    camera-gstreamer \
"

IMAGE_INSTALL += " \
    packagegroup-agl-demo-platform \
    weston-terminal-conf \
"

