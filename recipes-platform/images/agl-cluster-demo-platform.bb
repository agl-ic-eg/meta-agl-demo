DESCRIPTION = "AGL Cluster Demo Platform image currently contains a simple cluster interface."

LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

inherit features_check

REQUIRED_DISTRO_FEATURES = "wayland"

# Break out KUKSA.val packages, as demo unit configuration
# points at KUKSA.val server on the IVI board in full demo
# builds with the "agl-demo-preload" feature enabled.
KUKSA_DATABROKER_PACKAGES = " \
    packagegroup-agl-kuksa-val-databroker \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'packagegroup-agl-kuksa-val-databroker-devel' , '', d)} \
"

# add packages for cluster demo platform (include demo apps) here
IMAGE_INSTALL += " \
    packagegroup-agl-cluster-demo-platform \
    kuksa-certificates-agl-ca \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "cluster-demo-config", "", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "weston-ini-conf-landscape-inverted", "weston-ini-conf-landscape", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "", "${KUKSA_DATABROKER_PACKAGES}", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "AGLCI", "qemu-set-display", "", d)} \
    "
