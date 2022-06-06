require agl-image-ivi.bb

DESCRIPTION = "AGL Demo Platform image currently contains a simple HMI and \
demos."

FEATURE_PACKAGES_agl-demo-preload = "packagegroup-agl-demo-preload"
FEATURE_PACKAGES_agl-demo-cluster-support = "packagegroup-agl-demo-cluster-support"

IMAGE_FEATURES += "${@bb.utils.filter("AGL_FEATURES", "agl-demo-preload agl-demo-cluster-support", d)}"

# add packages for demo platform (include demo apps) here
IMAGE_INSTALL:append = " \
    packagegroup-agl-demo-platform \
"

