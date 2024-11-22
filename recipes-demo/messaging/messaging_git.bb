SUMMARY     = "Messaging application"
DESCRIPTION = "AGL demonstration Messaging application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/messaging"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "qtdeclarative libqtappfw"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/messaging;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "77ea1b245737d0cfc4d20035e6476dae6969e699"

S  = "${WORKDIR}/git"

inherit qt6-qmake pkgconfig agl-app

AGL_APP_NAME = "Messaging"

RDEPENDS:${PN} += "libqtappfw"
