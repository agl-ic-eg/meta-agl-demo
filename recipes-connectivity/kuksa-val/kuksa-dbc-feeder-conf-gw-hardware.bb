SUMMARY = "KUKSA.val DBC feeder configuration for gateway demo (secondary CAN interface)"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "file://kuksa-dbc-feeder.gw-hardware \
           file://config.ini.gw-hardware \
           file://kuksa-dbc-feeder-can1.service \
"

S = "${WORKDIR}"

inherit systemd update-alternatives

SYSTEMD_SERVICE:${PN} = "kuksa-dbc-feeder-can1.service"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/kuksa-dbc-feeder.gw-hardware ${D}${sysconfdir}/default/
    install -d ${D}${sysconfdir}/kuksa-dbc-feeder
    install -m 0644 ${WORKDIR}/config.ini.gw-hardware ${D}${sysconfdir}/kuksa-dbc-feeder/
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/kuksa-dbc-feeder-can1.service ${D}${systemd_system_unitdir}
    fi
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "kuksa-dbc-feeder vss-agl-gw-hardware"
