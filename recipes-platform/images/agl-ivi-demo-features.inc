require agl-demo-features.inc

FEATURE_PACKAGES_agl-demo-cluster-support = " \
    tbtnavi \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "tbtnavi-conf-kvm-demo", "tbtnavi-conf", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "", "kuksa-databroker-agl-demo-cluster", d)} \
"

IMAGE_FEATURES += " \
    kuksa-val-databroker-client \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "can-test-tools" , "", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "", "kuksa-val-databroker", d)} \
"
