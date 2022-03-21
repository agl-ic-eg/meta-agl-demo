SUMMARY = "The software for AGL Cluster Demo Qtwayland Compositor"
DESCRIPTION = "A set of packages belong to AGL Cluster Demo Qtwayland Compositor"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-cluster-demo-qtcompositor \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-agl-profile-cluster-qtcompositor \
    packagegroup-agl-ttf-fonts \
    packagegroup-agl-networking \
    "

AGL_SERVICE = " \
    "

AGL_APPS = " \
    cluster-gauges-qtcompositor \
    "

RDEPENDS:${PN}:append = " \
    linux-firmware-ralink \
    can-utils \
    ${AGL_SERVICE} \
    ${AGL_APPS} \
"
