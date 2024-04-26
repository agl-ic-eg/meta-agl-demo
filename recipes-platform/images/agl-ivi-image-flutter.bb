require agl-ivi-image.bb

SUMMARY = "AGL IVI demo base Flutter image"

IMAGE_INSTALL += " \
    packagegroup-agl-demo-platform-flutter \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "", "weston-terminal-conf", d)} \
"
