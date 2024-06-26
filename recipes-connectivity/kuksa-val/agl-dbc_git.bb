SUMMARY = "CAN database (DBC) files for AGL demos"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f814e94ea4f54c1cdeb3fa60579ca000"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/agl-dbc;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "e215d8663370fcdb477d1bc20b434b7e4295fab7"

PV = "1.0+git${SRCPV}"
S  = "${WORKDIR}/git"

inherit allarch

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${datadir}/dbc
    install -m 0644 ${S}/agl-vcar.dbc ${D}${datadir}/dbc/
}

FILES:${PN} += "${datadir}/dbc"
