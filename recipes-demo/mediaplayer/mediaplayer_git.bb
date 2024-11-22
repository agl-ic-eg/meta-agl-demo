SUMMARY     = "Media Player application"
DESCRIPTION = "AGL demonstration Media Player application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/mediaplayer"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "qtdeclarative libqtappfw"

PV = "2.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/mediaplayer;protocol=https;branch=${AGL_BRANCH} \
           file://mediaplayer.conf \
           file://mediaplayer.token \
           "
SRCREV  = "4f733db769ac96df3e83b9c90506924ee6f19800"

S  = "${WORKDIR}/git"

inherit qt6-qmake pkgconfig agl-app

AGL_APP_NAME = "Mediaplayer"

do_install:append() {
    # Currently using default global client and CA certificates
    # for KUKSA.val SSL, installing app specific ones would go here.

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/mediaplayer
    install -m 0644 ${WORKDIR}/mediaplayer.conf ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/mediaplayer.token ${D}${sysconfdir}/xdg/AGL/mediaplayer/
}

RDEPENDS:${PN} += "libqtappfw mpd"
