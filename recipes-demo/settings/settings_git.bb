SUMMARY     = "Settings application"
DESCRIPTION = "AGL demonstration Settings application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/settings"
SECTION     = "apps"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qtbase \
    qtdeclarative \
    qtquickcontrols2 \
    qtvirtualkeyboard \
    libqtappfw \
"

PV = "2.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/settings;protocol=https;branch=${AGL_BRANCH}"
SRCREV = "03330b92227fabff891b6bdbce4bf7f3c27a3e61"

S  = "${WORKDIR}/git"

inherit qmake5 pkgconfig

FILES:${PN} += "${datadir}/icons/"

RDEPENDS:${PN} += " \
    qtwayland \
    qtbase-qmlplugins \
    qtgraphicaleffects-qmlplugins \
    qtquickcontrols2-agl-style \
    libqtappfw \
"
