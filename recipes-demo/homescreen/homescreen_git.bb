SUMMARY     = "Home Screen application"
DESCRIPTION = "AGL demonstration Home Screen application"
HOMEPAGE    = "http://docs.automotivelinux.org"
LICENSE     = "Apache-2.0"
SECTION     = "apps"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qtbase \
    qtdeclarative \
    qtquickcontrols2 \
    libqtappfw \
    libhomescreen \
    libafb-helpers-qt \
    wayland-native \
    wayland \
    qtwayland \
    qtwayland-native \
"

PV      = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/homescreen;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "${AGL_APP_REVISION}"

S       = "${WORKDIR}/git"

inherit qmake5 systemd pkgconfig aglwgt

PATH:prepend = "${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}:"

OE_QMAKE_CXXFLAGS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', '' , '-DQT_NO_DEBUG_OUTPUT', d)}"

RDEPENDS:${PN} += " \
    libqtappfw \
    agl-service-audiomixer \
    agl-service-bluetooth \
    agl-service-homescreen \
    agl-service-network \
    agl-service-weather \
    agl-service-voice-high \
"
