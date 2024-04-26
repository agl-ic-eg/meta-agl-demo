SUMMARY = "Baseline Flutter Image for Release"

LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb
require agl-demo-features.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

# KUKSA.val databroker is not installed with "agl-demo-preload"
# feature enabled, since demo unit configuration points at the
# databroker on the IVI board in that setup.
IMAGE_FEATURES += " \
    kuksa-val-databroker-client \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "", "kuksa-val-databroker", d)} \
"

# Generic
IMAGE_INSTALL += "\
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "psplash-inverted-config", "", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "weston-ini-conf-landscape-inverted", "weston-ini-conf-landscape", d)} \
    \
    packagegroup-agl-networking \
    cluster-receiver \
    \
    simple-can-simulator \
    "

# Flutter
IMAGE_INSTALL += "\
    flutter-cluster-dashboard \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "flutter-cluster-dashboard-conf-demo", "flutter-cluster-dashboard-conf", d)} \
    cluster-demo-config-flutter \
    flutter-auto \
    "

CLANGSDK = "1"
