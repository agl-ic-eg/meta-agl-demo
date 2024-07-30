SUMMARY     = "AGL QtQuickControls2 style customizations"
HOMEPAGE    = "https://git.automotivelinux.org/src/qtquickcontrols2-agl-style"
LICENSE     = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=815ca599c9df247a0c7f619bab123dad"

DEPENDS = "qtdeclarative"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/qtquickcontrols2-agl-style;protocol=https;branch=${AGL_BRANCH} \
           file://0001-Migrate-to-Qt-6.patch \
           "
SRCREV = "c02692a3c20d9aed1192137a67d5be882c60e71f"

S = "${WORKDIR}/git"

inherit qt6-qmake

FILES:${PN} += "${OE_QMAKE_PATH_QML}/QtQuick/Controls.2/AGL/*"

RDEPENDS:${PN} += " \
    qtsvg-plugins \
"
