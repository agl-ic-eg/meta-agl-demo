DESCRIPTION = "AGL Cluster Demo Platform image currently contains a simple cluster interface."

require agl-cluster-demo-platform.inc

LICENSE = "MIT"

IMAGE_FEATURES:append = ""

# add packages for cluster demo platform (include demo apps) here
IMAGE_INSTALL:append = " \
    packagegroup-agl-cluster-demo-platform \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "cluster-dashboard-demo-config", "", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "weston-ini-conf-landscape-inverted", "weston-ini-conf-landscape", d)} \
    "

