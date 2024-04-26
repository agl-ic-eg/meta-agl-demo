DESCRIPTION = "AGL Cluster Demo Platform image currently contains a simple cluster interface."

LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb
require agl-demo-features.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

inherit features_check

REQUIRED_DISTRO_FEATURES = "wayland"

# KUKSA.val databroker is not installed with "agl-demo-preload"
# feature enabled, since demo unit configuration points at the
# databroker on the IVI board in that setup.
IMAGE_FEATURES += " \
    kuksa-val-databroker-client \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "", "kuksa-val-databroker", d)} \
"

# add packages for cluster demo platform (include demo apps) here
IMAGE_INSTALL += " \
    packagegroup-agl-cluster-demo-platform \
    kuksa-certificates-agl-ca \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "cluster-demo-config", "", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "weston-ini-conf-landscape-inverted", "weston-ini-conf-landscape", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "AGLCI", "qemu-set-display", "", d)} \
    "
