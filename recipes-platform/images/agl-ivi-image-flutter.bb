require agl-ivi-image.bb

SUMMARY = "AGL IVI demo base Flutter image"

IMAGE_INSTALL += " \
    packagegroup-agl-demo-platform-flutter \
    weston-terminal-conf \
"
