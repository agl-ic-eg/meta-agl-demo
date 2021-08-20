FILESEXTRAPATHS:prepend := "${THISDIR}/gpsd:"

SRC_URI += "file://sw-device-hook"

do_install:append() {
    # Install device-hook script for starting NMEA output on Sierra Wireless modems
    install -d ${D}${sysconfdir}/gpsd
    install -m 0755 ${WORKDIR}/sw-device-hook ${D}${sysconfdir}/gpsd/device-hook
}



SYSTEMD_SERVICE:${PN}:append = " gpsdctl@.service"

RPROVIDES:${PN}-conf:append = " virtual/gpsd-conf"

do_install:append() {
    # use the systemd compatible gpsd.rules
    install -d ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 ${S}/gpsd.rules ${D}/${sysconfdir}/udev/rules.d/60-gpsd.rules

    #support for systemd
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/systemd/gpsdctl@.service ${D}${systemd_unitdir}/system

    #autoprobe usb gps devices
    echo 'USBAUTO="true"' >> ${D}${sysconfdir}/default/gpsd.default
}
