SUMMARY = "KUKSA.val DBC feeder configuration for gateway demo control panel"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "file://kuksa-dbc-feeder.control-panel"

S = "${WORKDIR}"

inherit update-alternatives

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/kuksa-dbc-feeder.control-panel ${D}${sysconfdir}/default/
}

ALTERNATIVE_LINK_NAME[kuksa-dbc-feeder.env] = "${sysconfdir}/default/kuksa-dbc-feeder"

RPROVIDES:${PN} = "kuksa-dbc-feeder.env"
ALTERNATIVE:${PN} = "kuksa-dbc-feeder.env"
ALTERNATIVE_TARGET_${PN} = "${sysconfdir}/default/kuksa-dbc-feeder.control-panel"

RDEPENDS:${PN} += "kuksa-dbc-feeder"
