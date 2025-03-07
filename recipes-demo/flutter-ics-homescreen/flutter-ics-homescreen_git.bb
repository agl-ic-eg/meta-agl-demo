SUMMARY = "AGL ICS Flutter Homescreen"
DESCRIPTION = "Demo Flutter homescreen for Automotive Grade Linux by ICS."
HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-ics-homescreen"
SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-ics-homescreen;protocol=https;branch=${AGL_BRANCH} \
  file://ics-homescreen.toml \
  file://flutter-ics-homescreen.service \
  file://flutter-ics-homescreen.env \
  file://ics-homescreen.yaml \
  file://ics-homescreen.yaml.gateway-demo \
  file://ics-homescreen.yaml.kvm-demo \
  file://ics-homescreen.yaml.kvm-gateway-demo \
  file://ics-homescreen.token \
  file://radio-presets.yaml \
  file://kvm.conf \
"
SRCREV = "96290f750f085f5ce53e810eb5149aa820bca98f"

S = "${WORKDIR}/git"

PUBSPEC_APPNAME = "flutter_ics_homescreen"

inherit flutter-app systemd update-alternatives

APP_CONFIG = "ics-homescreen.toml"

PUBSPEC_IGNORE_LOCKFILE = "1"

SYSTEMD_SERVICE:${PN} = "flutter-ics-homescreen.service"

# Disable the background animation on all platforms except the Renesas M3/H3 for now
DISABLE_BG_ANIMATION = "-DDISABLE_BKG_ANIMATION=true"
DISABLE_BG_ANIMATION:rcar-gen3 = ""
APP_AOT_EXTRA:append = " ${DISABLE_BG_ANIMATION}"

# Check for agl-offline-voice-agent feature
ENABLE_VOICE_ASSISTANT = "${@bb.utils.contains('EXTRA_IMAGE_FEATURES', 'agl-offline-voice-agent', '-DENABLE_VOICE_ASSISTANT=true', '-DENABLE_VOICE_ASSISTANT=false', d)}"
APP_AOT_EXTRA:append = " ${ENABLE_VOICE_ASSISTANT}"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service

    install -D -m 0644 ${WORKDIR}/${BPN}.env ${D}${sysconfdir}/default/${BPN}

    install -D -m 0644 ${WORKDIR}/kvm.conf ${D}${systemd_system_unitdir}/${BPN}.service.d/kvm.conf

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/ics-homescreen
    install -m 0644 ${WORKDIR}/ics-homescreen.yaml ${D}${sysconfdir}/xdg/AGL/ics-homescreen.yaml.default
    install -m 0644 ${WORKDIR}/ics-homescreen.yaml.gateway-demo ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/ics-homescreen.yaml.kvm-demo ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/ics-homescreen.yaml.kvm-gateway-demo ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/ics-homescreen.token ${D}${sysconfdir}/xdg/AGL/ics-homescreen/
    install -m 0644 ${WORKDIR}/radio-presets.yaml ${D}${sysconfdir}/xdg/AGL/ics-homescreen/
}

ALTERNATIVE_LINK_NAME[ics-homescreen.yaml] = "${sysconfdir}/xdg/AGL/ics-homescreen.yaml"

FILES:${PN} += "${datadir} ${sysconfdir}/xdg/AGL ${sysconfdir}/default"

RDEPENDS:${PN} += " \
    flutter-auto \
    agl-flutter-env \
    applaunchd \
"

PACKAGE_BEFORE_PN += "${PN}-conf"
FILES:${PN}-conf += "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.default"
RDEPENDS:${PN}-conf = "${PN}"
RPROVIDES:${PN}-conf = "ics-homescreen.yaml"
ALTERNATIVE:${PN}-conf = "ics-homescreen.yaml"
ALTERNATIVE_TARGET_${PN}-conf = "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.default"

PACKAGE_BEFORE_PN += "${PN}-conf-gateway-demo"
FILES:${PN}-conf-gateway-demo += "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.gateway-demo"
RDEPENDS:${PN}-conf-gateway-demo = "${PN}"
RPROVIDES:${PN}-conf-gateway-demo = "ics-homescreen.yaml"
ALTERNATIVE:${PN}-conf-gateway-demo = "ics-homescreen.yaml"
ALTERNATIVE_TARGET_${PN}-conf-gateway-demo = "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.gateway-demo"
ALTERNATIVE_PRIORITY_${PN}-conf-gateway-demo = "20"

# systemd override to add network-online.target dependency for KVM setups
PACKAGE_BEFORE_PN += "${PN}-conf-kvm"
FILES:${PN}-conf-kvm += "${systemd_system_unitdir}/flutter-ics-homescreen.service.d/kvm.conf"
RDEPENDS:${PN}-conf-kvm = "${PN}"

PACKAGE_BEFORE_PN += "${PN}-conf-kvm-demo"
FILES:${PN}-conf-kvm-demo += "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.kvm-demo"
RDEPENDS:${PN}-conf-kvm-demo = "${PN} ${PN}-conf-kvm"
RPROVIDES:${PN}-conf-kvm-demo = "ics-homescreen.yaml"
ALTERNATIVE:${PN}-conf-kvm-demo = "ics-homescreen.yaml"
ALTERNATIVE_TARGET_${PN}-conf-kvm-demo = "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.kvm-demo"
ALTERNATIVE_PRIORITY_${PN}-conf-kvm-demo = "30"

PACKAGE_BEFORE_PN += "${PN}-conf-kvm-gateway-demo"
FILES:${PN}-conf-kvm-gateway-demo += "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.kvm-gateway-demo"
RDEPENDS:${PN}-conf-kvm-gateway-demo = "${PN} ${PN}-conf-kvm"
RPROVIDES:${PN}-conf-kvm-gateway-demo = "ics-homescreen.yaml"
ALTERNATIVE:${PN}-conf-kvm-gateway-demo = "ics-homescreen.yaml"
ALTERNATIVE_TARGET_${PN}-conf-kvm-gateway-demo = "${sysconfdir}/xdg/AGL/ics-homescreen.yaml.kvm-gateway-demo"
ALTERNATIVE_PRIORITY_${PN}-conf-kvm-gateway-demo = "31"
