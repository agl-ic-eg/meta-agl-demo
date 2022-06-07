SUMMARY = "Demo LIN configuration for DBC feeder for KUKSA.val"
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "file://config-sllin.ini \
            file://kuksa-dbc-feeder-sllin.service \
"

inherit allarch systemd

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_install() {
    # NOTE: Installing into same /etc directory as the main configuration for now,
    #       this may be worth re-evaluating at some point (e.g. splitting the DBC
    #       files, having a dedicated token, etc.).
    install -d ${D}${sysconfdir}/kuksa-dbc-feeder
    install -m 0644 ${WORKDIR}/config-sllin.ini ${D}${sysconfdir}/kuksa-dbc-feeder/
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/kuksa-dbc-feeder-sllin.service ${D}${systemd_system_unitdir}
    fi
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += " \
    kuksa-dbc-feeder \
    can-dev-helper \
    sllin-demo \
"
