require agl-demo-features.inc

FEATURE_PACKAGES_agl-demo-preload = "packagegroup-agl-demo-preload"
FEATURE_PACKAGES_agl-demo-cluster-support = "packagegroup-agl-demo-cluster-support"

IMAGE_FEATURES += "${@bb.utils.filter("AGL_FEATURES", "agl-demo-preload agl-demo-cluster-support", d)}"

IMAGE_FEATURES += " \
    kuksa-val-databroker-client \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "", "kuksa-val-databroker", d)} \
"
