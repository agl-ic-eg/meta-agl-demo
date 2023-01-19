FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://native-shell-client.service"

inherit systemd

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/native-shell-client.service ${D}${systemd_system_unitdir}/native-shell-client.service
}

RCONFLICTS:${PN} = "homescreen"
