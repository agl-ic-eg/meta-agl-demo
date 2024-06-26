SUMMARY = "KUKSA.val CAN provider configuration for AGL demos"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "file://config.ini \
           file://can-provider.token \
           file://dbc_default_values.json \
           file://can-dev-helper.conf \
"

S = "${WORKDIR}"

inherit allarch systemd

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${sysconfdir}/kuksa-can-provider
    install -m 0644 ${WORKDIR}/config.ini ${D}${sysconfdir}/kuksa-can-provider/
    install -m 0644 ${WORKDIR}/can-provider.token ${D}${sysconfdir}/kuksa-can-provider/
    install -m 0644 ${WORKDIR}/dbc_default_values.json ${D}${sysconfdir}/kuksa-can-provider/
    install -d ${D}${systemd_system_unitdir}/kuksa-can-provider.service.d
    install -m 0644 ${WORKDIR}/can-dev-helper.conf ${D}${systemd_system_unitdir}/kuksa-can-provider.service.d/
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "kuksa-can-provider agl-dbc can-dev-helper"
RPROVIDES:${PN} += "kuksa-can-provider-conf"
