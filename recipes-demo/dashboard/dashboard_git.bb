SUMMARY     = "Dashboard application"
DESCRIPTION = "AGL demonstration Dashboard application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/dashboard"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "qtquickcontrols2 qttools-native libqtappfw"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/dashboard;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "${AGL_APP_REVISION}"

S  = "${WORKDIR}/git"

inherit qmake5 aglwgt

RDEPENDS:${PN} += " \
    libqtappfw \
    agl-service-signal-composer \
"
