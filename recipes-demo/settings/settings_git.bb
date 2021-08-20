SUMMARY     = "Settings application"
DESCRIPTION = "AGL demonstration Settings application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/settings"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    libqtappfw \
    qtquickcontrols2 \
    qt-qrcode \
    qtvirtualkeyboard \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/settings;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "${AGL_APP_REVISION}"

S  = "${WORKDIR}/git"

inherit qmake5 aglwgt

RDEPENDS:${PN} += " \
    qt-qrcode \
    agl-service-bluetooth \
    agl-service-network \
    agl-service-voice-high \
    libqtappfw \
"
