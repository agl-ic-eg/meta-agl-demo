FILESEXTRAPATHS:append := ":${THISDIR}/qtmultimedia"

PACKAGECONFIG:append = " gstreamer"

SRC_URI:append = " \
    file://0001-metadata-image-support-with-the-data-URL-scheme-for-.patch \
    "
