SUMMARY = "The software for demo platform of AGL cluster profile"
DESCRIPTION = "A set of packages belong to AGL Cluster Demo Platform"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-cluster-demo-platform \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-agl-profile-cluster-qt5 \
    packagegroup-agl-ttf-fonts \
    packagegroup-agl-source-han-sans-ttf-fonts \
    packagegroup-agl-networking \
    "


AGL_APPS = " \
    cluster-dashboard \
    cluster-receiver \
    qt-cluster-receiver \
    "

AGL_APIS = " \
    agl-service-can-low-level \
    agl-service-gps \
    agl-service-signal-composer \
    "

RDEPENDS:${PN}:append = " \
    hmi-debug \
    can-utils \
    linux-firmware-ralink \
    ${AGL_APPS} \
    ${AGL_APIS} \
"
