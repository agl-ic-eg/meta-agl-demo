SUMMARY     = "Demo HVAC Service Daemon"
DESCRIPTION = "Demo HVAC Service Daemon"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-service-hvac"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    glib-2.0 \
    boost \
    openssl \
    systemd \
    protobuf-native \
    grpc-native \
    protobuf \
    grpc \
    kuksa-databroker \
"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-service-hvac;protocol=https;branch=${AGL_BRANCH} \
           file://agl-service-hvac.conf.default \
           file://agl-service-hvac.conf.gateway-demo \
           file://agl-service-hvac.token \
           file://agl-service-hvac.service \
           file://databroker.conf \
"
SRCREV  = "03cae3b01fe81328561a90119609a81924247cfe"

PV = "2.0+git${SRCPV}"
S  = "${WORKDIR}/git"

inherit meson pkgconfig systemd update-alternatives

EXTRA_OEMESON += "-Dprotos=${STAGING_INCDIR}"

SYSTEMD_SERVICE:${PN} = "agl-service-hvac.service"

do_install:append() {
    # Currently using default global client and CA certificates
    # for KUKSA.val SSL, installing app specific ones would go here.

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/agl-service-hvac
    install -m 0644 ${WORKDIR}/agl-service-hvac.conf.default ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/agl-service-hvac.conf.gateway-demo ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/agl-service-hvac.token ${D}${sysconfdir}/xdg/AGL/agl-service-hvac/

    # Replace the default systemd unit
    install -m 0644 ${WORKDIR}/agl-service-hvac.service ${D}${systemd_system_unitdir}/
    install -m 0644 -D ${WORKDIR}/databroker.conf ${D}${systemd_system_unitdir}/agl-service-hvac.d/databroker.conf
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "${PN}-conf"

ALTERNATIVE_LINK_NAME[agl-service-hvac.conf] = "${sysconfdir}/xdg/AGL/agl-service-hvac.conf"

PACKAGE_BEFORE_PN += "${PN}-conf ${PN}-conf-gateway-demo ${PN}-systemd-databroker"

FILES:${PN}-conf += "${sysconfdir}/xdg/AGL/agl-service-hvac.conf.default"
RDEPENDS:${PN}-conf = "${PN}"
RPROVIDES:${PN}-conf = "agl-service-hvac.conf"
ALTERNATIVE:${PN}-conf = "agl-service-hvac.conf"
ALTERNATIVE_TARGET_${PN}-conf = "${sysconfdir}/xdg/AGL/agl-service-hvac.conf.default"

FILES:${PN}-conf-gateway-demo += "${sysconfdir}/xdg/AGL/agl-service-hvac.conf.gateway-demo"
RDEPENDS:${PN}-conf-gateway-demo = "${PN}"
RPROVIDES:${PN}-conf-gateway-demo = "agl-service-hvac.conf"
ALTERNATIVE:${PN}-conf-gateway-demo = "agl-service-hvac.conf"
ALTERNATIVE_TARGET_${PN}-conf-gateway-demo = "${sysconfdir}/xdg/AGL/agl-service-hvac.conf.gateway-demo"
ALTERNATIVE_PRIORITY_${PN}-conf-gateway-demo = "20"

FILES:${PN}-systemd-databroker += "${systemd_system_unitdir}/agl-service-hvac.d/databroker.conf"

