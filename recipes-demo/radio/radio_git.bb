SUMMARY     = "Radio application"
DESCRIPTION = "AGL demonstration Radio application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/radio"
SECTION     = "apps"

LICENSE     = "Apache-2.0 & GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "qtquickcontrols2 libqtappfw"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/radio;protocol=https;branch=${AGL_BRANCH} \
           file://presets-ALS.conf \
           file://presets-CES.conf \
           file://presets-FOSDEM.conf \
"
SRCREV  = "6ae60dcb666e95dad881457cea03a7b72d15faf3"

S  = "${WORKDIR}/git"

inherit qmake5 pkgconfig agl-app

AGL_APP_NAME = "Radio"

# ALS, CES, FOSDEM available
AGL_RADIO_PRESETS_LOCALE ?= "CES"
do_install:append() {
    install -d ${D}${sysconfdir}/xdg/AGL
    install -m 0644 ${WORKDIR}/presets-CES.conf ${D}${sysconfdir}/xdg/AGL/radio-presets-CES.conf
    install -m 0644 ${WORKDIR}/presets-ALS.conf ${D}${sysconfdir}/xdg/AGL/radio-presets-ALS.conf
    install -m 0644 ${WORKDIR}/presets-FOSDEM.conf ${D}${sysconfdir}/xdg/AGL/radio-presets-FOSDEM.conf
    install -m 0644 ${WORKDIR}/presets-${AGL_RADIO_PRESETS_LOCALE}.conf ${D}${sysconfdir}/xdg/AGL/radio-presets.conf
}

FILES:${PN} += "${sysconfdir}/xdg/AGL/*"

RDEPENDS:${PN} += "libqtappfw"
