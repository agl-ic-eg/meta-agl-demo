SUMMARY     = "Mixer application"
DESCRIPTION = "AGL demonstration Mixer application for control of PipeWire mixer elements"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/mixer"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qtquickcontrols2 \
    qtwebsockets \
    libafb-helpers-qt \
"

PROVIDES += "virtual/mixer"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/mixer;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "${AGL_APP_REVISION}"

S  = "${WORKDIR}/git"

inherit cmake_qt5 aglwgt

OECMAKE_CXX_FLAGS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', '' , '-DQT_NO_DEBUG_OUTPUT', d)}"

RDEPENDS:${PN} = "agl-service-audiomixer"
RPROVIDES:${PN} += "virtual/mixer"
