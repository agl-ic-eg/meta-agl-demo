SUMMARY = "A basic Wayland based cluster image with Qt5 support"

require agl-image-cluster-qt5.inc

LICENSE = "MIT"

IMAGE_INSTALL:append = "\
    profile-cluster-qt5 \
    "
