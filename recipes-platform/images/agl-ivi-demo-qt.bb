require agl-ivi-image.bb

SUMMARY = "AGL IVI demo Qt image"

ONDEMANDNAVI_CONF = "ondemandnavi-conf"

AGL_APPS_INSTALL += " \
    dashboard \
    hvac \
    ondemandnavi \
    ${ONDEMANDNAVI_CONF} \
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

