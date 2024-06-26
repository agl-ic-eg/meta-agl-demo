SUMMARY = "KUKSA.val CAN provider configuration for gateway demo (secondary CAN interface)"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "file://kuksa-can-provider.gw-hardware \
           file://config.ini.gw-hardware \
           file://kuksa-can-provider-can1.service \
"

S = "${WORKDIR}"

inherit systemd update-alternatives

SYSTEMD_SERVICE:${PN} = "kuksa-can-provider-can1.service"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/kuksa-can-provider.gw-hardware ${D}${sysconfdir}/default/
    install -d ${D}${sysconfdir}/kuksa-can-provider
    install -m 0644 ${WORKDIR}/config.ini.gw-hardware ${D}${sysconfdir}/kuksa-can-provider/
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/kuksa-can-provider-can1.service ${D}${systemd_system_unitdir}
    fi
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "kuksa-can-provider kuksa-can-provider-conf-agl vss-agl-gw-hardware"
