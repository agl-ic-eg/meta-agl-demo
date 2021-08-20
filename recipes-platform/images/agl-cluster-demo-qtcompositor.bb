DESCRIPTION = "AGL Cluster Demo Qtwayland Compositor image currently contains a \
simple cluster interface and some AGL service."

require agl-cluster-demo-qtcompositor.inc

LICENSE = "MIT"

IMAGE_FEATURES:append = " \
    "

# add packages for cluster demo qtcompositor
IMAGE_INSTALL:append = " \
    packagegroup-agl-cluster-demo-qtcompositor \
    "

