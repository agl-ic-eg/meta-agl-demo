SUMMARY = "KUKSA.val databroker packages"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-kuksa-val-databroker \
    packagegroup-agl-kuksa-val-databroker-devel \
"

RDEPENDS:packagegroup-agl-kuksa-val-databroker = "\
    kuksa-databroker \
    kuksa-databroker-agl \
    kuksa-certificates-agl \
    kuksa-can-provider \
    kuksa-can-provider-conf-agl \
    agl-vss-helper \
"

RDEPENDS:packagegroup-agl-kuksa-val-databroker-devel = "\
    kuksa-databroker-cli \
"
