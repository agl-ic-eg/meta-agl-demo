SUMMARY     = "HVAC application"
DESCRIPTION = "AGL demonstration HVAC application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/hvac"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qtquickcontrols2 \
    libhomescreen \
    qttools-native \
    libqtappfw \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/hvac;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "${AGL_APP_REVISION}"

S  = "${WORKDIR}/git"

inherit qmake5 aglwgt

RDEPENDS:${PN} = "agl-service-hvac libqtappfw"
