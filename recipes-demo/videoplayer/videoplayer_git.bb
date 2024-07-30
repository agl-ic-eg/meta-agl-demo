SUMMARY     = "Video Player for AGL Demonstration"
DESCRIPTION = "AGL HMI Application for demonstrating Media Player on AGL Distribution"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/videoplayer"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/videoplayer;protocol=https;nobranch=1"
SRCREV = "2f4d09aec6531c64356a52255bcfaf8df7c4f3e4"

PV = "1.0+git${SRCPV}"
S  = "${WORKDIR}/git"

# build-time dependencies
DEPENDS += " \
    qtdeclarative \
    libqtappfw \
    qtmultimedia \
"

inherit qt6-qmake agl-app

AGL_APP_NAME = "Video"

RDEPENDS:${PN} += " \
    qtmultimedia \
    qtmultimedia-qmlplugins \
"

FILES:${PN} += "${datadir}/icons"
