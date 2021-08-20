SUMMARY = "The middlewares for AGL IVI profile"
DESCRIPTION = "The set of packages required for AGL Distribution"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-image-ivi \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-agl-image-minimal \
"

RDEPENDS:${PN} += "\
    packagegroup-agl-ivi-connectivity \
    packagegroup-agl-ivi-graphics \
    packagegroup-agl-ivi-multimedia \
    packagegroup-agl-ivi-navigation \
    "

RDEPENDS:${PN} += "\
    agl-login-manager \
    "
