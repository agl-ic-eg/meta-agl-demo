SUMMARY = "The middleware for AGL Qt5 based cluster profile"
DESCRIPTION = "The set of packages required for AGL Qt5 based Cluster Distribution"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-profile-cluster-qt5 \
    profile-cluster-qt5-wayland \
    profile-cluster-qt5 \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-agl-image-boot \
    packagegroup-agl-core-security \
    packagegroup-agl-graphical-weston \
"

RDEPENDS:profile-cluster-qt5-wayland = "${PN}"
RDEPENDS:profile-cluster-qt5 = "profile-cluster-qt5-wayland"
