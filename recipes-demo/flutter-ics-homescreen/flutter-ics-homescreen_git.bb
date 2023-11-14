SUMMARY = "AGL ICS Flutter Homescreen"
DESCRIPTION = "Demo Flutter homescreen for Automotive Grade Linux by ICS."
HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-ics-homescreen"
SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-ics-homescreen;protocol=https;branch=${AGL_BRANCH} \
  file://change_grpc_port.patch \
  file://flutter-ics-homescreen.json \
  file://flutter-ics-homescreen.service \
  file://ics-homescreen.yaml \
  file://ics-homescreen.token \
"
SRCREV = "70ec8a79a121471a004e7e4c23157d10157e136f"

S = "${WORKDIR}/git"

PUBSPEC_APPNAME = "flutter_ics_homescreen"
FLUTTER_APPLICATION_INSTALL_PREFIX = "/flutter"

FLUTTER_BUILD_ARGS = "bundle -v"

inherit flutter-app systemd

APP_CONFIG = "${BPN}.json"

SYSTEMD_SERVICE:${PN} = "flutter-ics-homescreen.service"

do_install:append() {
  install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service

  install -D -m 0644 ${WORKDIR}/${APP_CONFIG} ${D}${datadir}/flutter/${BPN}.json

  # VIS authorization token file for KUKSA.val should ideally not
  # be readable by other users, but currently that's not doable
  # until a packaging/sandboxing/MAC scheme is (re)implemented or
  # something like OAuth is plumbed in as an alternative.
  install -d ${D}${sysconfdir}/xdg/AGL/homescreen
  install -m 0644 ${WORKDIR}/ics-homescreen.yaml ${D}${sysconfdir}/xdg/AGL/
  install -m 0644 ${WORKDIR}/ics-homescreen.token ${D}${sysconfdir}/xdg/AGL/homescreen/
}

FILES:${PN} += "${datadir} ${sysconfdir}/xdg/AGL"
RDEPENDS:${PN} += "flutter-auto agl-flutter-env"
