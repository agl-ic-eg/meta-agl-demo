SUMMARY = "USB attached I2C demo hardware udev configuration"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://hvac-conf-in-rewrite.sh \
           file://rtc-i2c-attach.sh \
           file://hvac-conf-in-rewrite@.service \
           file://rtc-i2c-attach@.service \
           file://99-agl-led-rtc.rules \
           file://agl-service-hvac-leds.conf.in \
"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${sysconfdir}/xdg/AGL
    install -m 0644 ${WORKDIR}/agl-service-hvac-leds.conf.in ${D}${sysconfdir}/xdg/AGL

    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/hvac-conf-in-rewrite.sh ${D}${sbindir}
    install -m 0755 ${WORKDIR}/rtc-i2c-attach.sh ${D}${sbindir}

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/hvac-conf-in-rewrite@.service ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/rtc-i2c-attach@.service ${D}${systemd_system_unitdir}

        install -d ${D}${sysconfdir}/udev/rules.d
        install -m 0644 ${WORKDIR}/99-agl-led-rtc.rules ${D}${sysconfdir}/udev/rules.d/
    fi
}

FILES:${PN} += "${systemd_unitdir}"

RDEPENDS:${PN} += "bash"
