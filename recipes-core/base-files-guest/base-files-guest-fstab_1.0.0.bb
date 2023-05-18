SUMMARY = "Container guest extention for fstab"
DESCRIPTION = "The base-files-guest-fstab package creates the fstab for container guest integration."
SECTION = "base"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = " \
    file://fstab \
"
S = "${WORKDIR}"

#INHIBIT_DEFAULT_DEPS = "1"

do_install () {
	install -m 0755 -d ${D}${sysconfdir}

	install -m 0644 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab-guest
}

PACKAGES = "${PN}"
FILES:${PN} = "${sysconfdir}/fstab-guest"

PACKAGE_ARCH = "${MACHINE_ARCH}"

CONFFILES:${PN} = "${sysconfdir}/fstab-guest"
