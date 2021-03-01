FILESEXTRAPATHS_prepend := "${THISDIR}/gpsd:"

SRC_URI += "file://sw-device-hook"

# The C++ binding in libgps is problematic as it seems to currently
# be resulting in unresolved symbols at runtime unless libstdc++ is
# linked in by the user.
# Disable it for now since there are no known users.
EXTRA_OESCONS += "libgpsmm='false'"

SYSTEMD_SERVICE_${PN}_append = " gpsdctl@.service"

do_install_append() {
    # There is a missing check upstream, it seems
    rm -f ${D}${includedir}/libgpsmm.h

    # Use the systemd compatible gpsd.rules
    install -d ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 ${S}/gpsd.rules ${D}/${sysconfdir}/udev/rules.d/60-gpsd.rules

    # Support for systemd
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/systemd/gpsdctl@.service ${D}${systemd_unitdir}/system

    # Autoprobe USB devices
    echo 'USBAUTO="true"' >> ${D}${sysconfdir}/default/gpsd.default

    # Install device-hook script for starting NMEA output on Sierra Wireless modems
    install -d ${D}${sysconfdir}/gpsd
    install -m 0755 ${WORKDIR}/sw-device-hook ${D}${sysconfdir}/gpsd/device-hook
}

RPROVIDES_${PN}-conf_append = " virtual/gpsd-conf"

