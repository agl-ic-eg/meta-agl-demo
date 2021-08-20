DESCRIPTION = "lin-config tool for the sllin driver module"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://lin_config.c;beginline=4;endline=9;md5=196a29df19a30dbc752937bdfc819d7a"

DEPENDS += "libnl libxml2"

SRC_URI = "git://github.com/trainman419/linux-lin.git;protocol=https;branch=master"
SRCREV = "155d885e8ccc907a56f6c86c4b159fac27ef6fec"
S = "${WORKDIR}/git/lin_config/src"

SRC_URI:append = " \
	file://0001-Change-Makefile-to-use-pkg-config-for-libxml-2.0.patch \
	file://0002-Change-Makefile-to-use-LDFLAGS.patch \
	"

inherit pkgconfig

PV = "0.1+git${SRCPV}"

do_configure[noexec] = "1"

do_install:append() {
    install -d ${D}/${bindir}
    install -m 755 ${S}/lin_config ${D}/${bindir}
}
