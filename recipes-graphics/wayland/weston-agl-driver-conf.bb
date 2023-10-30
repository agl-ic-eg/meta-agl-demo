SUMMARY     = "Weston systemd configuration to run as agl-driver user"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd allarch features_check

REQUIRED_DISTRO_FEATURES = "wayland systemd"

SRC_URI = "file://weston-agl-driver.conf"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    # Install override drop-in
    install -d ${D}${systemd_system_unitdir}/weston.service.d
    install -m 0644 ${WORKDIR}/weston-agl-driver.conf ${D}${systemd_system_unitdir}/weston.service.d/
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "weston agl-users"
