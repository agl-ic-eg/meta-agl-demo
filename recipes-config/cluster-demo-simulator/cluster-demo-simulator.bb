DESCRIPTION = "Systemd unit to start simple CAN simulator"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://cluster-demo-simulator.service"

inherit systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "cluster-demo-simulator.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/cluster-demo-simulator.service ${D}${systemd_system_unitdir}
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} = "simple-can-simulator"
