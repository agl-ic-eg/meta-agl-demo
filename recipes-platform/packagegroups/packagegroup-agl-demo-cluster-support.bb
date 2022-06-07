SUMMARY = "Extra software and configuration for cluster demo with AGL IVI profile demo platform"

LICENSE = "MIT"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = "\
    packagegroup-agl-demo-cluster-support \
"

RDEPENDS:${PN} += "\
    packagegroup-agl-demo-platform \
    tbtnavi \
    cluster-demo-network-config \
    kuksa-val-agl-demo-cluster \
"
