SUMMARY     = "Messaging application"
DESCRIPTION = "AGL demonstration Messaging application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/messaging"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "qtdeclarative libqtappfw"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/messaging;protocol=https;branch=${AGL_BRANCH} \
           file://0001-Migrate-to-Qt-6.patch \
           "
SRCREV  = "e58b0382de8e665d64b8e3486022a6bcb0572823"

S  = "${WORKDIR}/git"

inherit qt6-qmake pkgconfig agl-app

AGL_APP_NAME = "Messaging"

RDEPENDS:${PN} += "libqtappfw"
