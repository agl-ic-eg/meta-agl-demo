SUMMARY     = "Audio Mixer Service Daemon"
DESCRIPTION = "AGL Audio Mixer Service Daemon"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-service-audiomixer"
SECTION     = "apps"
LICENSE     = "MIT & Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;beginline=3;md5=e8ad01a5182f2c1b3a2640e9ea268264"

DEPENDS = " \
    glib-2.0 \
    boost \
    openssl \
    systemd \
    pipewire \
    wireplumber \
    protobuf-native \
    grpc-native \
    protobuf \
    grpc \
    kuksa-databroker \
"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-service-audiomixer.git;protocol=https;branch=${AGL_BRANCH} \
           file://agl-service-audiomixer.conf.default \
           file://agl-service-audiomixer.conf.gateway-demo \
           file://agl-service-audiomixer.token \
           file://agl-service-audiomixer.service \
           file://databroker.conf \
"
SRCREV  = "e2ad7f96f1e4f3ec88848daca5909e3ae01b2126"

PV = "2.0+git${SRCPV}"
S  = "${WORKDIR}/git"

inherit meson pkgconfig systemd update-alternatives

EXTRA_OEMESON += "-Dprotos=${STAGING_INCDIR}"

SYSTEMD_SERVICE:${PN} = "agl-service-audiomixer.service" 

do_install:append() {
    # Currently using default global client and CA certificates
    # for KUKSA.val SSL, installing app specific ones would go here.

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/agl-service-audiomixer
    install -m 0644 ${WORKDIR}/agl-service-audiomixer.conf.default ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/agl-service-audiomixer.conf.gateway-demo ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/agl-service-audiomixer.token ${D}${sysconfdir}/xdg/AGL/agl-service-audiomixer/

    # Replace the default systemd unit
    install -m 0644 ${WORKDIR}/agl-service-audiomixer.service ${D}${systemd_system_unitdir}/
    install -m 0644 -D ${WORKDIR}/databroker.conf ${D}${systemd_system_unitdir}/agl-service-audiomixer.d/databroker.conf
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "${PN}-conf"

ALTERNATIVE_LINK_NAME[agl-service-audiomixer.conf] = "${sysconfdir}/xdg/AGL/agl-service-audiomixer.conf"

PACKAGE_BEFORE_PN += "${PN}-conf ${PN}-conf-gateway-demo ${PN}-systemd-databroker"

FILES:${PN}-conf += "${sysconfdir}/xdg/AGL/agl-service-audiomixer.conf.default"
RDEPENDS:${PN}-conf = "${PN}"
RPROVIDES:${PN}-conf = "agl-service-audiomixer.conf"
ALTERNATIVE:${PN}-conf = "agl-service-audiomixer.conf"
ALTERNATIVE_TARGET_${PN}-conf = "${sysconfdir}/xdg/AGL/agl-service-audiomixer.conf.default"

FILES:${PN}-conf-gateway-demo += "${sysconfdir}/xdg/AGL/agl-service-audiomixer.conf.gateway-demo"
RDEPENDS:${PN}-conf-gateway-demo = "${PN}"
RPROVIDES:${PN}-conf-gateway-demo = "agl-service-audiomixer.conf"
ALTERNATIVE:${PN}-conf-gateway-demo = "agl-service-audiomixer.conf"
ALTERNATIVE_TARGET_${PN}-conf-gateway-demo = "${sysconfdir}/xdg/AGL/agl-service-audiomixer.conf.gateway-demo"
ALTERNATIVE_PRIORITY_${PN}-conf-gateway-demo = "20"

FILES:${PN}-systemd-databroker += "${systemd_system_unitdir}/agl-service-audiomixer.d/databroker.conf"
