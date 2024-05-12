SUMMARY     = "AGL simple QEMU runner script"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit systemd allarch

SRC_URI = "file://agl-qemu-runner.sh \
           file://agl-qemu-runner@.service \
"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    # Install template unit
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/agl-qemu-runner@.service ${D}${systemd_system_unitdir}/

    # Install script
    install -D -m 0755 ${WORKDIR}/agl-qemu-runner.sh ${D}${sbindir}/agl-qemu-runner.sh
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "bash qemu"
