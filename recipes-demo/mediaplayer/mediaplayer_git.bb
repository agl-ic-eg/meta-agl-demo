SUMMARY     = "Media Player application"
DESCRIPTION = "AGL demonstration Media Player application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/mediaplayer"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "qtquickcontrols2 libqtappfw"

PV = "2.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/mediaplayer;protocol=https;branch=${AGL_BRANCH} \
           file://mediaplayer.conf \
           file://mediaplayer.token \
"
SRCREV  = "${AGL_APP_REVISION}"

S  = "${WORKDIR}/git"

inherit qmake5

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

FILES:${PN} += "${datadir}/icons/"

RDEPENDS:${PN} += "libqtappfw mpd"
