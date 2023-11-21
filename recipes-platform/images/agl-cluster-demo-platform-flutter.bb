SUMMARY = "Baseline Flutter Image for Release"

LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

# Break out KUKSA.val packages, as demo unit configuration
# points at KUKSA.val server on the IVI board in full demo
# builds with the "agl-demo-preload" feature enabled.
IMAGE_KUKSA_PACKAGES = " \
    packagegroup-agl-kuksa-val-databroker \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'packagegroup-agl-kuksa-val-databroker-devel' , '', d)} \
"


# generic
IMAGE_INSTALL += "\
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "psplash-inverted-config", "", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "weston-ini-conf-landscape-inverted", "weston-ini-conf-landscape", d)} \
    \
    packagegroup-agl-networking \
    cluster-receiver \
    \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "", "${IMAGE_KUKSA_PACKAGES}", d)} \
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
