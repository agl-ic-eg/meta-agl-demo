require agl-ivi-image.bb

SUMMARY = "AGL IVI demo base Flutter image"

IMAGE_INSTALL += " \
    packagegroup-agl-demo-platform-flutter \
    agl-persistent-storage-api \
    weston-terminal-conf \
"
