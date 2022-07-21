SUMMARY     = "HVAC application"
DESCRIPTION = "AGL demonstration HVAC application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/hvac"
SECTION     = "apps"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qttools-native \
    qtbase \
    qtdeclarative \
    qtquickcontrols2 \
    libqtappfw \
"

PV = "2.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/hvac;protocol=https;branch=${AGL_BRANCH} \
           file://hvac.conf \
           file://hvac.token \
"
SRCREV = "2f96cf1047608fa5bf83addc52695b1b7d23b28c"

S = "${WORKDIR}/git"

inherit qmake5 pkgconfig

do_install:append() {
    # Currently using default global client and CA certificates
    # for KUKSA.val SSL, installing app specific ones would go here.

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/hvac
    install -m 0644 ${WORKDIR}/hvac.conf ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/hvac.token ${D}${sysconfdir}/xdg/AGL/hvac/
}

FILES:${PN} += "${datadir}/icons/"

RDEPENDS:${PN} += " \
    qtwayland \
    qtbase-qmlplugins \
    qtgraphicaleffects-qmlplugins \
    qtquickcontrols2-agl-style \
    libqtappfw \
"
