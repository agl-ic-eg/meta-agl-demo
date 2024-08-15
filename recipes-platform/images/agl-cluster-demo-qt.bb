DESCRIPTION = "AGL Cluster Demo Platform image currently contains a simple cluster interface."

LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb
require agl-demo-features.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

inherit features_check

REQUIRED_DISTRO_FEATURES = "wayland"

IMAGE_FEATURES += " \
    kuksa-val-databroker-client \
    kuksa-val-databroker \
"

# Set up for testing with the databroker when using agl-devel
AGL_DEVEL_INSTALL = " \
    cluster-demo-config \
    simple-can-simulator \
"

QT_CLUSTER_DASHBOARD_CONF = "cluster-dashboard-conf"

# add packages for cluster demo platform (include demo apps) here
IMAGE_INSTALL += " \
    packagegroup-agl-cluster-demo-platform \
    ${QT_CLUSTER_DASHBOARD_CONF} \
    kuksa-certificates-agl-ca \
    weston-ini-conf-landscape \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "${AGL_DEVEL_INSTALL}" , "", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "AGLCI", "qemu-set-display", "", d)} \
"
