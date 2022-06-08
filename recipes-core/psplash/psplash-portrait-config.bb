SUMMARY     = "Systemd unit override for psplash portrait mode for the AGL Demonstrator"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd allarch

SRC_URI = "file://psplash-portrait.conf"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    # Install override
    install -d ${D}${systemd_system_unitdir}/psplash-start.service.d
    install -m 0644 ${WORKDIR}/psplash-portrait.conf ${D}${systemd_system_unitdir}/psplash-start.service.d/
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "psplash"
