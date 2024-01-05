DESCRIPTION = "AGL VSS helper daemon"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://agl-vss-helper.py \
           file://agl-vss-helper.yaml \
           file://agl-vss-helper.token \
           file://agl-vss-helper.service \
"

inherit systemd

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/${BPN}.py ${D}${sbindir}
    install -d ${D}${sysconfdir}/xdg/AGL/${BPN}
    install -m 0644 ${WORKDIR}/${BPN}.yaml ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/${BPN}.token ${D}${sysconfdir}/xdg/AGL/${BPN}/
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service
}

RDEPENDS:${PN} = " \
    python3 \
    python3-asyncio \
    python3-systemd \
    kuksa-databroker \
    kuksa-databroker-agl \
    kuksa-client \
    kuksa-certificates-agl-ca \
"
