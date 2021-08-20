SUMMARY = "The networking for AGL"
DESCRIPTION = "A set of packages for AGL cluster's  networking"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-networking \
    "

ALLOW_EMPTY:${PN} = "1"


# fonts
RDEPENDS:${PN}:append = " \
    connman \
    connman-client \
    connman-tools \
    cluster-connman-conf \
    iproute2 \
"
