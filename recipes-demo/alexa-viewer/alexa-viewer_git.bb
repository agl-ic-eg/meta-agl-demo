SUMMARY     = "Demo voice capabilities template viewer for Alexa voiceagent"
DESCRIPTION = "Demo voice capabilities template viewer for Alexa voiceagent"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/alexa-viewer"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qtquickcontrols2 \
    qlibhomescreen \
    af-binder \
    libqtappfw \
    wayland-native \
    wayland \
    qtwayland \
    qtwayland-native \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/alexa-viewer;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "${AGL_APP_REVISION}"

S  = "${WORKDIR}/git"

inherit cmake_qt5 aglwgt

OECMAKE_CXX_FLAGS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', '' , '-DQT_NO_DEBUG_OUTPUT', d)}"

RDEPENDS:${PN} = " \
    libqtappfw \
    libafbwsc \
    qlibhomescreen \
    agl-service-homescreen \
    agl-service-voice-high-capabilities \
"
