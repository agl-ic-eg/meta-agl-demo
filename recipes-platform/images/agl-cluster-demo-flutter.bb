SUMMARY = "Baseline Flutter Image for Release"

LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb
require agl-demo-features.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

IMAGE_FEATURES += " \
    kuksa-val-databroker-client \
    kuksa-val-databroker \
"

# Generic
IMAGE_INSTALL += "\
    weston-ini-conf-landscape \
    \
    packagegroup-agl-networking \
    cluster-receiver \
    \
    simple-can-simulator \
"

# Flutter
IMAGE_INSTALL += "\
    flutter-auto \
    flutter-cluster-dashboard \
    flutter-cluster-dashboard-conf \
    cluster-demo-config-flutter \
"

CLANGSDK = "1"
