SUMMARY     = "Setting files for agl-cluster-demo-plaform guest VM"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd allarch

SRC_URI = "file://${QEMU_IMAGE}.conf"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

QEMU_IMAGE = "agl-cluster-demo-platform"
QEMU_UNIT = "agl-qemu-runner@${QEMU_IMAGE}.service"

do_install() {
    # Install template unit links
    install -d ${D}${systemd_system_unitdir}
    ln -sf agl-qemu-runner@.service ${D}${systemd_system_unitdir}/${QEMU_UNIT}
    install -d ${D}${systemd_system_unitdir}/multi-user.target.wants
    ln -sf ${systemd_system_unitdir}/${QEMU_UNIT} ${D}${systemd_system_unitdir}/multi-user.target.wants/${QEMU_UNIT}

    # Install conf file
    install -d ${D}${sysconfdir}/agl-qemu-runner
    install -m 0644 ${WORKDIR}/${QEMU_IMAGE}.conf ${D}${sysconfdir}/agl-qemu-runner/
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "agl-qemu-runner qemu-config-vmnet0"
