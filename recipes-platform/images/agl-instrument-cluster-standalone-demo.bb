SUMMARY = "AGL Instrument Cluster minimized standalone demo image"
LICENSE = "MIT"

require recipes-platform/images/agl-image-boot.bb

NO_RECOMMENDATIONS = "1"

IMAGE_INSTALL += " \
    kernel-image \
    packagegroup-agl-ic-core \
    packagegroup-agl-ic-qt \
    cluster-refgui \
    dlt-daemon \
    dlt-daemon-system \
"

IMAGE_INSTALL:append:rpi = " mesa-megadriver"
