SUMMARY = "The middleware for AGL Qt5 based cluster qtcompositor"
DESCRIPTION = "The set of packages required for AGL Qt5 based Cluster Demo Qtcompositor Distribution"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-profile-cluster-qtcompositor \
    profile-cluster-qt5-egl \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-agl-image-boot \
    packagegroup-agl-core-security \
    packagegroup-agl-core-connectivity \
"

QT_LITE = " \
    qtbase \
    qtdeclarative \
    qtwayland \
    qtgraphicaleffects-qmlplugins \
    qtsvg-plugins \
"

RDEPENDS:${PN}:append = " \
    ${QT_LITE} \
"

RDEPENDS:profile-cluster-qt5-egl = "${PN}"
