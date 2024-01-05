require agl-ivi-demo-base.bb

DESCRIPTION = "AGL Flutter demo base image"

IMAGE_INSTALL += " \
    packagegroup-agl-demo-platform-flutter \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "", "weston-terminal-conf", d)} \
"
