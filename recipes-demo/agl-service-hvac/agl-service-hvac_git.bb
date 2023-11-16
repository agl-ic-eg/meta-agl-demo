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
           file://agl-service-hvac.conf \
           file://agl-service-hvac.token \
"
SRCREV  = "be3bc37fbdd48f70a2cee4fb0f61c8b688707b1f"

PV = "2.0+git${SRCPV}"
S  = "${WORKDIR}/git"

inherit meson pkgconfig systemd

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
    install -m 0644 ${WORKDIR}/agl-service-hvac.conf ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/agl-service-hvac.token ${D}${sysconfdir}/xdg/AGL/agl-service-hvac/
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "kuksa-databroker kuksa-databroker-agl"
