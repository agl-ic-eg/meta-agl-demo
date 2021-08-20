SUMMARY = "The middleware for AGL telematics profile"
DESCRIPTION = "The set of packages required for AGL Telematics Distribution"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-profile-telematics \
    profile-telematics \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-agl-image-boot \
    packagegroup-agl-core-security \
    ${@bb.utils.contains('VIRTUAL-RUNTIME_net_manager','connman','connman connman-client','',d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "3g", "libqmi", "", d)} \
    agl-login-manager \
    agl-service-can-low-level \
    agl-service-network \
    can-utils \
"

RDEPENDS:profile-telematics = "${PN}"
