require agl-demo-features.inc

TBTNAVI_CONF = "tbtnavi-conf"

FEATURE_PACKAGES_agl-demo-cluster-support = " \
    tbtnavi \
    ${TBTNAVI_CONF} \
    kuksa-databroker-agl-demo-cluster \
"

IMAGE_FEATURES += " \
    kuksa-val-databroker \
    kuksa-val-databroker-client \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "can-test-tools" , "", d)} \
"
