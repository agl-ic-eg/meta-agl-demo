FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://can-dev-mapping-helper.conf"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/can-dev-mapping-helper.conf \
        ${D}${sysconfdir}/systemd/system/afm-service-agl-service-can-low-level-.service.d/can-dev-mapping-helper.conf
}

RDEPENDS:${PN}:append = " can-dev-mapping-helper"
