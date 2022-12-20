SUMMARY = "AGL cluster demo configuration file"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += " \
    file://AGL.conf.default \
    file://AGL.conf.flutter \
"

inherit allarch update-alternatives

do_install() {
    install -D -m 0644 ${WORKDIR}/AGL.conf.default ${D}${sysconfdir}/xdg/AGL.conf.default
    install -m 0644 ${WORKDIR}/AGL.conf.flutter ${D}${sysconfdir}/xdg/ 
}

ALTERNATIVE_LINK_NAME[AGL.conf] = "${sysconfdir}/xdg/AGL.conf"

PACKAGE_BEFORE_PN += "${PN}-flutter"

FILES:${PN} += "${sysconfdir}/xdg/AGL.conf.default"
RPROVIDES:${PN} = "AGL.conf"
RCONFLICTS:${PN} = "${PN}-flutter"
ALTERNATIVE:${PN} = "AGL.conf"
ALTERNATIVE_TARGET_${PN} = "${sysconfdir}/xdg/AGL.conf.default"

FILES:${PN}-flutter += "${sysconfdir}/xdg/AGL.conf.flutter"
RPROVIDES:${PN}-flutter = "AGL.conf"
RCONFLICTS:${PN}-flutter = "${PN}"
ALTERNATIVE:${PN}-flutter = "AGL.conf"
ALTERNATIVE_TARGET_${PN}-flutter = "${sysconfdir}/xdg/AGL.conf.flutter"
