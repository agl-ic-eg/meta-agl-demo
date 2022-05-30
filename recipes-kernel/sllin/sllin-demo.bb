DESCRIPTION = "AGL IVI demo slLIN driver configuration"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit allarch systemd

SRC_URI = " \
    file://sllin-demo.service \
    file://start_lin_demo.sh \
    file://lin_config.conf \
"

SYSTEMD_SERVICE:${PN} = "sllin-demo.service"

do_install:append () {
	install -d 644 ${D}/${bindir}
	install -m 0755 ${WORKDIR}/start_lin_demo.sh ${D}/${bindir}/start_lin_demo.sh
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/sllin-demo.service ${D}${systemd_system_unitdir}/
	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/lin_config.conf ${D}${sysconfdir}/
}

FILES:${PN} += " \
    ${bindir}/start_lin_demo.sh \
    ${sysconfdir}/lin_config.conf \
"

RDEPENDS:${PN} += "lin-config"
