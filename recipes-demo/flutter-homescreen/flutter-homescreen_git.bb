SUMMARY = "AGL Flutter Homescreen"
DESCRIPTION = "Demo Flutter homescreen for Automotive Grade Linux."
HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-homescreen"
SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-homescreen;protocol=https;branch=${AGL_BRANCH} \
           file://flutter-homescreen.json \
           file://flutter-homescreen.service \
           file://homescreen.yaml \
           file://homescreen.token \
"
SRCREV = "6c016a39fb993165d0c103691ecf5333e09ef438"

S = "${WORKDIR}/git"

PUBSPEC_APPNAME = "flutter_homescreen"
FLUTTER_BUILD_ARGS = "bundle -v"

inherit flutter-app systemd

APP_CONFIG = "${BPN}.json"

SYSTEMD_SERVICE:${PN} = "flutter-homescreen.service"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service

    install -D -m 0644 ${WORKDIR}/${APP_CONFIG} ${D}${datadir}/flutter/${BPN}.json

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/homescreen
    install -m 0644 ${WORKDIR}/homescreen.yaml ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/homescreen.token ${D}${sysconfdir}/xdg/AGL/homescreen/
}

FILES:${PN} += "${datadir} ${sysconfdir}/xdg/AGL"
RDEPENDS:${PN} += "flutter-auto agl-flutter-env"
