FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = "\
    file://ofono.conf \
    file://0001-provision-allow-duplicate-entries-from-mbpi_lookup_a.patch \
    "

do_install:append() {
    install -m 0644 ${WORKDIR}/ofono.conf ${D}${sysconfdir}/dbus-1/system.d/ofono.conf
}

SYSTEMD_AUTO_ENABLE = "enable"
