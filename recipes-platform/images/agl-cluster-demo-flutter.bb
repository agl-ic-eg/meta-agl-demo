SUMMARY = "Baseline Flutter Image for Release"

LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb
require agl-demo-features.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

IMAGE_FEATURES += " \
    kuksa-val-databroker-client \
    kuksa-val-databroker \
"

AGL_DEVEL_INSTALL = " \
    simple-can-simulator \
"

# Generic
IMAGE_INSTALL += "\
    weston-ini-conf-landscape \
    \
    packagegroup-agl-networking \
    cluster-receiver \
    \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "${AGL_DEVEL_INSTALL}" , "", d)} \
"

# Flutter
FLUTTER_CLUSTER_DASHBOARD_CONF = "flutter-cluster-dashboard-conf"

IMAGE_INSTALL += "\
    flutter-auto \
    flutter-cluster-dashboard \
    ${FLUTTER_CLUSTER_DASHBOARD_CONF} \
    cluster-demo-config-flutter \
"

CLANGSDK = "1"
