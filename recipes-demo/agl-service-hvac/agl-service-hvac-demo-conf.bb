SUMMARY     = "AGL demo configuration for HVAC service daemon"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-service-hvac"
SECTION     = "apps"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://agl-service-hvac-can.conf.demo \
           file://sllin.conf \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    # Install configuration file to use sllin0 instead of the default
    # can0 CAN device.
    install -D -m 644 ${WORKDIR}/agl-service-hvac-can.conf.demo ${D}${sysconfdir}/xdg/AGL/agl-service-hvac-can.conf

    # Install systemd override to add a dependency on kuksa-dbc-feeder-sllin.service,
    # which currently brings up sllin0.  This will need to be reworked in the future
    # to be a bit more straightforward.
    install -d ${D}${systemd_system_unitdir}/agl-service-hvac.service.d
    install -m 0644 ${WORKDIR}/sllin.conf ${D}${systemd_system_unitdir}/agl-service-hvac.service.d/
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "agl-service-hvac kuksa-dbc-feeder-sllin"
