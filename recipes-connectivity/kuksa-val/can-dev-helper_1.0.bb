SUMMARY = "Systemd unit for CAN device helper"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://can-dev-helper.service \
           file://can-dev-helper.sh \
"

inherit systemd allarch

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service
    install -D -m 0755 ${WORKDIR}/${BPN}.sh ${D}${sbindir}/${BPN}.sh
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "bash"
