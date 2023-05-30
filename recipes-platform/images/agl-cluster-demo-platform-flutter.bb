SUMMARY = "Baseline Flutter Image for Release"

LICENSE = "MIT"

require recipes-platform/images/agl-image-weston.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

# Break out KUKSA.val packages, as demo unit configuration
# points at KUKSA.val server on the IVI board instead of
# running the full stack locally.
IMAGE_KUKSA_PACKAGES = " \
    kuksa-val \
    kuksa-val-agl \
    kuksa-certificates-agl \
    kuksa-dbc-feeder \
    kuksa-vss-init \
"

# generic
IMAGE_INSTALL:append = "\
    agl-compositor \
    agl-compositor-init \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "weston-ini-conf-landscape-inverted", "weston-ini-conf-landscape", d)} \
    \
    packagegroup-agl-profile-graphical \
    packagegroup-agl-networking \
    cluster-receiver \
    \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "", "${IMAGE_KUKSA_PACKAGES}", d)} \
    simple-can-simulator \
    "

# Flutter
IMAGE_INSTALL:append = "\
    flutter-cluster-dashboard \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "flutter-cluster-dashboard-conf-demo", "flutter-cluster-dashboard-conf", d)} \
    cluster-demo-config-flutter \
    flutter-auto-runtimerelease \
    "

CLANGSDK = "1"
