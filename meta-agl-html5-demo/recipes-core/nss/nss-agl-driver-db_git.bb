SUMMARY = "Custom nss db hosting the kuksa certificates"
DESCRIPTION = "Custom nss db hosting the kuksa certificates for chromium"
AUTHOR = "Jan-Simon Moeller <jsmoeller@linuxfoundation.org>"
HOMEPAGE = "https://git.automotivelinux.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "0.1"

SRC_URI = "file://${BPN}.service \
           file://${BPN}.sh \
          "

inherit systemd

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -d ${D}${sbindir}
        install -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}
        install -m 0755 ${WORKDIR}/${BPN}.sh ${D}${sbindir}
    fi
}

FILES:${PN} += "${systemd_system_unitdir} ${sbindir}"

RDEPENDS:${PN} += "nss agl-users kuksa-val-certificates-client bash"
