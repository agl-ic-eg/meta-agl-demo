SUMMARY     = "AGL VSS Proxy"
DESCRIPTION = "AGL VSS Proxy"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-vss-proxy"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    glib-2.0 \
    yaml-cpp \
    protobuf-native \
    grpc-native \
    protobuf \
    grpc \
    kuksa-databroker \
    mosquitto \
"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/agl-vss-proxy;protocol=https;branch=${AGL_BRANCH} \
           file://agl-vss-proxy.token \
"
SRCREV  = "2b123af2c621869fa844d0443cb3853dac059d22"

PV = "1.0+git${SRCPV}"
S  = "${WORKDIR}/git"

inherit meson pkgconfig systemd

EXTRA_OEMESON += "-Dprotos=${STAGING_INCDIR}"

SYSTEMD_SERVICE:${PN} = "agl-vss-proxy.service"

do_install:append() {
    # Currently using default global client and CA certificates
    # for KUKSA.val SSL, installing app specific ones would go here.

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/agl-vss-proxy
    install -m 0644 ${WORKDIR}/agl-vss-proxy.token ${D}${sysconfdir}/agl-vss-proxy/
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "kuksa-databroker kuksa-databroker-agl"
