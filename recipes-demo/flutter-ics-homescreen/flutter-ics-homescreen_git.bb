SUMMARY = "AGL ICS Flutter Homescreen"
DESCRIPTION = "Demo Flutter homescreen for Automotive Grade Linux by ICS."
HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-ics-homescreen"
SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-ics-homescreen;protocol=https;branch=${AGL_BRANCH} \
  file://flutter-ics-homescreen.json \
  file://flutter-ics-homescreen.service \
  file://ics-homescreen.yaml \
  file://ics-homescreen.yaml.kvm-demo \
  file://ics-homescreen.token \
  file://radio-presets.yaml \
  file://kvm.conf \
"
SRCREV = "5587c6ae79b482fbff26442bb239d7d7eb55a337"

S = "${WORKDIR}/git"

PUBSPEC_APPNAME = "flutter_ics_homescreen"
FLUTTER_BUILD_ARGS = "bundle -v"

inherit flutter-app systemd update-alternatives

APP_CONFIG = "${BPN}.json"

SYSTEMD_SERVICE:${PN} = "flutter-ics-homescreen.service"

# Disable the background animation on all platforms except the Renesas M3/H3 for now
DISABLE_BG_ANIMATION = "-DDISABLE_BKG_ANIMATION=true"
DISABLE_BG_ANIMATION:rcar-gen3 = ""
APP_AOT_EXTRA:append = " ${DISABLE_BG_ANIMATION}"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service

    install -D -m 0644 ${WORKDIR}/kvm.conf ${D}${systemd_system_unitdir}/${BPN}.service.d/kvm.conf

    install -D -m 0644 ${WORKDIR}/${APP_CONFIG} ${D}${datadir}/flutter/${BPN}.json

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/ics-homescreen
    install -m 0644 ${WORKDIR}/ics-homescreen.yaml ${D}${sysconfdir}/xdg/AGL/ics-homescreen.yaml.default
    install -m 0644 ${WORKDIR}/ics-homescreen.yaml.kvm-demo ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/ics-homescreen.token ${D}${sysconfdir}/xdg/AGL/ics-homescreen/
    install -m 0644 ${WORKDIR}/radio-presets.yaml ${D}${sysconfdir}/xdg/AGL/ics-homescreen/
}

ALTERNATIVE_LINK_NAME[ics-homescreen.yaml] = "${sysconfdir}/xdg/AGL/ics-homescreen.yaml"

FILES:${PN} += "${datadir} ${sysconfdir}/xdg/AGL"

RDEPENDS:${PN} += " \
    flutter-auto \
    agl-flutter-env \
    applaunchd \
"

PACKAGE_BEFORE_PN += "${PN}-conf ${PN}-conf-kvm-demo"

FILES:${PN}-conf += "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.default"
RDEPENDS:${PN}-conf = "${PN}"
RPROVIDES:${PN}-conf = "ics-homescreen.yaml"
RCONFLICTS:${PN}-conf = "${PN}-conf-kvm-demo"
ALTERNATIVE:${PN}-conf = "ics-homescreen.yaml"
ALTERNATIVE_TARGET_${PN}-conf = "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.default"

FILES:${PN}-conf-kvm-demo += " \
    ${sysconfdir}/xdg/AGL/ics-homescreen.yaml.kvm-demo \
    ${systemd_system_unitdir}/flutter-ics-homescreen.service.d/kvm.conf \
"
RDEPENDS:${PN}-conf-kvm-demo = "${PN}"
RPROVIDES:${PN}-conf-kvm-demo = "ics-homescreen.yaml"
RCONFLICTS:${PN}-conf-kvm-demo = "${PN}-conf"
ALTERNATIVE:${PN}-conf-kvm-demo = "ics-homescreen.yaml"
ALTERNATIVE_TARGET_${PN}-conf-kvm-demo = "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.kvm-demo"
